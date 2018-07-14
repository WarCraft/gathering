package gg.warcraft.gathering.api.spot.service;

import gg.warcraft.gathering.api.spot.BlockGatheringSpot;
import gg.warcraft.gathering.api.spot.EntityGatheringSpot;

import java.util.List;
import java.util.UUID;

/**
 * This repository is injectable, however you generally have no need for it. Use the command and query services instead.
 * <p>
 * If you feel you absolutely have to use this repository it can be used to forgo the command service and save a {@code
 * BlockGatheringSpot} or {@code EntityGatheringSpot} to the Monolith domain directly. This repository does no safety
 * checks whatsoever.
 */
public interface GatheringSpotRepository {

    /**
     * @param id The id. Can not be null.
     * @return The entity gathering spot with id. Can be null.
     */
    EntityGatheringSpot getEntityGatheringSpot(UUID id);

    /**
     * @return A list of all saved block gathering spots.
     */
    List<BlockGatheringSpot> getBlockGatheringSpots();

    /**
     * @return A list of all saved entity gathering spots.
     */
    List<EntityGatheringSpot> getEntityGatheringSpots();

    /**
     * @param blockGatheringSpot The gathering spot to save.
     */
    void save(BlockGatheringSpot blockGatheringSpot);

    /**
     * @param entityGatheringSpot The gathering spot to save.
     */
    void save(EntityGatheringSpot entityGatheringSpot);
}
