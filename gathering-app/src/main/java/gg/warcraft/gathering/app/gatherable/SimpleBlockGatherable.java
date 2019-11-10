package gg.warcraft.gathering.app.gatherable;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import gg.warcraft.gathering.api.gatherable.BlockGatherable;
import gg.warcraft.monolith.api.util.Duration;
import gg.warcraft.monolith.api.world.block.Block;
import gg.warcraft.monolith.api.world.block.BlockType;
import gg.warcraft.monolith.api.world.block.BlockTypeVariantOrState;
import gg.warcraft.monolith.api.world.item.Item;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SimpleBlockGatherable extends AbstractGatherable implements BlockGatherable {
    private final Predicate<Block> containsBlock;
    private final BlockTypeVariantOrState cooldownBlockType;

    @Inject
    public SimpleBlockGatherable(@Assisted Predicate<Block> containsBlock,
                                 @Assisted BlockTypeVariantOrState cooldownBlockData,
                                 @Assisted Supplier<List<Item>> dropsSupplier,
                                 @Assisted Supplier<Duration> cooldownSupplier) {
        super(dropsSupplier, cooldownSupplier);
        this.containsBlock = containsBlock;
        this.cooldownBlockType = cooldownBlockData;
    }

    @Override
    public BlockTypeVariantOrState getCooldownBlockData() {
        return cooldownBlockType;
    }

    public boolean containsBlock(Block block) {
        return containsBlock.test(block);
    }
}
