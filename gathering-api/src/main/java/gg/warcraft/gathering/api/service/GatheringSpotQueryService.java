package gg.warcraft.gathering.api.service;

import gg.warcraft.gathering.api.BlockGatheringSpot;
import gg.warcraft.gathering.api.EntityGatheringSpot;

import java.util.List;

/**
 * Query service for getting gathering spots for either block gathering spots or entity gathering spots
 */
public interface GatheringSpotQueryService {

    /**
     * Gets all of the block gathering spots
     *
     * @return the list of all block gathering spots
     */
    List<BlockGatheringSpot> getAllBlockGatheringSpots();

    /**
     * Gets all of the entity gathering spots
     *
     * @return the list of all entity gathering spots
     */
    List<EntityGatheringSpot> getAllEntityGatheringSpots();
}
