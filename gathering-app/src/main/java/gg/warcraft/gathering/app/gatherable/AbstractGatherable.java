package gg.warcraft.gathering.app.gatherable;

import gg.warcraft.gathering.api.gatherable.Gatherable;
import gg.warcraft.monolith.api.item.Item;
import gg.warcraft.monolith.api.util.Duration;

import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractGatherable implements Gatherable {
    private final Supplier<List<Item>> dropsSupplier;
    private final Supplier<Duration> cooldownSupplier;

    public AbstractGatherable(Supplier<List<Item>> dropsSupplier, Supplier<Duration> cooldownSupplier) {
        this.dropsSupplier = dropsSupplier;
        this.cooldownSupplier = cooldownSupplier;
    }

    @Override
    public List<Item> generateDrops() {
        return dropsSupplier.get();
    }

    @Override
    public Duration generateCooldown() {
        return cooldownSupplier.get();
    }
}
