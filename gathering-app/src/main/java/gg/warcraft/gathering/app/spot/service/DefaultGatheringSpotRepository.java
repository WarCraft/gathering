package gg.warcraft.gathering.app.spot.service;

import com.google.inject.Singleton;
import gg.warcraft.gathering.api.spot.BlockGatheringSpot;
import gg.warcraft.gathering.api.spot.EntityGatheringSpot;
import gg.warcraft.gathering.api.spot.service.GatheringSpotRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Singleton
public class DefaultGatheringSpotRepository implements GatheringSpotRepository {
    final List<BlockGatheringSpot> blockGatheringSpots;
    final Map<UUID, EntityGatheringSpot> entityGatheringSpots;

    public DefaultGatheringSpotRepository() {
        this.blockGatheringSpots = new ArrayList<>();
        this.entityGatheringSpots = new HashMap<>();
    }

    @Override
    public EntityGatheringSpot getEntityGatheringSpot(UUID id) {
        return entityGatheringSpots.get(id);
    }

    @Override
    public List<BlockGatheringSpot> getBlockGatheringSpots() {
        return new ArrayList<>(blockGatheringSpots);
    }

    @Override
    public List<EntityGatheringSpot> getEntityGatheringSpots() {
        return new ArrayList<>(entityGatheringSpots.values());
    }

    @Override
    public void save(BlockGatheringSpot blockGatheringSpot) {
        blockGatheringSpots.add(blockGatheringSpot);
    }

    @Override
    public void save(EntityGatheringSpot entityGatheringSpot) {
        entityGatheringSpots.put(entityGatheringSpot.getId(), entityGatheringSpot);
    }
}
