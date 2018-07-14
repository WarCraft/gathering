package gg.warcraft.gathering.app.event.handler;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import gg.warcraft.gathering.api.gatherable.EntityGatherable;
import gg.warcraft.gathering.api.gatherable.event.GatherableEntitySpawnedEvent;
import gg.warcraft.gathering.api.gatherable.service.GatherableCommandService;
import gg.warcraft.gathering.api.spot.service.GatheringSpotCommandService;
import gg.warcraft.gathering.api.spot.service.GatheringSpotQueryService;
import gg.warcraft.monolith.api.entity.event.EntityPreDeathEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityGatheredEventHandler {
    private final GatheringSpotQueryService gatheringSpotQueryService;
    private final GatheringSpotCommandService gatheringSpotCommandService;
    private final GatherableCommandService gatherableCommandService;

    @Inject
    public EntityGatheredEventHandler(GatheringSpotQueryService gatheringSpotQueryService,
                                      GatheringSpotCommandService gatheringSpotCommandService,
                                      GatherableCommandService gatherableCommandService) {
        this.gatheringSpotQueryService = gatheringSpotQueryService;
        this.gatheringSpotCommandService = gatheringSpotCommandService;
        this.gatherableCommandService = gatherableCommandService;
    }

    @Subscribe
    public void onEntityPreDeathEvent(EntityPreDeathEvent event) {
        UUID entityId = event.getEntityId();
        gatheringSpotQueryService.getEntityGatheringSpots().stream()
                .filter(spot -> spot.getEntityIds().contains(entityId))
                .findAny()
                .ifPresent(gatheringSpot -> {
                    event.explicitlyAllow();
                    event.setDrops(new ArrayList<>());

                    gatheringSpotCommandService.removeEntityFromGatheringSpot(gatheringSpot.getId(), entityId);
                    List<EntityGatherable> gatherables = gatheringSpot.getEntityGatherables();
                    EntityGatherable gatherable = gatherableCommandService.gatherEntity(gatherables, entityId);
                    if (gatherable != null) {
                        gatherableCommandService.respawnEntity(gatherable, gatheringSpot.getId());
                    }
                });
    }

    @Subscribe
    public void onGatherableEntitySpawnedEvent(GatherableEntitySpawnedEvent event) {
        UUID gatheringSpotId = event.getGatheringSpotId();
        UUID entityId = event.getEntityId();
        gatheringSpotCommandService.addEntityToGatheringSpot(gatheringSpotId, entityId);
    }
}
