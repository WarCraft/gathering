package gg.warcraft.gathering.api.gatherable

import gg.warcraft.monolith.api.world.block.{Block, BlockTypeVariantOrState}
import gg.warcraft.monolith.api.world.item.ItemTypeOrVariant

case class BlockGatherable(
    data: BlockTypeVariantOrState,
    dropData: ItemTypeOrVariant,
    dropName: String,
    cooldown: Int,
    cooldownDelta: Int,
    cooldownData: BlockTypeVariantOrState
) {
  def contains(block: Block): Boolean =
    block.hasData(data)
}
