package gg.warcraft.gathering.api.gatherable.event;

import gg.warcraft.monolith.api.core.event.Event;

import java.util.UUID;

public interface GatherableEntityRespawnedEvent extends Event {

    String getGatheringSpotId();

    UUID getEntityId();
}
