package gg.warcraft.gathering.app.gatherable.event;

import gg.warcraft.gathering.api.gatherable.event.GatherableEntitySpawnedEvent;

import java.util.UUID;

public class SimpleGatherableEntitySpawnedEvent implements GatherableEntitySpawnedEvent {
    private final UUID entityId;
    private final UUID gatheringSpotId;

    public SimpleGatherableEntitySpawnedEvent(UUID entityId, UUID gatheringSpotId) {
        this.entityId = entityId;
        this.gatheringSpotId = gatheringSpotId;
    }

    @Override
    public UUID getEntityId() {
        return entityId;
    }

    @Override
    public UUID getGatheringSpotId() {
        return gatheringSpotId;
    }
}
