package gg.warcraft.gathering.app;

import gg.warcraft.gathering.api.BlockGatherable;
import gg.warcraft.monolith.api.util.Duration;
import gg.warcraft.monolith.api.world.ItemType;
import gg.warcraft.monolith.api.world.block.BlockType;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SimpleBlockGatherable extends AbstractGatherable implements BlockGatherable {
    private final Predicate<BlockType> containsBlockType;
    private final BlockType cooldownBlockType;

    public SimpleBlockGatherable(Predicate<BlockType> containsBlockType, BlockType cooldownBlockType,
                                 Supplier<List<ItemType>> dropsSupplier, Supplier<Duration> cooldownSupplier) {
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
