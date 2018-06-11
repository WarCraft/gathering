package gg.warcraft.gathering.api;

import gg.warcraft.monolith.api.entity.EntityType;
import gg.warcraft.monolith.api.world.Location;

/**
 * An entity gatherable is an entity that is specified as a gatherable object.
 */
public interface EntityGatherable extends Gatherable {

    /**
     * @return the entity type of this entity gatherable
     */
    EntityType getEntityType();

    /**
     * @return the number of entities this entity gatherable provides
     */
    int getEntityCount();

    /**
     * Generates a spawn location for this entity gatherable. This method could return different locations based on
     * chance rather than the same one every time and as such will be called whenever an entity spawns.
     *
     * @return the location where the new entity should spawn
     */
    Location generateSpawnLocation();
}
