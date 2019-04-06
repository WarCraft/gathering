package gg.warcraft.gathering.api.spot.service;

import gg.warcraft.gathering.api.gatherable.BlockGatherable;
import gg.warcraft.gathering.api.gatherable.EntityGatherable;
import gg.warcraft.monolith.api.world.block.Block;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * This service is injectable.
 * <p>
 * The GatheringSpotCommandService serves as a point of entry into the gathering implementation. It provides methods to
 * create new {@code BlockGatheringSpot} and {@code EntityGatheringSpot}s.
 */
public interface GatheringSpotCommandService {

    /**
     * @param containsBlock The predicate to check whether or not a block is part of this gathering spot. Generally this
     *                      is a simple {@code BoundingBlockBox} check. Can not be null.
     * @param gatherables   A list of gatherables that belong to this gathering spot. Can not be null or empty.
     */
    String createBlockGatheringSpot(Predicate<Block> containsBlock, List<BlockGatherable> gatherables);

    /**
     * @param gatherables A list of gatherables that belong to this gathering spot. Can not be null or empty.
     */
    String createEntityGatheringSpot(List<EntityGatherable> gatherables);


    void addEntityToGatheringSpot(String gatheringSpotId, UUID entityId);

    void removeEntityFromGatheringSpot(String gatheringSpotId, UUID entityId);
}
