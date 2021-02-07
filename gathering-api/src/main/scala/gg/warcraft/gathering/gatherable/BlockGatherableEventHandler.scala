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

import gg.warcraft.gathering.GatheringSpotService
import gg.warcraft.monolith.api.block.BlockPreBreakEvent
import gg.warcraft.monolith.api.core.auth.AuthService
import gg.warcraft.monolith.api.core.event.{Event, PreEvent}

class BlockGatherableEventHandler(implicit
    authService: AuthService,
    gatheringSpotService: GatheringSpotService,
    gatherableService: BlockGatherableService
) extends Event.Handler {
  override def reduce[T <: PreEvent](event: T): T = event match {
    case it @ BlockPreBreakEvent(block, player, _, _, _)
        if !authService.isBuilding(player) =>
      val gathered = gatheringSpotService.blockGatheringSpotsById.values
        .filter { _.contains(block) }
        .exists { spot =>
          spot.blocks
            .find { it => block.hasData(it.blockData) }
            .exists { gatherableService.gatherBlock(spot, _, block, player) }
        }
      if (gathered) {
        it.copy(
          alternativeDrops = Some(List()),
          explicitlyAllowed = true
        ).asInstanceOf[T]
      } else event

    case _ => event
  }
}
