package gg.warcraft.gathering.app.gatherable.event;

import gg.warcraft.gathering.api.gatherable.event.BlockGatheredEvent;
import gg.warcraft.monolith.api.world.block.Block;
import gg.warcraft.monolith.api.world.block.event.AbstractBlockEvent;

public class SimpleBlockGatheredEvent extends AbstractBlockEvent implements BlockGatheredEvent {

    public SimpleBlockGatheredEvent(Block block) {
        super(block);
    }
}
