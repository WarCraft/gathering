package gg.warcraft.gathering.app.gatherable.event;

import gg.warcraft.gathering.api.gatherable.event.GatherableEntityRespawnedEvent;

import java.util.UUID;

public class SimpleGatherableEntityRespawnedEvent implements GatherableEntityRespawnedEvent {
    private final UUID entityId;
    private final UUID gatheringSpotId;

    public SimpleGatherableEntityRespawnedEvent(UUID entityId, UUID gatheringSpotId) {
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
