package gg.warcraft.gathering.api.gatherable;

import gg.warcraft.monolith.api.world.block.Block;
import gg.warcraft.monolith.api.world.block.BlockTypeVariantOrState;

/**
 * A BlockGatherable represents a block that can be harvested for resources such as a STONE block in a mine or a WOOD
 * block in a forest. Gatherables respawn after a cooldown.
 */
public interface BlockGatherable extends Gatherable {

    /**
     * @return The type this gatherable appears as while on cooldown. Never null.
     */
    BlockTypeVariantOrState getCooldownBlockData();

    /**
     * @param block The type. Can not be null.
     * @return True if this gatherable contains the type, false otherwise.
     */
    boolean containsBlock(Block block);
}
