package gg.warcraft.gathering.app.service;

import com.google.inject.Singleton;
import gg.warcraft.gathering.api.BlockGatheringSpot;
import gg.warcraft.gathering.api.EntityGatheringSpot;
import gg.warcraft.gathering.api.service.GatheringSpotRepository;

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
    public void save(BlockGatheringSpot blockGatheringSpot) {
        blockGatheringSpots.add(blockGatheringSpot);
    }

    @Override
    public void save(EntityGatheringSpot entityGatheringSpot) {
        entityGatheringSpots.add(entityGatheringSpot);
    }

    @Override
    public void delete(BlockGatheringSpot blockGatheringSpot) {
        blockGatheringSpots.remove(blockGatheringSpot);
    }

    @Override
    public void delete(EntityGatheringSpot entityGatheringSpot) {
        entityGatheringSpots.remove(entityGatheringSpot);
    }

    @Override
    public List<BlockGatheringSpot> getAllBlockGatheringSpots() {
        return new ArrayList<>(blockGatheringSpots);
    }

    @Override
    public List<EntityGatheringSpot> getAllEntityGatheringSpots() {
        return new ArrayList<>(entityGatheringSpots);
    }
}
