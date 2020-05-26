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

import java.util.UUID

import gg.warcraft.gathering.{GatheringSpot, GatheringSpotService}
import gg.warcraft.monolith.api.core.event.{Event, PreEvent}
import gg.warcraft.monolith.api.entity.{EntityDeathEvent, EntityPreFatalDamageEvent}
import gg.warcraft.monolith.api.player.PlayerService

class EntityGatherableEventHandler(implicit
    gatheringSpotService: GatheringSpotService,
    gatherableService: EntityGatherableService,
    playerService: PlayerService
) extends Event.Handler {
  private var _gatheredEntityIds: Set[UUID] = Set.empty

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
      if (gatherableService.gatherEntity(spot, it, entity, player)) {
        _gatheredEntityIds += entity.id
        event.copy(explicitlyAllowed = true)
      } else event
    }

    gatheringSpotService.gatheringSpots
      .find(_.contains(entity.id))
      .map(spot => {
        spot.entityGatherables
          .find(_.matches(entity.typed))
          .map(gatherEntity(spot, _))
          .getOrElse(event)
      })
      .getOrElse(event)
  }

  private def reduceDeath(event: EntityDeathEvent): EntityDeathEvent = {
    import event.entity
    // TODO add preDeath event to remove drops
    if (_gatheredEntityIds.contains(entity.id)) {
      _gatheredEntityIds -= entity.id
      event
    } else event
  }
}
