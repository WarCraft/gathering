package gg.warcraft.gathering.app.service;

import com.google.inject.Inject;
import gg.warcraft.gathering.api.BlockGatherable;
import gg.warcraft.gathering.api.BlockGatheringSpot;
import gg.warcraft.gathering.api.EntityGatherable;
import gg.warcraft.gathering.api.EntityGatheringSpot;
import gg.warcraft.gathering.api.service.GatheringSpotCommandService;
import gg.warcraft.gathering.api.service.GatheringSpotRepository;
import gg.warcraft.gathering.app.SimpleBlockGatheringSpot;
import gg.warcraft.gathering.app.SimpleEntityGatheringSpot;
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
