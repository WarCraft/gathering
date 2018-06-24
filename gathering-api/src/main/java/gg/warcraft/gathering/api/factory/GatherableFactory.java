package gg.warcraft.gathering.api.factory;

import gg.warcraft.gathering.api.BlockGatherable;
import gg.warcraft.gathering.api.EntityGatherable;
import gg.warcraft.monolith.api.entity.EntityType;
import gg.warcraft.monolith.api.item.ItemType;
import gg.warcraft.monolith.api.util.Duration;
import gg.warcraft.monolith.api.world.Location;
import gg.warcraft.monolith.api.world.block.BlockType;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A factory interface to create a {@link BlockGatherable} and {@link EntityGatherable} easily for you
 */
public interface GatherableFactory {

    /**
     * Creates a new block gatherable.
     *
     * @param containsBlockType the predicate to check whether a material data belongs to the block gatherable
     * @param cooldownBlockType the material data the gatherable will appear as while on cooldown
     * @param dropsSupplier     the drops supplier that will be called every time the block gatherable is gathered,
     *                          it allows for dynamic drop lists
     * @param cooldownSupplier  the cooldown supplier that will be called every time the block gatherable is
     *                          gathered, it allows for cooldown randomization
     * @return the newly created block gatherable
     */
    BlockGatherable createBlockGatherable(Predicate<BlockType> containsBlockType,
                                          BlockType cooldownBlockType,
                                          Supplier<List<ItemType>> dropsSupplier,
                                          Supplier<Duration> cooldownSupplier);

    /**
     * Creates a new entity gatherable.
     *
     * @param entityType            the entity type of the entity gatherable
     * @param entityCount           the number of entities the entity gatherable provides
     * @param spawnLocationSupplier the spawn location supplier that will be called every time an entity spawns
     * @param dropsSupplier         the drops supplier that will be called every time the entity gatherable is gathered,
     *                              it allows for dynamic drop lists
     * @param cooldownSupplier      the cooldown supplier that will be called every time the entity gatherable is
     *                              gathered, it allows for cooldown randomization
     * @return the newly created entity gatherable
     */
    EntityGatherable createEntityGatherable(EntityType entityType,
                                            int entityCount,
                                            Supplier<Location> spawnLocationSupplier,
                                            Supplier<List<ItemType>> dropsSupplier,
                                            Supplier<Duration> cooldownSupplier);
}
