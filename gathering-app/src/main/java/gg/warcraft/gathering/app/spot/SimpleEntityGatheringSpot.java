package gg.warcraft.gathering.app.spot;

import gg.warcraft.gathering.api.gatherable.EntityGatherable;
import gg.warcraft.gathering.api.spot.EntityGatheringSpot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class SimpleEntityGatheringSpot implements EntityGatheringSpot {
    private final Predicate<UUID> containsEntity;
    private final List<EntityGatherable> gatherables;

    public SimpleEntityGatheringSpot(Predicate<UUID> containsEntity, List<EntityGatherable> gatherables) {
        this.containsEntity = containsEntity;
        this.gatherables = gatherables;
    }

    @Override
    public List<EntityGatherable> getEntityGatherables() {
        return new ArrayList<>(gatherables);
    }

    @Override
    public boolean containsEntity(UUID entityId) {
        return containsEntity.test(entityId);
    }
}
