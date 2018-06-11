package gg.warcraft.gathering.api;

import gg.warcraft.monolith.api.world.block.BlockType;

/**
 * An block gatherable is a block that is specified as a gatherable object.
 */
public interface BlockGatherable extends Gatherable {

    /**
     * @return the material data this gatherable will appear as while it is on cooldown
     */
    BlockType getCooldownBlockType();

    /**
     * Checks whether a material data belongs to this block gatherable.
     *
     * @param blockType the material data to check
     * @return true if this material data belongs to this block gatherable, false otherwise
     */
    boolean containsBlockType(BlockType blockType);
}
