package gg.warcraft.gathering.app.event.handler;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import gg.warcraft.gathering.api.gatherable.event.GatherableEntityRespawnedEvent;
import gg.warcraft.gathering.api.gatherable.service.EntityGatherableCommandService;
import gg.warcraft.gathering.api.spot.service.GatheringSpotCommandService;
import gg.warcraft.gathering.api.spot.service.GatheringSpotQueryService;
import gg.warcraft.monolith.api.entity.Entity;
import gg.warcraft.monolith.api.entity.event.EntityDeathEvent;
import gg.warcraft.monolith.api.entity.event.EntityPreFatalDamageEvent;
import gg.warcraft.monolith.api.entity.service.EntityQueryService;

import java.util.ArrayList;
import java.util.UUID;

public class EntityGatheredEventHandler {
    private final GatheringSpotQueryService gatheringSpotQueryService;
    private final GatheringSpotCommandService gatheringSpotCommandService;
    private final EntityGatherableCommandService entityGatherableCommandService;
    private final EntityQueryService entityQueryService;

    @Inject
    public EntityGatheredEventHandler(GatheringSpotQueryService gatheringSpotQueryService,
                                      GatheringSpotCommandService gatheringSpotCommandService,
                                      EntityGatherableCommandService entityGatherableCommandService,
                                      EntityQueryService entityQueryService) {
        this.gatheringSpotQueryService = gatheringSpotQueryService;
        this.gatheringSpotCommandService = gatheringSpotCommandService;
        this.entityGatherableCommandService = entityGatherableCommandService;
        this.entityQueryService = entityQueryService;
    }

    @Subscribe
    public void onEntityPreFatalDamageEvent(EntityPreFatalDamageEvent event) {
        UUID entityId = event.getEntityId();
        gatheringSpotQueryService.getEntityGatheringSpots().stream()
                .filter(gatheringSpot -> gatheringSpot.getEntityIds().contains(entityId))
                .findAny()
                .ifPresent(gatheringSpot -> {
                    Entity entity = entityQueryService.getEntity(entityId);
                    gatheringSpot.getEntityGatherables().stream()
                            .filter(gatherable -> gatherable.getEntityType() == entity.getType())
                            .findAny()
                            .ifPresent(gatherable -> event.explicitlyAllow());
                });
    }

    @Subscribe
    public void onEntityDeathEvent(EntityDeathEvent event) {
        UUID entityId = event.getEntityId();
        gatheringSpotQueryService.getEntityGatheringSpots().stream()
                .filter(gatheringSpot -> gatheringSpot.getEntityIds().contains(entityId))
                .findAny()
                .ifPresent(gatheringSpot -> {
                    Entity entity = entityQueryService.getEntity(entityId);
                    gatheringSpot.getEntityGatherables().stream()
                            .filter(gatherable -> gatherable.getEntityType() == entity.getType())
                            .findAny()
                            .ifPresent(gatherable -> {
                                event.setDrops(new ArrayList<>());

                                UUID gatheringSpotId = gatheringSpot.getId();
                                gatheringSpotCommandService.removeEntityFromGatheringSpot(gatheringSpotId, entityId);
                                entityGatherableCommandService.gatherEntity(gatherable, entityId);
                                entityGatherableCommandService.respawnEntity(gatherable, gatheringSpotId);
                            });
                });
    }

    @Subscribe
    public void onGatherableEntitySpawnedEvent(GatherableEntityRespawnedEvent event) {
        UUID gatheringSpotId = event.getGatheringSpotId();
        UUID entityId = event.getEntityId();
        gatheringSpotCommandService.addEntityToGatheringSpot(gatheringSpotId, entityId);
    }
}
