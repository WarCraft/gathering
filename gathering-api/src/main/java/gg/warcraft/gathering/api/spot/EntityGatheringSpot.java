package gg.warcraft.gathering.api.spot;

import gg.warcraft.gathering.api.gatherable.EntityGatherable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * A EntityGatheringSpot represents a collection of {@code EntityGatherable}s.
 */
public interface EntityGatheringSpot extends GatheringSpot {

    /**
     * @return The id of this gathering spot. Never null or empty.
     */
    String getId();

    /**
     * @return The gatherables of this spot. Never null, but can be empty. Items are never null.
     */
    List<EntityGatherable> getEntityGatherables();

    /**
     * @return A set of entity ids that belong to this spot. Never null, but can be empty. Items are never null.
     */
    Set<UUID> getEntityIds();
}
