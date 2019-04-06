package gg.warcraft.gathering.app.spot.service;

import com.google.inject.Singleton;
import gg.warcraft.gathering.api.spot.BlockGatheringSpot;
import gg.warcraft.gathering.api.spot.EntityGatheringSpot;
import gg.warcraft.gathering.api.spot.service.GatheringSpotRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class DefaultGatheringSpotRepository implements GatheringSpotRepository {
    final Map<String, BlockGatheringSpot> blockGatheringSpots;
    final Map<String, EntityGatheringSpot> entityGatheringSpots;

    public DefaultGatheringSpotRepository() {
        this.blockGatheringSpots = new HashMap<>();
        this.entityGatheringSpots = new HashMap<>();
    }

    @Override
    public BlockGatheringSpot getBlockGatheringSpot(String id) {
        return blockGatheringSpots.get(id);
    }

    @Override
    public EntityGatheringSpot getEntityGatheringSpot(String id) {
        return entityGatheringSpots.get(id);
    }

    @Override
    public List<BlockGatheringSpot> getBlockGatheringSpots() {
        return new ArrayList<>(blockGatheringSpots.values());
    }

    @Override
    public List<EntityGatheringSpot> getEntityGatheringSpots() {
        return new ArrayList<>(entityGatheringSpots.values());
    }

    @Override
    public void save(BlockGatheringSpot blockGatheringSpot) {
        blockGatheringSpots.put(blockGatheringSpot.getId(), blockGatheringSpot);
    }

    @Override
    public void save(EntityGatheringSpot entityGatheringSpot) {
        entityGatheringSpots.put(entityGatheringSpot.getId(), entityGatheringSpot);
    }
}
