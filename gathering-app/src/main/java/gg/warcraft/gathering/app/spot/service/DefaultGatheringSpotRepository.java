package gg.warcraft.gathering.app.spot.service;

import com.google.inject.Singleton;
import gg.warcraft.gathering.api.spot.BlockGatheringSpot;
import gg.warcraft.gathering.api.spot.EntityGatheringSpot;
import gg.warcraft.gathering.api.spot.service.GatheringSpotRepository;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class DefaultGatheringSpotRepository implements GatheringSpotRepository {
    final List<BlockGatheringSpot> blockGatheringSpots;
    final List<EntityGatheringSpot> entityGatheringSpots;

    public DefaultGatheringSpotRepository() {
        this.blockGatheringSpots = new ArrayList<>();
        this.entityGatheringSpots = new ArrayList<>();
    }

    @Override
    public List<BlockGatheringSpot> getAllBlockGatheringSpots() {
        return new ArrayList<>(blockGatheringSpots);
    }

    @Override
    public List<EntityGatheringSpot> getAllEntityGatheringSpots() {
        return new ArrayList<>(entityGatheringSpots);
    }

    @Override
    public void save(BlockGatheringSpot blockGatheringSpot) {
        blockGatheringSpots.add(blockGatheringSpot);
    }

    @Override
    public void save(EntityGatheringSpot entityGatheringSpot) {
        entityGatheringSpots.add(entityGatheringSpot);
    }
}
