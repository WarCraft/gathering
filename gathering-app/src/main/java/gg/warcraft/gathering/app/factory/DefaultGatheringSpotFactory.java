package gg.warcraft.gathering.app.factory;

import gg.warcraft.gathering.api.BlockGatherable;
import gg.warcraft.gathering.api.BlockGatheringSpot;
import gg.warcraft.gathering.api.EntityGatherable;
import gg.warcraft.gathering.api.EntityGatheringSpot;
import gg.warcraft.gathering.api.factory.GatheringSpotFactory;
import gg.warcraft.gathering.app.SimpleBlockGatheringSpot;
import gg.warcraft.gathering.app.SimpleEntityGatheringSpot;
import gg.warcraft.monolith.api.world.block.Block;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class DefaultGatheringSpotFactory implements GatheringSpotFactory {

    @Override
    public BlockGatheringSpot createBlockGatheringSpot(Predicate<Block> containsBlock,
                                                       List<BlockGatherable> gatherables) {
        return new SimpleBlockGatheringSpot(containsBlock, gatherables);
    }

    @Override
    public EntityGatheringSpot createEntityGatheringSpot(Predicate<UUID> containsEntity,
                                                         List<EntityGatherable> gatherables) {
        return new SimpleEntityGatheringSpot(containsEntity, gatherables);
    }
}
