package gg.warcraft.gathering.app.service;

import com.google.inject.Inject;
import gg.warcraft.gathering.api.BlockGatheringSpot;
import gg.warcraft.gathering.api.EntityGatheringSpot;
import gg.warcraft.gathering.api.service.GatheringSpotQueryService;
import gg.warcraft.gathering.api.service.GatheringSpotRepository;

import java.util.List;

public class DefaultGatheringSpotQueryService implements GatheringSpotQueryService {
    private final GatheringSpotRepository gatheringSpotRepository;

    @Inject
    public DefaultGatheringSpotQueryService(GatheringSpotRepository gatheringSpotRepository) {
        this.gatheringSpotRepository = gatheringSpotRepository;
    }

    @Override
    public List<BlockGatheringSpot> getAllBlockGatheringSpots() {
        return gatheringSpotRepository.getAllBlockGatheringSpots();
    }

    @Override
    public List<EntityGatheringSpot> getAllEntityGatheringSpots() {
        return gatheringSpotRepository.getAllEntityGatheringSpots();
    }
}
