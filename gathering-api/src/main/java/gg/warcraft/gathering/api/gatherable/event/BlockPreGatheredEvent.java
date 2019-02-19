package gg.warcraft.gathering.api.gatherable.event;

import gg.warcraft.monolith.api.world.block.event.BlockPreEvent;

import java.util.UUID;

public interface BlockPreGatheredEvent extends BlockPreEvent {

    UUID getPlayerId();
}
