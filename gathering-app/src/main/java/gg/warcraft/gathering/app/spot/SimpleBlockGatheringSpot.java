package gg.warcraft.gathering.app.spot;

import gg.warcraft.gathering.api.gatherable.BlockGatherable;
import gg.warcraft.gathering.api.spot.BlockGatheringSpot;
import gg.warcraft.monolith.api.world.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SimpleBlockGatheringSpot implements BlockGatheringSpot {
    private final String id;
    private final Predicate<Block> containsBlock;
    private final List<BlockGatherable> gatherables;

    public SimpleBlockGatheringSpot(String id, Predicate<Block> containsBlock, List<BlockGatherable> gatherables) {
        this.id = id;
        this.containsBlock = containsBlock;
        this.gatherables = gatherables;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<BlockGatherable> getBlockGatherables() {
        return new ArrayList<>(gatherables);
    }

    @Override
    public boolean containsBlock(Block block) {
        return containsBlock.test(block);
    }

    @Override
    public String getId() {
        return id;
    }
}
