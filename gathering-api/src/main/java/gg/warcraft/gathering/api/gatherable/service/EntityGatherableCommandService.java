package gg.warcraft.gathering.api.gatherable.service;

import gg.warcraft.gathering.api.gatherable.BlockGatherable;
import gg.warcraft.gathering.api.gatherable.EntityGatherable;
import gg.warcraft.monolith.api.world.BlockLocation;

import java.util.UUID;

public interface EntityGatherableCommandService {

    /**
     * Look in {@link BlockGatherableCommandService#gatherBlock(BlockGatherable, BlockLocation, String, UUID)}
     * for more information
     */
    boolean gatherEntity(EntityGatherable gatherable, UUID entityId, String gatheringSpotId, UUID playerId);

    void respawnEntity(EntityGatherable gatherable, String gatheringSpotId);

    void removeAllEntities();
}
