package gg.warcraft.gathering.app.gatherable.event;

import java.util.UUID;

import gg.warcraft.gathering.api.gatherable.event.EntityGatheredEvent;
import gg.warcraft.monolith.api.entity.EntityType;
import gg.warcraft.monolith.api.entity.event.AbstractEntityEvent;

public class SimpleEntityGatheredEvent extends AbstractEntityEvent implements EntityGatheredEvent {

    public SimpleEntityGatheredEvent(UUID entityId, EntityType entityType) {
        super(entityId, entityType);
    }
}
