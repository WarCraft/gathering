package gg.warcraft.gathering.api.gatherable

import java.util.UUID

import gg.warcraft.gathering.api.GatheringSpot
import gg.warcraft.monolith.api.core.TaskService
import gg.warcraft.monolith.api.core.event.EventService
import gg.warcraft.monolith.api.math.Vector3f
import gg.warcraft.monolith.api.world.WorldService
import gg.warcraft.monolith.api.world.block.Block
import gg.warcraft.monolith.api.world.block.backup.BlockBackupService
import gg.warcraft.monolith.api.world.item.ItemService

class BlockGatherableService(
    private implicit val eventService: EventService,
    private implicit val taskService: TaskService,
    private implicit val itemService: ItemService,
    private implicit val worldService: WorldService,
    private implicit val blockBackupService: BlockBackupService
) {
  private final val dropOffset = Vector3f(0.5f, 0.5f, 0.5f)

  def gatherBlock(
      spot: GatheringSpot,
      gatherable: BlockGatherable,
      block: Block,
      playerId: UUID
  ): Boolean = {
    var preGatherEvent = BlockPreGatherEvent(block, spot.id, playerId)
    preGatherEvent = eventService.publish(preGatherEvent)
    if (!preGatherEvent.allowed) return false

    val drop = gatherable.generateDrop
    val dropLocation = block.location.toLocation.add(dropOffset)
    itemService.dropItems(dropLocation, drop)

    queueCooldownState(gatherable, block)
    queueBlockRestoration(gatherable, block)

    val gatherEvent = BlockGatherEvent(block, spot.id, playerId)
    eventService.publish(gatherEvent)
    true
  }

  def queueCooldownState(gatherable: BlockGatherable, block: Block): Unit =
    taskService.runNextTick(() => {
      worldService.setBlock(block.location, gatherable.cooldownData)
    })

  def queueBlockRestoration(gatherable: BlockGatherable, block: Block): Unit = {
    val backupId = blockBackupService.createBackup(block.location)
    taskService.runLater(
      () => blockBackupService.restoreBackup(backupId),
      gatherable.generateCooldown
    )
  }
}
