package gg.warcraft.gathering.api

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import gg.warcraft.gathering.api.gatherable.{BlockGatherable, EntityGatherable}
import gg.warcraft.monolith.api.Implicits._
import gg.warcraft.monolith.api.config.BoundingBlockBoxConfiguration

@JsonCreator
case class BlockGatherableConfig(
    @JsonProperty("blockData") blockData: String,
    @JsonProperty("dropData") dropData: String,
    @JsonProperty("dropName") dropName: String,
    @JsonProperty("cooldown") cooldown: Int,
    @JsonProperty("cooldownDelta") cooldownDelta: Int,
    @JsonProperty("cooldownData") cooldownData: String
) {
  def toBlockGatherable: BlockGatherable = new BlockGatherable(
    blockData,
    dropData,
    dropName,
    cooldown,
    cooldownDelta,
    cooldownData
  )
}

@JsonCreator
case class EntityGatherableConfig(
    @JsonProperty("entityType") entityType: String,
    @JsonProperty("entityCount") entityCount: Int,
    @JsonProperty("dropData") dropData: String,
    @JsonProperty("dropName") dropName: String,
    @JsonProperty("cooldown") cooldown: Int,
    @JsonProperty("cooldownDelta") cooldownDelta: Int
) {
  def toEntityGatherable: EntityGatherable = new EntityGatherable(
    entityType,
    entityCount,
    dropData,
    dropName,
    cooldown,
    cooldownDelta
  )
}

@JsonCreator
case class GatheringSpotConfig(
    @JsonProperty("id") id: String,
    @JsonProperty("boundingBox") boundingBox: BoundingBlockBoxConfiguration,
    @JsonProperty("blockGatherables") blocks: List[BlockGatherableConfig],
    @JsonProperty("entityGatherables") entities: List[EntityGatherableConfig]
) {
  def toGatheringSpot: GatheringSpot = new GatheringSpot(
    id,
    null, // TODO
    blocks.map(_.toBlockGatherable),
    entities.map(_.toEntityGatherable)
  )
}

@JsonCreator
case class GatheringConfig(
    @JsonProperty("gatheringSpots") gatheringSpots: List[GatheringSpotConfig]
)
