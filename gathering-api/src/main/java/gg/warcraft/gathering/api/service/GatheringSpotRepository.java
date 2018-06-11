package gg.warcraft.gathering.api.service;

import gg.warcraft.gathering.api.BlockGatheringSpot;
import gg.warcraft.gathering.api.EntityGatheringSpot;

import java.util.List;

/**
 * A local store for gathering spots represented as a {@link java.util.List}
 */
public interface GatheringSpotRepository {

    /**
     * Saves the given block gathering spot into a {@link java.util.List}
     *
     * @param blockGatheringSpot to be stored locally
     */
    void save(BlockGatheringSpot blockGatheringSpot);

    /**
     * Saves the given entity gathering spot into a {@link java.util.List}
     *
     * @param entityGatheringSpot to be stored locally
     */
    void save(EntityGatheringSpot entityGatheringSpot);

    /**
     * @param blockGatheringSpot
     */
    void delete(BlockGatheringSpot blockGatheringSpot);

    /**
     * @param entityGatheringSpot
     */
    void delete(EntityGatheringSpot entityGatheringSpot);

    /**
     * Gets all of the block gathering spots on the map
     *
     * @return the list of all of the block gathering spots
     */
    List<BlockGatheringSpot> getAllBlockGatheringSpots();

    /**
     * Gets all of the entity gathering spots on the map
     *
     * @return the list of the entity gathering spots
     */
    List<EntityGatheringSpot> getAllEntityGatheringSpots();
}
