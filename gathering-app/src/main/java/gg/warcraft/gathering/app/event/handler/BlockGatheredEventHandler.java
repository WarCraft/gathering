package gg.warcraft.gathering.app.event.handler;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import gg.warcraft.gathering.api.gatherable.service.BlockGatherableCommandService;
import gg.warcraft.gathering.api.spot.service.GatheringSpotQueryService;
import gg.warcraft.monolith.api.world.BlockLocation;
import gg.warcraft.monolith.api.world.block.Block;
import gg.warcraft.monolith.api.world.block.event.BlockPreBreakEvent;

import java.util.ArrayList;
import java.util.UUID;

public class BlockGatheredEventHandler {
    private final BlockGatherableCommandService blockGatherableCommandService;
    private final GatheringSpotQueryService gatheringSpotQueryService;

    @Inject
    public BlockGatheredEventHandler(BlockGatherableCommandService blockGatherableCommandService,
                                     GatheringSpotQueryService gatheringSpotQueryService) {
        this.blockGatherableCommandService = blockGatherableCommandService;
        this.gatheringSpotQueryService = gatheringSpotQueryService;
    }

    @Subscribe
    public void onBlockPreBreakEvent(BlockPreBreakEvent event) {
        UUID playerId = event.getPlayerId();
        if (playerId == null) {
            return;
        }

        Block block = event.getBlock();
        gatheringSpotQueryService.getBlockGatheringSpots().stream()
                .filter(gatheringSpot -> gatheringSpot.containsBlock(block))
                .forEach(gatheringSpot -> gatheringSpot.getBlockGatherables().stream()
                        .filter(gatherable -> gatherable.containsBlockType(block.getType()))
                        .findAny()
                        .ifPresent(gatherable -> {
                            event.explicitlyAllow();
                            event.setAlternativeDrops(new ArrayList<>());

                            BlockLocation location = block.getLocation();
                            final boolean blockGathered = blockGatherableCommandService.gatherBlock(gatherable,
                                    location, gatheringSpot.getId(), playerId);

                            if (blockGathered) {
                                blockGatherableCommandService.respawnBlock(gatherable, location);
                            }
                        }));
    }
}
