package gg.warcraft.gathering.api.gatherable.event;

import gg.warcraft.monolith.api.core.event.CancellableEvent;
import gg.warcraft.monolith.api.entity.EntityEvent;

import java.util.UUID;

public interface EntityPreGatheredEvent extends EntityEvent, CancellableEvent {

    String getGatheringSpotId();

    UUID getPlayerId();
}
