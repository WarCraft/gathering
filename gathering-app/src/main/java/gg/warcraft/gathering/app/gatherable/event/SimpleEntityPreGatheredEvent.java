package gg.warcraft.gathering.app.gatherable.event;

import java.util.UUID;

import gg.warcraft.gathering.api.gatherable.event.EntityPreGatheredEvent;
import gg.warcraft.monolith.api.entity.EntityType;
import gg.warcraft.monolith.api.entity.event.AbstractEntityPreEvent;

public class SimpleEntityPreGatheredEvent extends AbstractEntityPreEvent implements EntityPreGatheredEvent {

    public SimpleEntityPreGatheredEvent(UUID entityId, EntityType entityType, boolean cancelled) {
        super(entityId, entityType, cancelled);
    }
}
