package gg.warcraft.gathering.api

import com.fasterxml.jackson.annotation.{JsonCreator, JsonProperty}
import gg.warcraft.monolith.api.config.BoundingBlockBoxConfiguration

@JsonCreator
case class BlockGatherableConfig(
    @JsonProperty("blockData") blockData: String,
    @JsonProperty("dropData") dropData: String,
    @JsonProperty("dropName") dropName: String,
    @JsonProperty("cooldown") cooldown: Int,
    @JsonProperty("cooldownDelta") cooldownDelta: Int,
    @JsonProperty("cooldownData") cooldownData: String
)

@JsonCreator
case class EntityGatherableConfig(
    @JsonProperty("entityType") entityType: String,
    @JsonProperty("entityCount") entityCount: Int,
    @JsonProperty("dropData") dropData: String,
    @JsonProperty("dropName") dropName: String,
    @JsonProperty("cooldown") cooldown: Int,
    @JsonProperty("cooldownDelta") cooldownDelta: Int
)

@JsonCreator
case class GatheringSpotConfig(
    @JsonProperty("id") id: String,
    @JsonProperty("boundingBox") boundingBox: BoundingBlockBoxConfiguration,
    @JsonProperty("blockGatherables") blocks: List[BlockGatherableConfig],
    @JsonProperty("entityGatherables") entities: List[EntityGatherableConfig]
)

@JsonCreator
case class GatheringConfig(
    @JsonProperty("gatheringSpots") gatheringSpots: List[GatheringSpotConfig]
)
