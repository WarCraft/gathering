package gg.warcraft.gathering.app;

import gg.warcraft.gathering.api.EntityGatherable;
import gg.warcraft.gathering.api.EntityGatheringSpot;

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
