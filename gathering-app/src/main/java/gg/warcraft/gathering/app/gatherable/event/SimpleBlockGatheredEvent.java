package gg.warcraft.gathering.app.gatherable.event;

import gg.warcraft.gathering.api.gatherable.event.BlockGatheredEvent;
import gg.warcraft.monolith.api.world.block.Block;
import gg.warcraft.monolith.api.world.block.event.AbstractBlockEvent;

import java.util.UUID;

public class SimpleBlockGatheredEvent extends AbstractBlockEvent implements BlockGatheredEvent {
    private final String gatheringSpotId;
    private final UUID playerId;

    public SimpleBlockGatheredEvent(Block block, String gatheringSpotId, UUID playerId) {
        super(block);
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
