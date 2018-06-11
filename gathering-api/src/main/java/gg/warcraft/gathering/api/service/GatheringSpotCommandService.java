package gg.warcraft.gathering.api.service;

import gg.warcraft.gathering.api.BlockGatheringSpot;
import gg.warcraft.gathering.api.EntityGatheringSpot;

/**
 * Command service for any create operations on a {@link BlockGatheringSpot} and a {@link EntityGatheringSpot}
 */
public interface GatheringSpotCommandService {

    /**
     * Adds a block gathering spot from the service.
     *
     * @param blockGatheringSpot the block gathering spot to remove
     */
    void addBlockGatheringSpot(BlockGatheringSpot blockGatheringSpot);

    /**
     * Removes a block gathering spot from the service.
     *
     * @param blockGatheringSpot the block gathering spot to remove
     */
    void removeBlockGatheringSpot(BlockGatheringSpot blockGatheringSpot);

    /**
     * Adds an entity gathering spot from the service.
     *
     * @param entityGatheringSpot the entity gathering spot to remove
     */
    void addEntityGatheringSpot(EntityGatheringSpot entityGatheringSpot);

    /**
     * Removes an entity gathering spot from the service.
     *
     * @param entityGatheringSpot the entity gathering spot to remove
     */
    void removeEntityGatheringSpot(EntityGatheringSpot entityGatheringSpot);
}
