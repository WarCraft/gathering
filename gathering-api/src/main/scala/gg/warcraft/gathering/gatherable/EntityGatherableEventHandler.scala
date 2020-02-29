package gg.warcraft.gathering.gatherable

import java.util.UUID

import gg.warcraft.gathering.{GatheringSpot, GatheringSpotService}
import gg.warcraft.monolith.api.core.event.{EventHandler, PreEvent}
import gg.warcraft.monolith.api.entity.{EntityDeathEvent, EntityPreFatalDamageEvent}
import gg.warcraft.monolith.api.entity.player.service.PlayerQueryService

import scala.collection.mutable

object EntityGatherableEventHandler {
  private val gatheredEntityIds = mutable.Set[UUID]()
}

class EntityGatherableEventHandler(
    private implicit val gatheringSpotService: GatheringSpotService,
    private implicit val gatherableService: EntityGatherableService,
    private implicit val playerService: PlayerQueryService
) extends EventHandler {
  import EntityGatherableEventHandler.gatheredEntityIds

  override def reduce[T <: PreEvent](event: T): T = event match {
    case it: EntityPreFatalDamageEvent => reducePreFatal(it).asInstanceOf[T]
    case it: EntityDeathEvent          => reduceDeath(it).asInstanceOf[T]
    case _                             => event
  }

  private def reducePreFatal(
      event: EntityPreFatalDamageEvent
  ): EntityPreFatalDamageEvent = {
    import event._

    val attackerId = damage.source.entityId
    if (attackerId.isEmpty) return event
    val player = playerService.getPlayer(attackerId.get)
    if (player == null) return event

    val gatherEntity = (spot: GatheringSpot, it: EntityGatherable) => {
      if (gatherableService.gatherEntity(spot, it, entityId, player.getId)) {
        gatheredEntityIds.add(entityId)
        event.copy(explicitlyAllowed = true)
      } else event
    }

    gatheringSpotService.getGatheringSpots
      .find(_.contains(entityId))
      .map(spot => {
        spot.entityGatherables
          .find(_.matches(entityType))
          .map(gatherEntity(spot, _))
          .getOrElse(event)
      })
      .getOrElse(event)
  }

  private def reduceDeath(event: EntityDeathEvent): EntityDeathEvent = {
    import event._

    // TODO add preDeath event to remove drops
    if (gatheredEntityIds.remove(entityId)) event
    else event
  }
}
