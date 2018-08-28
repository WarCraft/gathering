package gg.warcraft.gathering.api.gatherable;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;
import gg.warcraft.monolith.api.entity.EntityType;
import gg.warcraft.monolith.api.item.Item;
import gg.warcraft.monolith.api.util.Duration;
import gg.warcraft.monolith.api.world.block.BlockType;
import gg.warcraft.monolith.api.world.location.Location;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This factory is injectable.
 * <p>
 * GatherableFactory serves as a point of entry into the gathering implementation. It allows for easy creation of
 * {@code BlockGatherable} and {@code EntityGatherable} objects.
 */
public interface GatherableFactory {

    /**
     * @param containsBlockType The predicate to check whether a block type belongs to the gatherable. Can not be null.
     * @param cooldownBlockType The block type this gatherable will be set to while on cooldown. Can not be null.
     * @param dropsSupplier     The item supplier that will be called every time the gatherable is gathered, it allows
     *                          for dynamic drop lists. Can not be null. The return value of the supplier can not be
     *                          null, but can be empty. Items can not be null.
     * @param cooldownSupplier  The cooldown supplier that will be called every time the gatherable is gathered, it
     *                          allows for cooldown randomization. Can not be null. The return value of the supplier can
     *                          not be null.
     * @return A new block gatherable. Never null.
     */
    @Named("block")
    BlockGatherable createBlockGatherable(Predicate<BlockType> containsBlockType,
                                          BlockType cooldownBlockType,
                                          @Assisted Supplier<List<Item>> dropsSupplier,
                                          @Assisted Supplier<Duration> cooldownSupplier);

    /**
     * @param entityType            The entity type this gatherable represents. Can not be null.
     * @param entityCount           The maximum number of entities this gatherable should produce.
     * @param spawnLocationSupplier The spawn location supplier that will be called every time a new entity is spawned.
     *                              Can not be null. The return value of the supplier can not be null.
     * @param dropsSupplier         The item supplier that will be called every time the gatherable is gathered, it
     *                              allows for dynamic drop lists. Can not be null. The return value of the supplier can
     *                              not be null, but can be empty. Items can not be null.
     * @param cooldownSupplier      The cooldown supplier that will be called every time the gatherable is gathered, it
     *                              allows for cooldown randomization. Can not be null. The return value of the supplier
     *                              can not be null.
     * @return A new entity gatherable. Never null.
     */
    @Named("entity")
    EntityGatherable createEntityGatherable(EntityType entityType,
                                            int entityCount,
                                            @Assisted Supplier<Location> spawnLocationSupplier,
                                            @Assisted Supplier<List<Item>> dropsSupplier,
                                            @Assisted Supplier<Duration> cooldownSupplier);
}
