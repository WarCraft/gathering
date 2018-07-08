package gg.warcraft.gathering.api;

import gg.warcraft.monolith.api.entity.EntityType;
import gg.warcraft.monolith.api.world.Location;

/**
 * An EntityGatherable represents an entity that can be harvested for resources such as a PIG on a farm. Gatherables
 * respawn after a cooldown.
 */
public interface EntityGatherable extends Gatherable {

    /**
     * @return The entity type of this gatherable. Never null.
     */
    EntityType getEntityType();

    /**
     * @return The number of entities this gatherable provides.
     */
    int getEntityCount();

    /**
     * Generates a spawn location for this gatherable. This method could return different locations based on chance
     * rather than the same one every time and as such will be called whenever an entity respawns.
     *
     * @return The spawn location. Never null.
     */
    Location generateSpawnLocation();
}
