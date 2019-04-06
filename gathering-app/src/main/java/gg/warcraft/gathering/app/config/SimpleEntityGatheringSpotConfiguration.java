package gg.warcraft.gathering.app.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gg.warcraft.gathering.api.config.DropConfiguration;
import gg.warcraft.gathering.api.config.EntityGatheringSpotConfiguration;
import gg.warcraft.monolith.api.config.LocationConfiguration;
import gg.warcraft.monolith.api.entity.EntityType;

public class SimpleEntityGatheringSpotConfiguration implements EntityGatheringSpotConfiguration {
    private final String id;
    private final LocationConfiguration spawnLocation;
    private final float spawnRadius;
    private final EntityType entityType;
    private final int entityCount;
    private final DropConfiguration drop;
    private final int cooldownInSeconds;

    @JsonCreator
    public SimpleEntityGatheringSpotConfiguration(@JsonProperty("id") String id,
                                                  @JsonProperty("spawnLocation") LocationConfiguration spawnLocation,
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
    public LocationConfiguration getSpawnLocation() {
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
