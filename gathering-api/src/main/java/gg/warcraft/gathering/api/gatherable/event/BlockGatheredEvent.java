package gg.warcraft.gathering.api.gatherable.event;

import gg.warcraft.monolith.api.world.block.event.BlockEvent;

import java.util.UUID;

public interface BlockGatheredEvent extends BlockEvent {

    UUID getPlayerId();
}
