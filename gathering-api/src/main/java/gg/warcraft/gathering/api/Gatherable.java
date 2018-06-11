package gg.warcraft.gathering.api;

import gg.warcraft.monolith.api.util.Duration;
import gg.warcraft.monolith.api.world.ItemType;

import java.util.List;

/**
 * A specified gatherable object that is respawnable
 */
public interface Gatherable {

    /**
     * Generates a list of drops for this gatherable. This method could return different items based on chance rather
     * than the same ones every time and as such will be called whenever this gatherable is gathered.
     *
     * @return the generated drops
     */
    List<ItemType> generateDrops();

    /**
     * Generates a cooldown for this gatherable. This method could return different durations based on chance rather
     * than the same one every time and as such will be called whenever this gatherable is gathered.
     *
     * @return the duration after which this gatherable will respawn
     */
    Duration generateCooldown();
}
