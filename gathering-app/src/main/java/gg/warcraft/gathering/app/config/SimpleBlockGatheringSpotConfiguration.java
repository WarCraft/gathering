package gg.warcraft.gathering.app.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gg.warcraft.gathering.api.config.BlockGatheringSpotConfiguration;
import gg.warcraft.gathering.api.config.DropConfiguration;
import gg.warcraft.monolith.api.config.BoundingBlockBoxConfiguration;

public class SimpleBlockGatheringSpotConfiguration implements BlockGatheringSpotConfiguration {
    private final String id;
    private final BoundingBlockBoxConfiguration boundingBox;
    private final String blockType;
    private final String cooldownType;
    private final DropConfiguration drop;
    private final int cooldownInSeconds;

    @JsonCreator
    public SimpleBlockGatheringSpotConfiguration(@JsonProperty("id") String id,
                                                 @JsonProperty("boundingBox") BoundingBlockBoxConfiguration boundingBox,
                                                 @JsonProperty("blockType") String blockType,
                                                 @JsonProperty("cooldownType") String cooldownType,
                                                 @JsonProperty("drop") DropConfiguration drop,
                                                 @JsonProperty("cooldownInSeconds") int cooldownInSeconds) {
        this.id = id;
        this.boundingBox = boundingBox;
        this.blockType = blockType;
        this.cooldownType = cooldownType;
        this.drop = drop;
        this.cooldownInSeconds = cooldownInSeconds;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public BoundingBlockBoxConfiguration getBoundingBox() {
        return boundingBox;
    }

    @Override
    public String getBlockType() {
        return blockType;
    }

    @Override
    public String getCooldownType() {
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
