package gg.warcraft.gathering.app.gatherable.event;

import gg.warcraft.gathering.api.gatherable.event.EntityPreGatheredEvent;
import gg.warcraft.monolith.api.entity.EntityType;
import gg.warcraft.monolith.api.entity.event.AbstractEntityPreEvent;

import java.util.UUID;

public class SimpleEntityPreGatheredEvent extends AbstractEntityPreEvent implements EntityPreGatheredEvent {
    private final UUID playerId;

    public SimpleEntityPreGatheredEvent(UUID entityId, EntityType entityType,
                                        UUID playerId, boolean cancelled) {
        super(entityId, entityType, cancelled);
        this.playerId = playerId;
    }

    @Override
    public UUID getPlayerId() {
        return playerId;
    }
}
