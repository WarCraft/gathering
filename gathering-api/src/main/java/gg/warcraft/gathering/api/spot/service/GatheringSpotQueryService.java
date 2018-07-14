package gg.warcraft.gathering.api.spot.service;

import gg.warcraft.gathering.api.spot.BlockGatheringSpot;
import gg.warcraft.gathering.api.spot.EntityGatheringSpot;

import java.util.List;
import java.util.UUID;

/**
 * This service is injectable.
 * <p>
 * The GatheringSpotQueryService serves as a point of entry into the gathering implementation. It provides methods to
 * query the Monolith domain for all currently active {@code BlockGatheringSpot} and
 * {@code EntityGatheringSpot}s.
 */
public interface GatheringSpotQueryService {

    /**
     * @param id The id. Can not be null.
     * @return The block gathering spot with id. Can be null.
     */
    BlockGatheringSpot getBlockGatheringSpot(UUID id);

    /**
     * @param id The id. Can not be null.
     * @return The entity gathering spot with id. Can be null.
     */
    EntityGatheringSpot getEntityGatheringSpot(UUID id);

    /**
     * @return A list with all currently active block gathering spots. Never null, but can be empty. Items are never
     * null.
     */
    List<BlockGatheringSpot> getBlockGatheringSpots();

    /**
     * @return A list with all currently active entity gathering spots. Never null, but can be empty. Items are never
     * null.
     */
    List<EntityGatheringSpot> getEntityGatheringSpots();
}
