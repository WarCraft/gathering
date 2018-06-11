package gg.warcraft.gathering.app;

import gg.warcraft.gathering.api.BlockGatherable;
import gg.warcraft.gathering.api.BlockGatheringSpot;
import gg.warcraft.monolith.api.world.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SimpleBlockGatheringSpot implements BlockGatheringSpot {
    private final Predicate<Block> containsBlock;
    private final List<BlockGatherable> gatherables;

    public SimpleBlockGatheringSpot(Predicate<Block> containsBlock, List<BlockGatherable> gatherables) {
        this.containsBlock = containsBlock;
        this.gatherables = gatherables;
    }

    @Override
    public List<BlockGatherable> getBlockGatherables() {
        return new ArrayList<>(gatherables);
    }

    @Override
    public boolean containsBlock(Block block) {
        return containsBlock.test(block);
    }
}
