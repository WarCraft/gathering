package gg.warcraft.gathering.app.gatherable.event;

import gg.warcraft.gathering.api.gatherable.event.GatherableEntityRespawnedEvent;

import java.util.UUID;

public class SimpleGatherableEntityRespawnedEvent implements GatherableEntityRespawnedEvent {
    private final UUID entityId;
    private final String gatheringSpotId;

    public SimpleGatherableEntityRespawnedEvent(UUID entityId, String gatheringSpotId) {
        this.entityId = entityId;
        this.gatheringSpotId = gatheringSpotId;
    }

    @Override
    public UUID getEntityId() {
        return entityId;
    }

    @Override
    public String getGatheringSpotId() {
        return gatheringSpotId;
    }
}
