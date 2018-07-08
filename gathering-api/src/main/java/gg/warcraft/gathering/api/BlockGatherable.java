package gg.warcraft.gathering.api;

import gg.warcraft.monolith.api.world.block.BlockType;

/**
 * A BlockGatherable represents a block that can be harvested for resources such as a STONE block in a mine or a WOOD
 * block in a forest. Gatherables respawn after a cooldown.
 */
public interface BlockGatherable extends Gatherable {

    /**
     * @return The type this gatherable appears as while on cooldown. Never null.
     */
    BlockType getCooldownBlockType();

    /**
     * @param blockType The type. Can not be null.
     * @return True if this gatherable contains the type, false otherwise.
     */
    boolean containsBlockType(BlockType blockType);
}
