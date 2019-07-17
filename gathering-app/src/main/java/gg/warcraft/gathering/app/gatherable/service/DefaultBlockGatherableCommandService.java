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
import gg.warcraft.monolith.api.world.BlockLocation;
import gg.warcraft.monolith.api.world.Location;
import gg.warcraft.monolith.api.world.block.Block;
import gg.warcraft.monolith.api.world.block.BlockType;
import gg.warcraft.monolith.api.world.block.backup.service.BlockBackupCommandService;
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
    private final EventService eventService;

    @Inject
    public DefaultBlockGatherableCommandService(WorldQueryService worldQueryService,
                                                WorldCommandService worldCommandService,
                                                BlockBackupCommandService blockBackupCommandService,
                                                TaskService taskService, EventService eventService) {
        this.worldQueryService = worldQueryService;
        this.worldCommandService = worldCommandService;
        this.blockBackupCommandService = blockBackupCommandService;
        this.taskService = taskService;
        this.eventService = eventService;
    }

    void spawnDrops(BlockGatherable gatherable, Block block) {
        List<Item> drops = gatherable.generateDrops();
        BlockLocation blockLocation = block.getLocation();
        Location dropLocation = new Location(
                blockLocation.getWorld(),
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
        UUID blockBackupId = blockBackupCommandService.createBlockBackup(block.getLocation());
        taskService.runLater(() -> blockBackupCommandService.restoreBlockBackup(blockBackupId), cooldown);
    }

    @Override
    public boolean gatherBlock(BlockGatherable gatherable, BlockLocation location, String gatheringSpotId, UUID playerId) {
        Block block = worldQueryService.getBlockAt(location);
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
        Block block = worldQueryService.getBlockAt(location);
        queueBlockCooldownState(gatherable, block);
        queueBlockRestoration(gatherable, block);
    }
}
