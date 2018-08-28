package gg.warcraft.gathering.app.gatherable;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import gg.warcraft.gathering.api.gatherable.EntityGatherable;
import gg.warcraft.monolith.api.entity.EntityType;
import gg.warcraft.monolith.api.item.Item;
import gg.warcraft.monolith.api.util.Duration;
import gg.warcraft.monolith.api.world.location.Location;

import java.util.List;
import java.util.function.Supplier;

public class SimpleEntityGatherable extends AbstractGatherable implements EntityGatherable {
    private final EntityType entityType;
    private final int entityCount;
    private final Supplier<Location> spawnLocationSupplier;

    @Inject
    public SimpleEntityGatherable(@Assisted EntityType entityType, @Assisted int entityCount,
                                  @Assisted Supplier<Location> spawnLocationSupplier,
                                  @Assisted Supplier<List<Item>> dropsSupplier,
                                  @Assisted Supplier<Duration> cooldownSupplier) {
        super(dropsSupplier, cooldownSupplier);
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
