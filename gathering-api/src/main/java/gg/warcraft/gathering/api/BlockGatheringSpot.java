package gg.warcraft.gathering.api;

import gg.warcraft.monolith.api.world.block.Block;

import java.util.List;

/**
 * A BlockGatheringSpot represents a collection of {@code BlockGatherable}s.
 */
public interface BlockGatheringSpot {

    /**
     * @return The gatherables of this spot. Never null, but can be empty. Items are never null.
     */
    List<BlockGatherable> getBlockGatherables();

    /**
     * @param block The block.
     * @return True if the block belongs to this spot, false otherwise.
     */
    boolean containsBlock(Block block);
}
