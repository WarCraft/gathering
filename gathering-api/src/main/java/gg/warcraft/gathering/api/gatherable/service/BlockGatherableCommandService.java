package gg.warcraft.gathering.api.gatherable.service;

import gg.warcraft.gathering.api.gatherable.BlockGatherable;
import gg.warcraft.monolith.api.world.location.BlockLocation;

import java.util.UUID;

public interface BlockGatherableCommandService {

    void gatherBlock(BlockGatherable gatherable, BlockLocation location, String gatheringSpotId, UUID playerId);

    void respawnBlock(BlockGatherable gatherable, BlockLocation location);
}
