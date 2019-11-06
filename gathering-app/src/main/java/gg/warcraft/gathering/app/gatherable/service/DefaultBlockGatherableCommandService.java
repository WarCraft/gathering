package gg.warcraft.gathering.app.gatherable.service;

import com.google.inject.Inject;
import gg.warcraft.gathering.api.gatherable.BlockGatherable;
import gg.warcraft.gathering.api.gatherable.event.BlockGatheredEvent;
import gg.warcraft.gathering.api.gatherable.event.BlockPreGatheredEvent;
import gg.warcraft.gathering.api.gatherable.service.BlockGatherableCommandService;
import gg.warcraft.gathering.app.gatherable.event.SimpleBlockGatheredEvent;
import gg.warcraft.gathering.app.gatherable.event.SimpleBlockPreGatheredEvent;
import gg.warcraft.monolith.api.core.EventService;
import gg.warcraft.monolith.api.core.TaskService;
import gg.warcraft.monolith.api.util.Duration;
import gg.warcraft.monolith.api.world.BlockLocation;
import gg.warcraft.monolith.api.world.Location;
import gg.warcraft.monolith.api.world.WorldService;
import gg.warcraft.monolith.api.world.block.Block;
import gg.warcraft.monolith.api.world.block.BlockTypeVariantOrState;
import gg.warcraft.monolith.api.world.block.backup.BlockBackupService;
import gg.warcraft.monolith.api.world.item.Item;
import gg.warcraft.monolith.api.world.item.ItemService;

import java.util.List;
import java.util.UUID;

public class DefaultBlockGatherableCommandService implements BlockGatherableCommandService {
    private static final float DROP_OFFSET = 0.5f;

    private final WorldService worldService;
    private final ItemService itemService;
    private final BlockBackupService blockBackupCommandService;
    private final TaskService taskService;
    private final EventService eventService;

    @Inject
    public DefaultBlockGatherableCommandService(WorldService worldService, ItemService itemService,
                                                BlockBackupService blockBackupCommandService,
                                                TaskService taskService, EventService eventService) {
        this.worldService = worldService;
        this.itemService = itemService;
        this.blockBackupCommandService = blockBackupCommandService;
        this.taskService = taskService;
        this.eventService = eventService;
    }

    void spawnDrops(BlockGatherable gatherable, Block block) {
        List<Item> drops = gatherable.generateDrops();
        BlockLocation blockLocation = block.location();
        Location dropLocation = new Location(
                blockLocation.world(),
                blockLocation.x() + DROP_OFFSET,
                blockLocation.y() + DROP_OFFSET,
                blockLocation.z() + DROP_OFFSET);
        itemService.dropItems(dropLocation, drops.toArray(new Item[0])); // TODO create nicer Java API
    }

    void queueBlockCooldownState(BlockGatherable blockGatherable, Block block) {
        BlockTypeVariantOrState cooldownBlockData = blockGatherable.getCooldownBlockData();
        taskService.runNextTick(() -> worldService.setBlock(block.location(), cooldownBlockData));
    }

    void queueBlockRestoration(BlockGatherable blockGatherable, Block block) {
        Duration cooldown = blockGatherable.generateCooldown();
        UUID blockBackupId = blockBackupCommandService.createBackup(block.location());
        taskService.runLater(() -> blockBackupCommandService.restoreBackup(blockBackupId), cooldown);
    }

    @Override
    public boolean gatherBlock(BlockGatherable gatherable, BlockLocation location, String gatheringSpotId, UUID playerId) {
        Block block = worldService.getBlock(location);
        BlockPreGatheredEvent blockPreGatheredEvent = new SimpleBlockPreGatheredEvent(
                block, gatheringSpotId, playerId, false);
        eventService.publish(blockPreGatheredEvent);
        if (blockPreGatheredEvent.isCancelled() && !blockPreGatheredEvent.isExplicitlyAllowed()) {
            return false;
        }

        spawnDrops(gatherable, block);

        BlockGatheredEvent blockGatheredEvent = new SimpleBlockGatheredEvent(
                block, gatheringSpotId, playerId);
        eventService.publish(blockGatheredEvent);

        return true;
    }

    @Override
    public void respawnBlock(BlockGatherable gatherable, BlockLocation location) {
        Block block = worldService.getBlock(location);
        queueBlockCooldownState(gatherable, block);
        queueBlockRestoration(gatherable, block);
    }
}
