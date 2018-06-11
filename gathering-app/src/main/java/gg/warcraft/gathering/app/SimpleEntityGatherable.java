package gg.warcraft.gathering.app;

import gg.warcraft.gathering.api.EntityGatherable;
import gg.warcraft.monolith.api.entity.EntityType;
import gg.warcraft.monolith.api.util.Duration;
import gg.warcraft.monolith.api.world.ItemType;
import gg.warcraft.monolith.api.world.Location;

import java.util.List;
import java.util.function.Supplier;

public class SimpleEntityGatherable extends AbstractGatherable implements EntityGatherable {
    private final EntityType entityType;
    private final int entityCount;
    private final Supplier<Location> spawnLocationSupplier;

    public SimpleEntityGatherable(EntityType entityType, int entityCount, Supplier<Location> spawnLocationSupplier,
                                  Supplier<List<ItemType>> dropsSupplier, Supplier<Duration> durationSupplier) {
        super(dropsSupplier, durationSupplier);
        this.entityType = entityType;
        this.entityCount = entityCount;
        this.spawnLocationSupplier = spawnLocationSupplier;
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
    public Location generateSpawnLocation() {
        return spawnLocationSupplier.get();
    }
}
