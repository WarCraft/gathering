package gg.warcraft.gathering

import gg.warcraft.gathering.gatherable.{BlockGatherable, EntityGatherable}
import gg.warcraft.monolith.api.entity.Entity
import gg.warcraft.monolith.api.world.block.Block
import gg.warcraft.monolith.api.world.block.box.BoundingBlockBox

class GatheringSpot(
    val id: String,
    val boundingBox: BoundingBlockBox,
    val blockGatherables: List[BlockGatherable],
    val entityGatherables: List[EntityGatherable]
) {
  def contains(block: Block): Boolean =
    boundingBox.test(block.location)

  def contains(entity: Entity): Boolean =
    boundingBox.test(entity.getLocation.toBlockLocation)
}
