package gg.warcraft.gathering.app.gatherable.event;

import gg.warcraft.gathering.api.gatherable.event.BlockPreGatheredEvent;
import gg.warcraft.monolith.api.world.block.Block;
import gg.warcraft.monolith.api.world.block.event.AbstractBlockPreEvent;

import java.util.UUID;

public class SimpleBlockPreGatheredEvent extends AbstractBlockPreEvent implements BlockPreGatheredEvent {
    private final String gatheringSpotId;
    private final UUID playerId;

    public SimpleBlockPreGatheredEvent(Block block, String gatheringSpotId, UUID playerId, boolean cancelled) {
        super(block, cancelled);
        this.gatheringSpotId = gatheringSpotId;
        this.playerId = playerId;
    }

    @Override
    public String getGatheringSpotId() {
        return gatheringSpotId;
    }

    @Override
    public UUID getPlayerId() {
        return playerId;
    }
}
