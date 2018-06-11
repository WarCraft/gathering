package gg.warcraft.gathering.api;

import java.util.List;
import java.util.UUID;

/**
 * An entity gathering spot is a spot on the map that contains gatherable entities
 */
public interface EntityGatheringSpot {

    /**
     * @return the entity gatherables of this gathering spot
     */
    List<EntityGatherable> getEntityGatherables();

    /**
     * Checks whether an entity belongs to this entity gathering spot.
     *
     * @param entityId the entity to check
     * @return true if this entity belongs to this entity gathering spot, false otherwise
     */
    boolean containsEntity(UUID entityId);
}
