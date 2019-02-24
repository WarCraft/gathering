package gg.warcraft.gathering.api.gatherable.event;

import gg.warcraft.monolith.api.entity.event.EntityPreEvent;

import java.util.UUID;

public interface EntityPreGatheredEvent extends EntityPreEvent {

    UUID getPlayerId();
}
