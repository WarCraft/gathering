/*
 * MIT License
 *
 * Copyright (c) 2020 WarCraft <https://github.com/WarCraft>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package gg.warcraft.gathering.gatherable

import gg.warcraft.gathering.GatheringSpot
import gg.warcraft.monolith.api.block.Block
import gg.warcraft.monolith.api.block.backup.BlockBackupService
import gg.warcraft.monolith.api.core.Duration._
import gg.warcraft.monolith.api.core.event.EventService
import gg.warcraft.monolith.api.core.task.TaskService
import gg.warcraft.monolith.api.item.ItemService
import gg.warcraft.monolith.api.math.Vector3f
import gg.warcraft.monolith.api.player.Player
import gg.warcraft.monolith.api.world.WorldService

import scala.util.Random

class BlockGatherableService(implicit
    eventService: EventService,
    taskService: TaskService,
    worldService: WorldService,
    blockBackupService: BlockBackupService,
   itemService: ItemService
) extends GatherableService {
  import blockBackupService.restoreBackup

  protected final val dropOffset = Vector3f(0.5f, 0.5f, 0.5f)

  def gatherBlock(
      spot: GatheringSpot,
      gatherable: BlockGatherable,
      block: Block,
      player: Player
  ): Boolean = {
    var preGatherEvent = BlockPreGatherEvent(block, spot, player)
    preGatherEvent = eventService.publish(preGatherEvent)
    if (!preGatherEvent.allowed) return false

    spawnDrops(gatherable, block.location.toLocation)
    queueBlockCooldown(gatherable, block)
    queueBlockRestoration(gatherable, block)

    val gatherEvent = BlockGatherEvent(block, spot, player)
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
