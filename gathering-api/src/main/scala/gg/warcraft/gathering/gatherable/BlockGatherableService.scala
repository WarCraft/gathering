package gg.warcraft.gathering.gatherable

import java.util.UUID

import gg.warcraft.gathering.GatheringSpot
import gg.warcraft.monolith.api.block.backup.BlockBackupService
import gg.warcraft.monolith.api.block.Block
import gg.warcraft.monolith.api.core.event.EventService
import gg.warcraft.monolith.api.core.Duration._
import gg.warcraft.monolith.api.core.task.TaskService
import gg.warcraft.monolith.api.item.ItemService
import gg.warcraft.monolith.api.math.Vector3f
import gg.warcraft.monolith.api.world.WorldService

import scala.util.Random

class BlockGatherableService(
    private implicit val eventService: EventService,
    private implicit val taskService: TaskService,
    private implicit val worldService: WorldService,
    private implicit val blockBackupService: BlockBackupService,
    protected implicit val itemService: ItemService
) extends GatherableService {
  import blockBackupService.restoreBackup

  protected final val dropOffset = Vector3f(0.5f, 0.5f, 0.5f)

  def gatherBlock(
      spot: GatheringSpot,
      gatherable: BlockGatherable,
      block: Block,
      playerId: UUID
  ): Boolean = {
    var preGatherEvent = BlockPreGatherEvent(block, spot, playerId)
    preGatherEvent = eventService.publish(preGatherEvent)
    if (!preGatherEvent.allowed) return false

    spawnDrops(gatherable, block.location.toLocation)
    queueBlockCooldown(gatherable, block)
    queueBlockRestoration(gatherable, block)

    val gatherEvent = BlockGatherEvent(block, spot, playerId)
    eventService.publish(gatherEvent)
    true
  }

  private def queueBlockCooldown(gatherable: BlockGatherable, block: Block): Unit =
    taskService.evalNextTick {
      worldService.setBlock(block.location, gatherable.cooldownData)
    }

  private def queueBlockRestoration(
      gatherable: BlockGatherable,
      block: Block
  ): Unit = {
    val backupId = blockBackupService.createBackup(block.location)
    val cooldown = gatherable.cooldown + Random.nextInt(gatherable.cooldownDelta)
    taskService.evalLater(cooldown.seconds, restoreBackup(backupId))
  }
}
