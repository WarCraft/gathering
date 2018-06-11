package gg.warcraft.gathering.app;

import gg.warcraft.gathering.api.Gatherable;
import gg.warcraft.monolith.api.util.Duration;
import gg.warcraft.monolith.api.world.ItemType;

import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractGatherable implements Gatherable {
    private final Supplier<List<ItemType>> dropsSupplier;
    private final Supplier<Duration> cooldownSupplier;

    public AbstractGatherable(Supplier<List<ItemType>> dropsSupplier, Supplier<Duration> cooldownSupplier) {
        this.dropsSupplier = dropsSupplier;
        this.cooldownSupplier = cooldownSupplier;
    }

    @Override
    public List<ItemType> generateDrops() {
        return dropsSupplier.get();
    }

    @Override
    public Duration generateCooldown() {
        return cooldownSupplier.get();
    }
}
