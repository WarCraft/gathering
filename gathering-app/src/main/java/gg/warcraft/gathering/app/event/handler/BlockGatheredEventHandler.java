package gg.warcraft.gathering.app.event.handler;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import gg.warcraft.gathering.api.gatherable.BlockGatherable;
import gg.warcraft.gathering.api.gatherable.Gatherable;
import gg.warcraft.gathering.api.spot.service.GatheringSpotQueryService;
import gg.warcraft.monolith.api.core.TaskService;
import gg.warcraft.monolith.api.item.Item;
import gg.warcraft.monolith.api.util.Duration;
import gg.warcraft.monolith.api.world.BlockLocation;
import gg.warcraft.monolith.api.world.Location;
import gg.warcraft.monolith.api.world.block.Block;
import gg.warcraft.monolith.api.world.block.BlockType;
import gg.warcraft.monolith.api.world.block.backup.service.BlockBackupCommandService;
import gg.warcraft.monolith.api.world.block.event.BlockPreBreakEvent;
import gg.warcraft.monolith.api.world.service.WorldCommandService;
import gg.warcraft.monolith.api.world.service.WorldQueryService;

import java.util.ArrayList;
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
        float x = blockLocation.getX() + DROP_OFFSET;
        float y = blockLocation.getY() + DROP_OFFSET;
        float z = blockLocation.getZ() + DROP_OFFSET;
        Location dropLocation = worldQueryService.getLocation(blockLocation.getWorld().getType(), x, y, z);
        List<Item> drops = gatherable.generateDrops();
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
    public void onBlockPreBreakEvent(BlockPreBreakEvent event) {
        Block block = event.getBlock();
        gatheringSpotQueryService.getBlockGatheringSpots().stream()
                .filter(spot -> spot.containsBlock(block))
                .forEach(spot -> spot.getBlockGatherables().stream()
                        .filter(gatherable -> gatherable.containsBlockType(block.getType()))
                        .forEach(gatherable -> {
                            event.explicitlyAllow();
                            event.setAlternativeDrops(new ArrayList<>());
                            spawnDrops(gatherable, block.getLocation());
                            queueBlockRestoration(gatherable, block);
                            queueBlockCooldownState(gatherable, block);
                        }));
    }
}
