package gg.warcraft.gathering.api.config;

import gg.warcraft.monolith.api.config.BoundingBlockBoxConfiguration;

public interface BlockGatheringSpotConfiguration {

    String getId();

    BoundingBlockBoxConfiguration getBoundingBox();

    String getBlockType();

    String getCooldownType();

    DropConfiguration getDrop();

    int getCooldownInSeconds();
}
