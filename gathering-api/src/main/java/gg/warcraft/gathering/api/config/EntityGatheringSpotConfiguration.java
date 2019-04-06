package gg.warcraft.gathering.api.config;

import gg.warcraft.monolith.api.config.LocationConfiguration;
import gg.warcraft.monolith.api.entity.EntityType;

public interface EntityGatheringSpotConfiguration {

    String getId();

    LocationConfiguration getSpawnLocation();

    float getSpawnRadius();

    EntityType getEntityType();

    int getEntityCount();

    DropConfiguration getDrop();

    int getCooldownInSeconds();
}
