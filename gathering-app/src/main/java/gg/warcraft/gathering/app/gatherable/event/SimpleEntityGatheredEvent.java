package gg.warcraft.gathering.app.gatherable.event;

import gg.warcraft.gathering.api.gatherable.event.EntityGatheredEvent;
import gg.warcraft.monolith.api.entity.EntityType;
import gg.warcraft.monolith.api.entity.event.AbstractEntityEvent;

import java.util.UUID;

public class SimpleEntityGatheredEvent extends AbstractEntityEvent implements EntityGatheredEvent {
    private final UUID playerId;

    public SimpleEntityGatheredEvent(UUID entityId, EntityType entityType, UUID playerId) {
        super(entityId, entityType);
        this.playerId = playerId;
    }

    @Override
    public UUID getPlayerId() {
        return playerId;
    }
}
