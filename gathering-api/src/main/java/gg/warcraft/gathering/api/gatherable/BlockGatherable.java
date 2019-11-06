package gg.warcraft.gathering.api.gatherable;

/**
 * A BlockGatherable represents a block that can be harvested for resources such as a STONE block in a mine or a WOOD
 * block in a forest. Gatherables respawn after a cooldown.
 */
public interface BlockGatherable extends Gatherable {

    /**
     * @return The type this gatherable appears as while on cooldown. Never null.
     */
    Object getCooldownBlockData();

    /**
     * @param blockType The type. Can not be null.
     * @return True if this gatherable contains the type, false otherwise.
     */
    boolean containsBlockData(Object blockData);
}
