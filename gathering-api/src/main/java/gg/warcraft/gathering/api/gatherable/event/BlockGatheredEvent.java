package gg.warcraft.gathering.api.gatherable.event;

import gg.warcraft.monolith.api.core.event.Event;
import gg.warcraft.monolith.api.world.block.BlockEvent;

import java.util.UUID;

public interface BlockGatheredEvent extends BlockEvent, Event {

    String getGatheringSpotId();

    UUID getPlayerId();
}
