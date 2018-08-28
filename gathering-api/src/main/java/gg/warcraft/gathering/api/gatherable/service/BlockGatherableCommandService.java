package gg.warcraft.gathering.api.gatherable.service;

import gg.warcraft.gathering.api.gatherable.BlockGatherable;
import gg.warcraft.monolith.api.world.location.BlockLocation;

public interface BlockGatherableCommandService {

    void gatherBlock(BlockGatherable gatherable, BlockLocation location);

    void respawnBlock(BlockGatherable gatherable, BlockLocation location);
}
