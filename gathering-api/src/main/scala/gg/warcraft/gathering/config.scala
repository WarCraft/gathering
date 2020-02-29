package gg.warcraft.gathering

import gg.warcraft.gathering.gatherable.{BlockGatherable, EntityGatherable}
import gg.warcraft.monolith.api.config.BoundingBlockBoxConfiguration
import gg.warcraft.monolith.api.entity.EntityType
import gg.warcraft.monolith.api.world.block.BlockTypeVariantOrState
import gg.warcraft.monolith.api.world.item.ItemTypeOrVariant

case class BlockGatherableConfig(
    blockData: BlockTypeVariantOrState,
    dropData: ItemTypeOrVariant,
    dropName: String,
    cooldown: Int,
    cooldownDelta: Int,
    cooldownData: BlockTypeVariantOrState
) {
  def toBlockGatherable: BlockGatherable = BlockGatherable(
    blockData,
    dropData,
    dropName,
    cooldown,
    cooldownDelta,
    cooldownData
  )
}

case class EntityGatherableConfig(
    entityType: EntityType,
    entityCount: Int,
    dropData: ItemTypeOrVariant,
    dropName: String,
    cooldown: Int,
    cooldownDelta: Int
) {
  def toEntityGatherable: EntityGatherable = EntityGatherable(
    entityType,
    entityCount,
    dropData,
    dropName,
    cooldown,
    cooldownDelta
  )
}

case class GatheringSpotConfig(
    id: String,
    boundingBox: BoundingBlockBoxConfiguration,
    blocks: List[BlockGatherableConfig],
    entities: List[EntityGatherableConfig]
) {
  def toGatheringSpot: GatheringSpot = new GatheringSpot(
    id,
    null, // TODO
    blocks.map(_.toBlockGatherable),
    entities.map(_.toEntityGatherable)
  )
}

case class GatheringConfig(
    gatheringSpots: List[GatheringSpotConfig]
)
