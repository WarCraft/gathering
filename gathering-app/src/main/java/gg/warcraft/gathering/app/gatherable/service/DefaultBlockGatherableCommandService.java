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
import gg.warcraft.monolith.api.item.Item;
import gg.warcraft.monolith.api.util.Duration;
import gg.warcraft.monolith.api.world.block.Block;
import gg.warcraft.monolith.api.world.block.BlockType;
import gg.warcraft.monolith.api.world.block.backup.service.BlockBackupCommandService;
import gg.warcraft.monolith.api.world.location.BlockLocation;
import gg.warcraft.monolith.api.world.location.Location;
import gg.warcraft.monolith.api.world.location.LocationFactory;
import gg.warcraft.monolith.api.world.service.WorldCommandService;
import gg.warcraft.monolith.api.world.service.WorldQueryService;

import java.util.List;
import java.util.UUID;

public class DefaultBlockGatherableCommandService implements BlockGatherableCommandService {
    private static final float DROP_OFFSET = 0.5f;

    private final WorldQueryService worldQueryService;
    private final WorldCommandService worldCommandService;
    private final BlockBackupCommandService blockBackupCommandService;
    private final TaskService taskService;
    private final LocationFactory locationFactory;
    private final EventService eventService;

    @Inject
    public DefaultBlockGatherableCommandService(WorldQueryService worldQueryService,
                                                WorldCommandService worldCommandService,
                                                BlockBackupCommandService blockBackupCommandService,
                                                TaskService taskService, LocationFactory locationFactory,
                                                EventService eventService) {
        this.worldQueryService = worldQueryService;
        this.worldCommandService = worldCommandService;
        this.blockBackupCommandService = blockBackupCommandService;
        this.taskService = taskService;
        this.locationFactory = locationFactory;
        this.eventService = eventService;
    }

    void spawnDrops(BlockGatherable gatherable, Block block) {
        List<Item> drops = gatherable.generateDrops();
        BlockLocation blockLocation = block.getLocation();
        Location dropLocation = locationFactory.createLocation(
                blockLocation.getWorld().getType(),
                blockLocation.getX() + DROP_OFFSET,
                blockLocation.getY() + DROP_OFFSET,
                blockLocation.getZ() + DROP_OFFSET);
        worldCommandService.dropItemsAt(drops, dropLocation);
    }

    void queueBlockCooldownState(BlockGatherable blockGatherable, Block block) {
        BlockType cooldownBlockType = blockGatherable.getCooldownBlockType();
        taskService.runNextTick(() -> worldCommandService.setBlockType(block, cooldownBlockType));
    }

    void queueBlockRestoration(BlockGatherable blockGatherable, Block block) {
        Duration cooldown = blockGatherable.generateCooldown();
        UUID blockBackupId = blockBackupCommandService.createBlockBackup(block);
        taskService.runLater(() -> blockBackupCommandService.restoreBlockBackup(blockBackupId), cooldown);
    }

    @Override
    public void gatherBlock(BlockGatherable gatherable, BlockLocation location) {
        Block block = worldQueryService.getBlockAt(location);
        BlockPreGatheredEvent blockPreGatheredEvent = new SimpleBlockPreGatheredEvent(block, false);
        eventService.publish(blockPreGatheredEvent);
        if (blockPreGatheredEvent.isCancelled() && !blockPreGatheredEvent.isExplicitlyAllowed()) {
            return;
        }

        spawnDrops(gatherable, block);

        BlockGatheredEvent blockGatheredEvent = new SimpleBlockGatheredEvent(block);
        eventService.publish(blockGatheredEvent);
    }

    @Override
    public void respawnBlock(BlockGatherable gatherable, BlockLocation location) {
        Block block = worldQueryService.getBlockAt(location);
        queueBlockCooldownState(gatherable, block);
        queueBlockRestoration(gatherable, block);
    }
}
