package gg.warcraft.gathering.app.event.handler;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import gg.warcraft.gathering.api.gatherable.EntityGatherable;
import gg.warcraft.gathering.api.gatherable.event.GatherableEntitySpawnedEvent;
import gg.warcraft.gathering.api.gatherable.service.EntityGatherableCommandService;
import gg.warcraft.gathering.api.spot.service.GatheringSpotCommandService;
import gg.warcraft.gathering.api.spot.service.GatheringSpotQueryService;
import gg.warcraft.monolith.api.entity.event.EntityPreDeathEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityGatheredEventHandler {
    private final GatheringSpotQueryService gatheringSpotQueryService;
    private final GatheringSpotCommandService gatheringSpotCommandService;
    private final EntityGatherableCommandService entityGatherableCommandService;

    @Inject
    public EntityGatheredEventHandler(GatheringSpotQueryService gatheringSpotQueryService,
                                      GatheringSpotCommandService gatheringSpotCommandService,
                                      EntityGatherableCommandService entityGatherableCommandService) {
        this.gatheringSpotQueryService = gatheringSpotQueryService;
        this.gatheringSpotCommandService = gatheringSpotCommandService;
        this.entityGatherableCommandService = entityGatherableCommandService;
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
                    EntityGatherable gatherable = entityGatherableCommandService.gatherEntity(gatherables, entityId);
                    if (gatherable != null) {
                        entityGatherableCommandService.respawnEntity(gatherable, gatheringSpot.getId());
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
