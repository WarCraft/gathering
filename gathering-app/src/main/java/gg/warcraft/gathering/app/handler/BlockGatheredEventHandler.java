package gg.warcraft.gathering.app.handler;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import gg.warcraft.gathering.api.BlockGatherable;
import gg.warcraft.gathering.api.Gatherable;
import gg.warcraft.gathering.api.service.GatheringSpotQueryService;
import gg.warcraft.monolith.api.core.TaskService;
import gg.warcraft.monolith.api.util.Duration;
import gg.warcraft.monolith.api.world.BlockLocation;
import gg.warcraft.monolith.api.world.ItemType;
import gg.warcraft.monolith.api.world.Location;
import gg.warcraft.monolith.api.world.block.Block;
import gg.warcraft.monolith.api.world.block.BlockType;
import gg.warcraft.monolith.api.world.block.backup.service.BlockBackupCommandService;
import gg.warcraft.monolith.api.world.event.BlockBreakEvent;
import gg.warcraft.monolith.api.world.service.WorldCommandService;
import gg.warcraft.monolith.api.world.service.WorldQueryService;

import java.util.List;
import java.util.UUID;

public class BlockGatheredEventHandler {
    private static final float DROP_OFFSET = 0.5f;

    private final GatheringSpotQueryService gatheringSpotQueryService;
    private final TaskService taskService;
    private final WorldCommandService worldCommandService;
    private final WorldQueryService worldQueryService;
    private final BlockBackupCommandService blockBackupCommandService;

    @Inject
    public BlockGatheredEventHandler(GatheringSpotQueryService gatheringSpotQueryService, TaskService taskService,
                                     WorldCommandService worldCommandService, WorldQueryService worldQueryService,
                                     BlockBackupCommandService blockBackupCommandService) {
        this.gatheringSpotQueryService = gatheringSpotQueryService;
        this.taskService = taskService;
        this.worldCommandService = worldCommandService;
        this.worldQueryService = worldQueryService;
        this.blockBackupCommandService = blockBackupCommandService;
    }

    private void spawnDrops(Gatherable gatherable, BlockLocation blockLocation) {
        Location dropLocation = worldQueryService.getLocation(blockLocation.getWorld().getType(),
                blockLocation.getX() + DROP_OFFSET, blockLocation.getY(), blockLocation.getZ() + DROP_OFFSET);
        List<ItemType> drops = gatherable.generateDrops();
        worldCommandService.dropItemsAt(drops, dropLocation);
    }

    private void queueBlockRestoration(BlockGatherable blockGatherable, Block block) {
        Duration cooldown = blockGatherable.generateCooldown();
        UUID blockBackupId = blockBackupCommandService.createBlockBackup(block);
        taskService.runLater(() -> blockBackupCommandService.restoreBlockBackup(blockBackupId), cooldown);
    }

    private void queueBlockCooldownState(BlockGatherable blockGatherable, Block block) {
        BlockType cooldownBlockType = blockGatherable.getCooldownBlockType();
        taskService.runNextTick(() -> worldCommandService.setBlockType(block, cooldownBlockType));
    }

    @Subscribe
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        gatheringSpotQueryService.getAllBlockGatheringSpots().stream()
                .filter(spot -> spot.containsBlock(block))
                .forEach(spot -> spot.getBlockGatherables().stream()
                        .filter(gatherable -> gatherable.containsBlockType(block.getType()))
                        .forEach(gatherable -> {
                            event.setDropItems(false);
                            spawnDrops(gatherable, block.getLocation());
                            queueBlockRestoration(gatherable, block);
                            queueBlockCooldownState(gatherable, block);
                        }));
    }
}
