package gg.warcraft.gathering.api.gatherable.event;

import gg.warcraft.monolith.api.core.event.CancellableEvent;
import gg.warcraft.monolith.api.world.block.BlockEvent;

import java.util.UUID;

public interface BlockPreGatheredEvent extends BlockEvent, CancellableEvent {

    String getGatheringSpotId();

    UUID getPlayerId();
}
