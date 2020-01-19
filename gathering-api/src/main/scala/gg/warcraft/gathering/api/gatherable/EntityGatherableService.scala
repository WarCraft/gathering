package gg.warcraft.gathering.api.gatherable

import java.util.UUID

import gg.warcraft.gathering.api.GatheringSpot
import gg.warcraft.monolith.api.core.event.EventService
import gg.warcraft.monolith.api.core.{Duration, TaskService}
import gg.warcraft.monolith.api.entity.Entity
import gg.warcraft.monolith.api.entity.service.EntityCommandService
import gg.warcraft.monolith.api.math.Vector3f
import gg.warcraft.monolith.api.world.Location
import gg.warcraft.monolith.api.world.item.ItemService

import scala.collection.mutable
import scala.util.Random

object EntityGatherableService {
  private val entities = mutable.Set[UUID]()
}

class EntityGatherableService(
    private implicit val eventService: EventService,
    private implicit val taskService: TaskService,
    private implicit val itemService: ItemService,
    private implicit val entityService: EntityCommandService
) {
  import EntityGatherableService._

  private final val dropOffset = Vector3f(0.5f, 0, 0.5f)

  def gatherEntity(
      spot: GatheringSpot,
      gatherable: EntityGatherable,
      entity: Entity,
      playerId: UUID
  ): Boolean = {
    val entityId = entity.getId
    val entityType = entity.getType
    var preGatherEvent =
      EntityPreGatherEvent(entityId, entityType, spot.id, playerId)
    preGatherEvent = eventService.publish(preGatherEvent)
    if (!preGatherEvent.allowed) return false

    val drop = itemService.create(gatherable.dropData).withName(gatherable.dropName)
    val dropLocation = entity.getLocation.add(dropOffset)
    itemService.dropItems(dropLocation, drop)
    entities.remove(entityId)
    // TODO delete entityId from persistence

    val gatherEvent =
      EntityGatherEvent(entityId, entityType, spot.id, playerId)
    eventService.publish(gatherEvent)
    true
  }

  def queueEntityRespawn(gatherable: EntityGatherable, spot: GatheringSpot): Unit = {
    val location: Location = null // TODO generate random location in boundingbox
    val cooldown = gatherable.cooldown + Random.nextInt(gatherable.cooldownDelta)
    taskService.runLater(
      () => {
        val entityType = gatherable.entityType
        val entityId = entityService.spawnEntity(entityType, location)
        entities.add(entityId)
        // TODO add entityId to persistence with gatheringSpotId so
        // TODO we don't have to keep removing and respawning them

        // NOTE option to publish GatherableEntityRespawnEvent
      },
      Duration.ofSeconds(cooldown)
    )
  }
}
