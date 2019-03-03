package gg.warcraft.gathering.api.config;

import gg.warcraft.monolith.api.config.BoundingBlockBoxConfiguration;
import gg.warcraft.monolith.api.world.block.BlockType;

public interface BlockGatheringSpotConfiguration {

    String getId();

    BoundingBlockBoxConfiguration getBoundingBox();

    BlockType getBlockType();

    BlockType getCooldownType();

    DropConfiguration getDrop();

    int getCooldownInSeconds();
}
