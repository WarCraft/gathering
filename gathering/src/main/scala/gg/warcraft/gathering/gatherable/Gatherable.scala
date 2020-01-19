package gg.warcraft.gathering.gatherable

import gg.warcraft.monolith.api.entity.{Entity, EntityType}
import gg.warcraft.monolith.api.world.block.{Block, BlockTypeVariantOrState}
import gg.warcraft.monolith.api.world.item.ItemTypeOrVariant

trait Gatherable {
  val dropData: ItemTypeOrVariant
  val dropName: String
  val cooldown: Int
  val cooldownDelta: Int
}

case class BlockGatherable(
    blockData: BlockTypeVariantOrState,
    dropData: ItemTypeOrVariant,
    dropName: String,
    cooldown: Int,
    cooldownDelta: Int,
    cooldownData: BlockTypeVariantOrState
) extends Gatherable {
  def matches(block: Block): Boolean =
    block.hasData(blockData)
}

case class EntityGatherable(
    entityType: EntityType,
    entityCount: Int,
    dropData: ItemTypeOrVariant,
    dropName: String,
    cooldown: Int,
    cooldownDelta: Int
) extends Gatherable {
  def matches(entity: Entity): Boolean =
    entity.getType == entityType
}
