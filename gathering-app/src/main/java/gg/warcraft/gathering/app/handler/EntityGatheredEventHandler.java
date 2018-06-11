package gg.warcraft.gathering.app.handler;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import gg.warcraft.gathering.api.EntityGatherable;
import gg.warcraft.gathering.api.EntityGatheringSpot;
import gg.warcraft.gathering.api.Gatherable;
import gg.warcraft.gathering.api.service.GatheringSpotQueryService;
import gg.warcraft.monolith.api.core.TaskService;
import gg.warcraft.monolith.api.entity.Entity;
import gg.warcraft.monolith.api.entity.EntityType;
import gg.warcraft.monolith.api.entity.event.EntityDeathEvent;
import gg.warcraft.monolith.api.entity.service.EntityCommandService;
import gg.warcraft.monolith.api.entity.service.EntityQueryService;
import gg.warcraft.monolith.api.util.Duration;
import gg.warcraft.monolith.api.world.ItemType;
import gg.warcraft.monolith.api.world.Location;
import gg.warcraft.monolith.api.world.service.WorldCommandService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EntityGatheredEventHandler {
    private final GatheringSpotQueryService gatheringSpotQueryService;
    private final EntityQueryService entityQueryService;
    private final EntityCommandService entityCommandService;
    private final WorldCommandService worldCommandService;
    private final TaskService taskService;

    @Inject
    public EntityGatheredEventHandler(GatheringSpotQueryService gatheringSpotQueryService,
                                      EntityQueryService entityQueryService, EntityCommandService entityCommandService,
                                      WorldCommandService worldCommandService, TaskService taskService) {
        this.gatheringSpotQueryService = gatheringSpotQueryService;
        this.entityQueryService = entityQueryService;
        this.entityCommandService = entityCommandService;
        this.worldCommandService = worldCommandService;
        this.taskService = taskService;
    }

    private void spawnDrops(Gatherable gatherable, Location location) {
        List<ItemType> drops = gatherable.generateDrops();
        worldCommandService.dropItemsAt(drops, location);
    }

    private void queueEntityRespawn(EntityGatherable entityGatherable, EntityType entityType) {
        Location respawnLocation = entityGatherable.generateSpawnLocation();
        Duration cooldown = entityGatherable.generateCooldown();
        taskService.runLater(() -> {
            entityCommandService.spawnEntity(entityType, respawnLocation);
        }, cooldown);
    }

    @Subscribe
    public void onEntityDeathEvent(EntityDeathEvent event) {
        UUID entityId = event.getEntityId();
        List<EntityGatheringSpot> gatheringSpots = gatheringSpotQueryService.getAllEntityGatheringSpots().stream()
                .filter(spot -> spot.containsEntity(entityId))
                .collect(Collectors.toList());
        if (gatheringSpots.isEmpty()) {
            return;
        }
        Entity entity = entityQueryService.getEntity(entityId);
        gatheringSpots.forEach(spot -> spot.getEntityGatherables().stream()
                .filter(gatherable -> gatherable.getEntityType().equals(entity.getType()))
                .forEach(gatherable -> {
                    event.setDrops(new ArrayList<>());

                    spawnDrops(gatherable, entity.getLocation());
                    queueEntityRespawn(gatherable, entity.getType());
                }));
    }
}
