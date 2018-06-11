package gg.warcraft.gathering.app.factory;

import gg.warcraft.gathering.api.BlockGatherable;
import gg.warcraft.gathering.api.EntityGatherable;
import gg.warcraft.gathering.api.factory.GatherableFactory;
import gg.warcraft.monolith.api.entity.EntityType;
import gg.warcraft.monolith.api.util.Duration;
import gg.warcraft.monolith.api.world.ItemType;
import gg.warcraft.monolith.api.world.Location;
import gg.warcraft.monolith.api.world.block.BlockType;
import gg.warcraft.monolith.app.gathering.SimpleBlockGatherable;
import gg.warcraft.monolith.app.gathering.SimpleEntityGatherable;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class DefaultGatherableFactory implements GatherableFactory {

    @Override
    public BlockGatherable createBlockGatherable(Predicate<BlockType> containsBlockType, BlockType cooldownBlockType,
                                                 Supplier<List<ItemType>> dropsSupplier, Supplier<Duration> cooldownSupplier) {
        return new SimpleBlockGatherable(containsBlockType, cooldownBlockType, dropsSupplier, cooldownSupplier);
    }

    @Override
    public BlockGatherable createBlockGatherable(Set<BlockType> blockTypes, BlockType cooldownBlockType,
                                                 ItemType dropItemType, Supplier<Duration> cooldownSupplier) {
        Predicate<BlockType> containsMaterialData = blockTypes::contains;
        Supplier<List<ItemType>> dropsSupplier = () -> Collections.singletonList(dropItemType);
        return createBlockGatherable(containsMaterialData, cooldownBlockType, dropsSupplier, cooldownSupplier);
    }

    @Override
    public BlockGatherable createBlockGatherable(Set<BlockType> blockTypes, BlockType cooldownBlockType,
                                                 Supplier<List<ItemType>> dropsSupplier,
                                                 Supplier<Duration> cooldownSupplier) {
        Predicate<BlockType> containsMaterialData = blockTypes::contains;
        return createBlockGatherable(containsMaterialData, cooldownBlockType, dropsSupplier, cooldownSupplier);
    }

    @Override
    public EntityGatherable createEntityGatherable(EntityType entityType, int entityCount,
                                                   Supplier<Location> spawnLocationSupplier,
                                                   Supplier<List<ItemType>> dropsSupplier,
                                                   Supplier<Duration> cooldownSupplier) {
        return new SimpleEntityGatherable(entityType, entityCount, spawnLocationSupplier, dropsSupplier, cooldownSupplier);
    }
}
