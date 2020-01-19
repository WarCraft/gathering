package gg.warcraft.gathering.gatherable

import java.util.UUID

import gg.warcraft.gathering.{GatheringSpot, GatheringSpotService}
import gg.warcraft.monolith.api.core.event.{EventHandler, PreEvent}
import gg.warcraft.monolith.api.entity.player.service.PlayerQueryService
import gg.warcraft.monolith.api.entity.{EntityDeathEvent, EntityPreFatalDamageEvent}

import scala.collection.mutable

object EntityGatherableEventHandler {
  private val gatheredEntityIds = mutable.Set[UUID]()
}

class EntityGatherableEventHandler(
    private val gatheringSpotService: GatheringSpotService,
    private val gatherableService: EntityGatherableService
)(
    private implicit val playerService: PlayerQueryService
) extends EventHandler {
  import EntityGatherableEventHandler.gatheredEntityIds

  override def reduce[T >: PreEvent](event: T): T = event match {
    case preFatalEvent: EntityPreFatalDamageEvent =>
      import preFatalEvent._

      val attackerId = damage.source.entityId
      if (attackerId.isEmpty) return preFatalEvent
      val player = playerService.getPlayer(attackerId.get)
      if (player == null) return preFatalEvent

      val gatherEntity = (spot: GatheringSpot, it: EntityGatherable) => {
        if (gatherableService.gatherEntity(spot, it, entityId, player.getId)) {
          gatheredEntityIds.add(entityId)
          preFatalEvent.copy(explicitlyAllowed = true)
        } else preFatalEvent
      }

      gatheringSpotService.getGatheringSpots
        .find(_.contains(entityId))
        .map(spot => {
          spot.entityGatherables
            .find(_.matches(entityType))
            .map(gatherEntity(spot, _))
            .getOrElse(preFatalEvent)
        })
        .getOrElse(preFatalEvent)

    case deathEvent: EntityDeathEvent =>
      import deathEvent._

      // TODO add preDeath event to remove drops
      if (gatheredEntityIds.remove(entityId)) deathEvent
      else deathEvent

    case _ => event
  }
}
