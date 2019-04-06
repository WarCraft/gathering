package gg.warcraft.gathering.app.spot;

import gg.warcraft.gathering.api.gatherable.EntityGatherable;
import gg.warcraft.gathering.api.spot.EntityGatheringSpot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class SimpleEntityGatheringSpot implements EntityGatheringSpot {
    private final String id;
    private final List<EntityGatherable> gatherables;
    private final Set<UUID> entityIds;

    public SimpleEntityGatheringSpot(String id, List<EntityGatherable> gatherables, Set<UUID> entityIds) {
        this.id = id;
        this.gatherables = gatherables;
        this.entityIds = entityIds;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<EntityGatherable> getEntityGatherables() {
        return new ArrayList<>(gatherables);
    }

    @Override
    public Set<UUID> getEntityIds() {
        return new HashSet<>(entityIds);
    }
}
