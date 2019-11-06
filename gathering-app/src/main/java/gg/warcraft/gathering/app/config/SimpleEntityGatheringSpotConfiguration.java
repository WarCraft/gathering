package gg.warcraft.gathering.app.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gg.warcraft.gathering.api.config.DropConfiguration;
import gg.warcraft.gathering.api.config.EntityGatheringSpotConfiguration;
import gg.warcraft.monolith.api.entity.EntityType;
import gg.warcraft.monolith.api.world.config.BlockLocationConfig;
import gg.warcraft.monolith.api.world.config.LocationConfig;

public class SimpleEntityGatheringSpotConfiguration implements EntityGatheringSpotConfiguration {
    private final String id;
    private final BlockLocationConfig spawnLocation;
    private final float spawnRadius;
    private final EntityType entityType;
    private final int entityCount;
    private final DropConfiguration drop;
    private final int cooldownInSeconds;

    @JsonCreator
    public SimpleEntityGatheringSpotConfiguration(@JsonProperty("id") String id,
                                                  @JsonProperty("spawnLocation") BlockLocationConfig spawnLocation,
                                                  @JsonProperty("spawnRadius") float spawnRadius,
                                                  @JsonProperty("entityType") EntityType entityType,
                                                  @JsonProperty("entityCount") int entityCount,
                                                  @JsonProperty("drop") DropConfiguration drop,
                                                  @JsonProperty("cooldownInSeconds") int cooldownInSeconds) {
        this.id = id;
        this.spawnLocation = spawnLocation;
        this.spawnRadius = spawnRadius;
        this.entityType = entityType;
        this.entityCount = entityCount;
        this.drop = drop;
        this.cooldownInSeconds = cooldownInSeconds;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public BlockLocationConfig getSpawnLocation() {
        return spawnLocation;
    }

    @Override
    public float getSpawnRadius() {
        return spawnRadius;
    }

    @Override
    public EntityType getEntityType() {
        return entityType;
    }

    @Override
    public int getEntityCount() {
        return entityCount;
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
