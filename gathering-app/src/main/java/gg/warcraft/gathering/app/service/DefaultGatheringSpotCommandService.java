package gg.warcraft.gathering.app.service;

import com.google.inject.Inject;
import gg.warcraft.gathering.api.BlockGatheringSpot;
import gg.warcraft.gathering.api.EntityGatheringSpot;
import gg.warcraft.gathering.api.service.GatheringSpotCommandService;
import gg.warcraft.gathering.api.service.GatheringSpotRepository;

public class DefaultGatheringSpotCommandService implements GatheringSpotCommandService {
    private final GatheringSpotRepository gatheringSpotRepository;

    @Inject
    public DefaultGatheringSpotCommandService(GatheringSpotRepository gatheringSpotRepository) {
        this.gatheringSpotRepository = gatheringSpotRepository;
    }

    @Override
    public void addBlockGatheringSpot(BlockGatheringSpot blockGatheringSpot) {
        gatheringSpotRepository.save(blockGatheringSpot);
    }

    @Override
    public void removeBlockGatheringSpot(BlockGatheringSpot blockGatheringSpot) {
        gatheringSpotRepository.delete(blockGatheringSpot);
    }

    @Override
    public void addEntityGatheringSpot(EntityGatheringSpot entityGatheringSpot) {
        // TODO spawn entities to max entities count
        gatheringSpotRepository.save(entityGatheringSpot);
    }

    @Override
    public void removeEntityGatheringSpot(EntityGatheringSpot entityGatheringSpot) {
        gatheringSpotRepository.delete(entityGatheringSpot);
    }
}
