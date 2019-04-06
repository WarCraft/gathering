package gg.warcraft.gathering.app.gatherable.event;

import gg.warcraft.gathering.api.gatherable.event.EntityGatheredEvent;
import gg.warcraft.monolith.api.entity.EntityType;
import gg.warcraft.monolith.api.entity.event.AbstractEntityEvent;

import java.util.UUID;

public class SimpleEntityGatheredEvent extends AbstractEntityEvent implements EntityGatheredEvent {
    private final String gatheringSpotId;
    private final UUID playerId;

    public SimpleEntityGatheredEvent(UUID entityId, EntityType entityType, String gatheringSpotId, UUID playerId) {
        super(entityId, entityType);
        this.gatheringSpotId = gatheringSpotId;
        this.playerId = playerId;
    }

    @Override
    public String getGatheringSpotId() {
        return gatheringSpotId;
    }

    @Override
    public UUID getPlayerId() {
        return playerId;
    }
}
