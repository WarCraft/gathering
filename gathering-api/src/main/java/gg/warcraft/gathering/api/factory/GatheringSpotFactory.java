package gg.warcraft.gathering.api.factory;

import gg.warcraft.gathering.api.BlockGatherable;
import gg.warcraft.gathering.api.BlockGatheringSpot;
import gg.warcraft.gathering.api.EntityGatherable;
import gg.warcraft.gathering.api.EntityGatheringSpot;
import gg.warcraft.monolith.api.world.block.Block;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface GatheringSpotFactory {

    BlockGatheringSpot createBlockGatheringSpot(Predicate<Block> containsBlock, List<BlockGatherable> gatherables);

    EntityGatheringSpot createEntityGatheringSpot(Predicate<UUID> containsEntity, List<EntityGatherable> gatherables);
}
