package gg.warcraft.gathering.api.spot.service;

import gg.warcraft.gathering.api.spot.BlockGatheringSpot;
import gg.warcraft.gathering.api.spot.EntityGatheringSpot;

import java.util.List;

/**
 * This service is injectable.
 * <p>
 * The GatheringSpotQueryService serves as a point of entry into the gathering implementation. It provides methods to
 * query the Monolith domain for all currently active {@code BlockGatheringSpot} and
 * {@code EntityGatheringSpot}s.
 */
public interface GatheringSpotQueryService {

    /**
     * @return A list with all currently active block gathering spots. Never null, but can be empty. Items are never
     * null.
     */
    List<BlockGatheringSpot> getAllBlockGatheringSpots();

    /**
     * @return A list with all currently active entity gathering spots. Never null, but can be empty. Items are never
     * null.
     */
    List<EntityGatheringSpot> getAllEntityGatheringSpots();
}
