package gg.warcraft.gathering.app.factory;

import gg.warcraft.gathering.api.BlockGatherable;
import gg.warcraft.gathering.api.BlockGatheringSpot;
import gg.warcraft.gathering.api.EntityGatherable;
import gg.warcraft.gathering.api.EntityGatheringSpot;
import gg.warcraft.gathering.api.factory.GatheringSpotFactory;
import gg.warcraft.monolith.api.world.block.Block;
import gg.warcraft.monolith.app.gathering.SimpleBlockGatheringSpot;
import gg.warcraft.monolith.app.gathering.SimpleEntityGatheringSpot;

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
