package gg.warcraft.gathering.app.gatherable;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import gg.warcraft.gathering.api.gatherable.BlockGatherable;
import gg.warcraft.monolith.api.util.Duration;
import gg.warcraft.monolith.api.world.block.BlockType;
import gg.warcraft.monolith.api.world.item.Item;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SimpleBlockGatherable extends AbstractGatherable implements BlockGatherable {
    private final Predicate<Object> containsBlockType;
    private final Object cooldownBlockType;

    @Inject
    public SimpleBlockGatherable(@Assisted Predicate<Object> containsBlockData,
                                 @Assisted Object cooldownBlockData,
                                 @Assisted Supplier<List<Item>> dropsSupplier,
                                 @Assisted Supplier<Duration> cooldownSupplier) {
        super(dropsSupplier, cooldownSupplier);
        this.containsBlockType = containsBlockData;
        this.cooldownBlockType = cooldownBlockData;
    }

    @Override
    public Object getCooldownBlockData() {
        return cooldownBlockType;
    }

    public boolean containsBlockData(Object blockData) {
        return containsBlockType.test(blockData);
    }
}
