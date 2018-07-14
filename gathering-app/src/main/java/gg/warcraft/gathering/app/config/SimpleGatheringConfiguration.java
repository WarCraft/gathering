package gg.warcraft.gathering.app.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gg.warcraft.gathering.api.config.BlockGatheringSpotConfiguration;
import gg.warcraft.gathering.api.config.EntityGatheringSpotConfiguration;
import gg.warcraft.gathering.api.config.GatheringConfiguration;

import java.util.List;

public class SimpleGatheringConfiguration implements GatheringConfiguration {
    private final List<BlockGatheringSpotConfiguration> blockGatheringSpots;
    private final List<EntityGatheringSpotConfiguration> entityGatheringSpots;

    @JsonCreator
    public SimpleGatheringConfiguration(@JsonProperty("blockGatheringSpots") List<BlockGatheringSpotConfiguration> blockGatheringSpots,
                                        @JsonProperty("entityGatheringSpots") List<EntityGatheringSpotConfiguration> entityGatheringSpots) {
        this.blockGatheringSpots = blockGatheringSpots;
        this.entityGatheringSpots = entityGatheringSpots;
    }

    @Override
    public List<BlockGatheringSpotConfiguration> getBlockGatheringSpots() {
        return blockGatheringSpots;
    }

    @Override
    public List<EntityGatheringSpotConfiguration> getEntityGatheringSpots() {
        return entityGatheringSpots;
    }
}
