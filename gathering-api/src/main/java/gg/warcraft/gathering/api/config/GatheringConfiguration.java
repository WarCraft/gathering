package gg.warcraft.gathering.api.config;

import java.util.List;

public interface GatheringConfiguration {

    List<BlockGatheringSpotConfiguration> getBlockGatheringSpots();

    List<EntityGatheringSpotConfiguration> getEntityGatheringSpots();
}
