package gg.warcraft.gathering.api.gatherable.service;

import gg.warcraft.gathering.api.gatherable.BlockGatherable;
import gg.warcraft.monolith.api.world.location.BlockLocation;

import java.util.UUID;

public interface BlockGatherableCommandService {

    /**
     * @return gathered - a flag whether the block has been gathered or not when the event has been cancelled
     */
    boolean gatherBlock(BlockGatherable gatherable, BlockLocation location, String gatheringSpotId, UUID playerId);

    void respawnBlock(BlockGatherable gatherable, BlockLocation location);
}
