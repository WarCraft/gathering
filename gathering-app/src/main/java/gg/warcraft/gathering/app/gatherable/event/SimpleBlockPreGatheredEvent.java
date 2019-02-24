package gg.warcraft.gathering.app.gatherable.event;

import gg.warcraft.gathering.api.gatherable.event.BlockPreGatheredEvent;
import gg.warcraft.monolith.api.world.block.Block;
import gg.warcraft.monolith.api.world.block.event.AbstractBlockPreEvent;

import java.util.UUID;

public class SimpleBlockPreGatheredEvent extends AbstractBlockPreEvent implements BlockPreGatheredEvent {
    private final UUID playerId;

    public SimpleBlockPreGatheredEvent(Block block, UUID playerId, boolean cancelled) {
        super(block, cancelled);
        this.playerId = playerId;
    }

    @Override
    public UUID getPlayerId() {
        return playerId;
    }
}
