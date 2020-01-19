package gg.warcraft.gathering

import java.util.UUID

import gg.warcraft.gathering.gatherable.{BlockGatherable, EntityGatherable}
import gg.warcraft.monolith.api.world.block.Block
import gg.warcraft.monolith.api.world.block.box.BoundingBlockBox

import scala.collection.mutable

class GatheringSpot(
    val id: String,
    val boundingBox: BoundingBlockBox,
    val blockGatherables: List[BlockGatherable],
    val entityGatherables: List[EntityGatherable]
) {
  val entities: mutable.Set[UUID] = mutable.Set()

  def contains(block: Block): Boolean =
    boundingBox.test(block.location)

  def contains(entityId: UUID): Boolean =
    entities.contains(entityId)
}
