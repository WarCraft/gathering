package gg.warcraft.gathering.api.service;

import gg.warcraft.gathering.api.BlockGatherable;
import gg.warcraft.gathering.api.EntityGatherable;
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
    void createBlockGatheringSpot(Predicate<Block> containsBlock, List<BlockGatherable> gatherables);

    /**
     * @param containsEntity The predicate to check whether or not an entity is part of this gathering spot. Generally
     *                       this is a simple location check. Can not be null.
     * @param gatherables    A list of gatherables that belong to this gathering spot. Can not be null or empty.
     */
    void createEntityGatheringSpot(Predicate<UUID> containsEntity, List<EntityGatherable> gatherables);
}
