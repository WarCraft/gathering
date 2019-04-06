package gg.warcraft.gathering.api.gatherable.event;

import gg.warcraft.monolith.api.entity.event.EntityEvent;

import java.util.UUID;

public interface EntityGatheredEvent extends EntityEvent {

    String getGatheringSpotId();

    UUID getPlayerId();
}
