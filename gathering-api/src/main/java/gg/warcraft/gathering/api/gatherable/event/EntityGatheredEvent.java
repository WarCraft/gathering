package gg.warcraft.gathering.api.gatherable.event;

import gg.warcraft.monolith.api.core.event.Event;
import gg.warcraft.monolith.api.entity.EntityEvent;

import java.util.UUID;

public interface EntityGatheredEvent extends EntityEvent, Event {

    String getGatheringSpotId();

    UUID getPlayerId();
}
