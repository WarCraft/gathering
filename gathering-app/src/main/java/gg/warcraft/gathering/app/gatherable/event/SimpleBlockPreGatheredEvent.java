package gg.warcraft.gathering.app.gatherable.event;

import gg.warcraft.gathering.api.gatherable.event.BlockPreGatheredEvent;
import gg.warcraft.monolith.api.world.block.Block;
import gg.warcraft.monolith.api.world.block.event.AbstractBlockPreEvent;

public class SimpleBlockPreGatheredEvent extends AbstractBlockPreEvent implements BlockPreGatheredEvent {

    public SimpleBlockPreGatheredEvent(Block block, boolean cancelled) {
        super(block, cancelled);
    }
}
