package gg.warcraft.gathering.api.gatherable.event;

import gg.warcraft.monolith.api.core.Event;

import java.util.UUID;

public interface GatherableEntityRespawnedEvent extends Event {

    UUID getEntityId();

    String getGatheringSpotId();
}
