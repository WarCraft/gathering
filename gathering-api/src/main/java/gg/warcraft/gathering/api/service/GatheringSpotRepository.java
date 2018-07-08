package gg.warcraft.gathering.api.service;

import gg.warcraft.gathering.api.BlockGatheringSpot;
import gg.warcraft.gathering.api.EntityGatheringSpot;

import java.util.List;

/**
 * This repository is injectable, however you generally have no need for it. Use the command and query services instead.
 * <p>
 * If you feel you absolutely have to use this repository it can be used to forgo the command service and save a {@code
 * BlockGatheringSpot} or {@code EntityGatheringSpot} to the Monolith domain directly. This repository does no safety
 * checks whatsoever.
 */
public interface GatheringSpotRepository {

    /**
     * @return A list of all saved block gathering spots.
     */
    List<BlockGatheringSpot> getAllBlockGatheringSpots();

    /**
     * @return A list of all saved entity gathering spots.
     */
    List<EntityGatheringSpot> getAllEntityGatheringSpots();

    /**
     * @param blockGatheringSpot The gathering spot to save.
     */
    void save(BlockGatheringSpot blockGatheringSpot);

    /**
     * @param entityGatheringSpot The gathering spot to save.
     */
    void save(EntityGatheringSpot entityGatheringSpot);
}
