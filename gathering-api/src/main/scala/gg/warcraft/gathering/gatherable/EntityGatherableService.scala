package gg.warcraft.gathering.gatherable

import java.util.UUID

import gg.warcraft.gathering.GatheringSpot
import gg.warcraft.monolith.api.core.event.EventService
import gg.warcraft.monolith.api.core.{Duration, TaskService}
import gg.warcraft.monolith.api.entity.service.{
  EntityCommandService, EntityQueryService
}
import gg.warcraft.monolith.api.math.Vector3f
import gg.warcraft.monolith.api.world.Location
import gg.warcraft.monolith.api.world.item.ItemService

import scala.util.Random

class EntityGatherableService(
    private implicit val eventService: EventService,
    private implicit val taskService: TaskService,
    private implicit val entityService: EntityCommandService,
    private implicit val entityQueryService: EntityQueryService,
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
    val entity = entityQueryService.getEntity(entityId)
    import entity.{getId, getType}

    var preGatherEvent = EntityPreGatherEvent(getId, getType, spot.id, playerId)
    preGatherEvent = eventService.publish(preGatherEvent)
    if (!preGatherEvent.allowed) return false

    spawnDrops(gatherable, entity.getLocation)
    entityGatherableRepository.delete(getId)

    val gatherEvent =
      EntityGatherEvent(getId, getType, spot.id, playerId)
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
        entityGatherableRepository.save(spot.id, entityId)

        // NOTE option to publish GatherableEntityRespawnEvent
      },
      Duration.ofSeconds(cooldown)
    )
  }
}
