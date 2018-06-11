package gg.warcraft.gathering.api;

import gg.warcraft.monolith.api.world.block.Block;

import java.util.List;

/**
 * A block gathering spot is a spot on the map that contains gatherable blocks
 */
public interface BlockGatheringSpot {

    /**
     * @return the block gatherables of this gathering spot
     */
    List<BlockGatherable> getBlockGatherables();

    /**
     * Checks whether a block belongs to this block gathering spot.
     *
     * @param block the block to check
     * @return true if this block belongs to this block gathering spot, false otherwise
     */
    boolean containsBlock(Block block);
}
