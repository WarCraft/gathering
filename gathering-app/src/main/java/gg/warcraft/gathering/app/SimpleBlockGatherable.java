package gg.warcraft.gathering.app;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import gg.warcraft.gathering.api.BlockGatherable;
import gg.warcraft.monolith.api.item.Item;
import gg.warcraft.monolith.api.util.Duration;
import gg.warcraft.monolith.api.world.block.BlockType;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SimpleBlockGatherable extends AbstractGatherable implements BlockGatherable {
    private final Predicate<BlockType> containsBlockType;
    private final BlockType cooldownBlockType;

    @Inject
    public SimpleBlockGatherable(@Assisted Predicate<BlockType> containsBlockType,
                                 @Assisted BlockType cooldownBlockType,
                                 @Assisted Supplier<List<Item>> dropsSupplier,
                                 @Assisted Supplier<Duration> cooldownSupplier) {
        super(dropsSupplier, cooldownSupplier);
        this.containsBlockType = containsBlockType;
        this.cooldownBlockType = cooldownBlockType;
    }

    @Override
    public BlockType getCooldownBlockType() {
        return cooldownBlockType;
    }

    @Override
    public boolean containsBlockType(BlockType blockType) {
        return containsBlockType.test(blockType);
    }
}
