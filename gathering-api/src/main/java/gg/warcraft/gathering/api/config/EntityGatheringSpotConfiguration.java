package gg.warcraft.gathering.api.config;

import gg.warcraft.monolith.api.entity.EntityType;
import gg.warcraft.monolith.api.world.config.LocationConfig;

public interface EntityGatheringSpotConfiguration {

    String getId();

    LocationConfig getSpawnLocation();

    float getSpawnRadius();

    EntityType getEntityType();

    int getEntityCount();

    DropConfiguration getDrop();

    int getCooldownInSeconds();
}
