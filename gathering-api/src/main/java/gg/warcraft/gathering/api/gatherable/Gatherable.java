package gg.warcraft.gathering.api.gatherable;

import gg.warcraft.monolith.api.util.Duration;
import gg.warcraft.monolith.api.world.item.Item;

import java.util.List;

/**
 * A Gatherable represents a block or entity that can be harvested for resources such as a STONE block in a mine, a
 * WOOD block in a forest, or a PIG on a farm. Gatherables respawn after a cooldown.
 */
public interface Gatherable {

    /**
     * Generates a list of drops for this gatherable. This method could return different items based on chance rather
     * than the same ones every time and as such will be called whenever this gatherable is gathered.
     *
     * @return the generated drops
     */
    List<Item> generateDrops();

    /**
     * Generates a cooldown for this gatherable. This method could return different durations based on chance rather
     * than the same one every time and as such will be called whenever this gatherable is gathered.
     *
     * @return the duration after which this gatherable will respawn
     */
    Duration generateCooldown();
}
