package gg.warcraft.gathering.app.spot.service;

import com.google.inject.Inject;
import gg.warcraft.gathering.api.gatherable.BlockGatherable;
import gg.warcraft.gathering.api.gatherable.EntityGatherable;
import gg.warcraft.gathering.api.spot.BlockGatheringSpot;
import gg.warcraft.gathering.api.spot.EntityGatheringSpot;
import gg.warcraft.gathering.api.spot.service.GatheringSpotCommandService;
import gg.warcraft.gathering.api.spot.service.GatheringSpotRepository;
import gg.warcraft.gathering.app.spot.SimpleBlockGatheringSpot;
import gg.warcraft.gathering.app.spot.SimpleEntityGatheringSpot;
import gg.warcraft.monolith.api.world.block.Block;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class DefaultGatheringSpotCommandService implements GatheringSpotCommandService {
    private final GatheringSpotRepository gatheringSpotRepository;

    @Inject
    public DefaultGatheringSpotCommandService(GatheringSpotRepository gatheringSpotRepository) {
        this.gatheringSpotRepository = gatheringSpotRepository;
    }

    @Override
    public void createBlockGatheringSpot(Predicate<Block> containsBlock, List<BlockGatherable> gatherables) {
        BlockGatheringSpot gatheringSpot = new SimpleBlockGatheringSpot(containsBlock, gatherables);
        gatheringSpotRepository.save(gatheringSpot);
    }

    @Override
    public void createEntityGatheringSpot(Predicate<UUID> containsEntity, List<EntityGatherable> gatherables) {
        EntityGatheringSpot gatheringSpot = new SimpleEntityGatheringSpot(containsEntity, gatherables);
        gatheringSpotRepository.save(gatheringSpot);
    }
}
