package gg.warcraft.gathering.app.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gg.warcraft.gathering.api.config.BlockGatheringSpotConfiguration;
import gg.warcraft.gathering.api.config.DropConfiguration;
import gg.warcraft.monolith.api.config.BoundingBlockBoxConfiguration;
import gg.warcraft.monolith.api.world.block.BlockType;

public class SimpleBlockGatheringSpotConfiguration implements BlockGatheringSpotConfiguration {
    private final BoundingBlockBoxConfiguration boundingBox;
    private final BlockType blockType;
    private final BlockType cooldownType;
    private final DropConfiguration drop;
    private final int cooldownInSeconds;

    @JsonCreator
    public SimpleBlockGatheringSpotConfiguration(@JsonProperty("boundingBox") BoundingBlockBoxConfiguration boundingBox,
                                                 @JsonProperty("blockType") BlockType blockType,
                                                 @JsonProperty("cooldownType") BlockType cooldownType,
                                                 @JsonProperty("drop") DropConfiguration drop,
                                                 @JsonProperty("cooldownInSeconds") int cooldownInSeconds) {
        this.boundingBox = boundingBox;
        this.blockType = blockType;
        this.cooldownType = cooldownType;
        this.drop = drop;
        this.cooldownInSeconds = cooldownInSeconds;
    }

    @Override
    public String getId() {
        return null; // Implement this method soon
    }

    @Override
    public BoundingBlockBoxConfiguration getBoundingBox() {
        return boundingBox;
    }

    @Override
    public BlockType getBlockType() {
        return blockType;
    }

    @Override
    public BlockType getCooldownType() {
        return cooldownType;
    }

    @Override
    public DropConfiguration getDrop() {
        return drop;
    }

    @Override
    public int getCooldownInSeconds() {
        return cooldownInSeconds;
    }
}
