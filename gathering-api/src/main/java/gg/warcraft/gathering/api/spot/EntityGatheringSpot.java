package gg.warcraft.gathering.api.spot;

import gg.warcraft.gathering.api.gatherable.EntityGatherable;

import java.util.List;
import java.util.UUID;

/**
 * A EntityGatheringSpot represents a collection of {@code EntityGatherable}s.
 */
public interface EntityGatheringSpot {

    /**
     * @return The gatherables of this spot. Never null, but can be empty. Items are never null.
     */
    List<EntityGatherable> getEntityGatherables();

    /**
     * @param entityId The id of the entity.
     * @return True if the entity belongs to this spot, false otherwise.
     */
    boolean containsEntity(UUID entityId);
}
