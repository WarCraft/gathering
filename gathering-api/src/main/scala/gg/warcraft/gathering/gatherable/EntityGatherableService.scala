package gg.warcraft.gathering.gatherable

import java.util.UUID

import gg.warcraft.gathering.GatheringSpot
import gg.warcraft.monolith.api.core.event.EventService
import gg.warcraft.monolith.api.core.Duration._
import gg.warcraft.monolith.api.core.task.TaskService
import gg.warcraft.monolith.api.entity.EntityService
import gg.warcraft.monolith.api.item.ItemService
import gg.warcraft.monolith.api.math.Vector3f
import gg.warcraft.monolith.api.world.Location

import scala.util.Random

class EntityGatherableService(
    private implicit val eventService: EventService,
    private implicit val taskService: TaskService,
    private implicit val entityService: EntityService,
    private implicit val entityGatherableRepository: EntityGatherableRepository,
    protected implicit val itemService: ItemService
) extends GatherableService {
  protected final val dropOffset = Vector3f(0, 0.5f, 0)

  def gatherEntity(
      spot: GatheringSpot,
      gatherable: EntityGatherable,
      entityId: UUID,
      playerId: UUID
  ): Boolean = {
    val entity = entityService.getEntity(entityId)

    var preGatherEvent = EntityPreGatherEvent(entity, spot, playerId)
    preGatherEvent = eventService.publish(preGatherEvent)
    if (preGatherEvent.allowed) {
      spawnDrops(gatherable, entity.location)
      entityGatherableRepository.delete(entity.id)

      val gatherEvent = EntityGatherEvent(entity, spot, playerId)
      eventService.publish(gatherEvent)
      true
    } else false
  }

  def queueEntityRespawn(gatherable: EntityGatherable, spot: GatheringSpot): Unit = {
    val location: Location = null // TODO generate random location in boundingbox
    val cooldown = gatherable.cooldown + Random.nextInt(gatherable.cooldownDelta)
    taskService.runLater(
      () => {
        val entityType = gatherable.entityType
        val entityId = entityService.spawnEntity(entityType, location)
        entityGatherableRepository.save(spot.id, entityId)

        // NOTE option to publish GatherableEntityRespawnEvent
      },
      cooldown.seconds
    )
  }
}
