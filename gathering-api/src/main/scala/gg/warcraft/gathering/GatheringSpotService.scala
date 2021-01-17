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

package gg.warcraft.gathering

import gg.warcraft.gathering.gatherable.EntityGatherableService
import gg.warcraft.monolith.api.entity.EntityService

class GatheringSpotService(implicit
    entityService: EntityService,
    entityGatherableService: EntityGatherableService
) {
  private var _blockGatheringSpotsById: Map[String, BlockGatheringSpot] =
    Map.empty
  def blockGatheringSpotsById: Map[String, BlockGatheringSpot] =
    _blockGatheringSpotsById

  private var _entityGatheringSpotsById: Map[String, EntityGatheringSpot] =
    Map.empty
  def entityGatheringSpotsById: Map[String, EntityGatheringSpot] =
    _entityGatheringSpotsById

  def readConfig(config: GatheringConfig): Unit = {
    config.blockGatheringSpots.foreach(addGatheringSpot)
    config.entityGatheringSpot.map { _.parse() }.foreach(addGatheringSpot)
  }

  def addGatheringSpot(spot: GatheringSpot): Boolean =
    if (
      !_blockGatheringSpotsById.contains(spot.id)
      && !_entityGatheringSpotsById.contains(spot.id)
    ) {
      spot match {
        case spot: BlockGatheringSpot =>
          _blockGatheringSpotsById += (spot.id -> spot)
        case spot: EntityGatheringSpot =>
          _entityGatheringSpotsById += (spot.id -> spot)
          spot.entities.foreach { entity =>
            for (_ <- 1 to entity.entityCount)
              entityGatherableService.queueEntityRespawn(entity, spot)

            entityService.getEntitiesWithin(spot.boundingBox)
              .filter { _.typed == entity.entityType }
              .filter { it => !spot.entityIds.contains(it.id) }
              .foreach(it => entityService.removeEntity(it.id))
          }
      }
      true
    } else false

  def removeGatheringSpot(id: String): Boolean = {
    var result = false
    result = _blockGatheringSpotsById.get(id) match {
      case Some(_) =>
        _blockGatheringSpotsById -= id
        true
      case None => false
    }
    result = _entityGatheringSpotsById.get(id) match {
      case Some(spot) =>
        _entityGatheringSpotsById -= id
        spot.entityIds.foreach(entityService.removeEntity)
        true
      case None => false
    }
    result
  }
}
