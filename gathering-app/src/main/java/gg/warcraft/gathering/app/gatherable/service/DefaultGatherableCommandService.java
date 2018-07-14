package gg.warcraft.gathering.app.gatherable.service;

import com.google.inject.Inject;
import gg.warcraft.gathering.api.gatherable.EntityGatherable;
import gg.warcraft.gathering.api.gatherable.event.GatherableEntitySpawnedEvent;
import gg.warcraft.gathering.api.gatherable.service.GatherableCommandService;
import gg.warcraft.gathering.api.gatherable.service.GatherableRepository;
import gg.warcraft.gathering.app.gatherable.event.SimpleGatherableEntitySpawnedEvent;
import gg.warcraft.monolith.api.core.EventService;
import gg.warcraft.monolith.api.core.TaskService;
import gg.warcraft.monolith.api.entity.Entity;
import gg.warcraft.monolith.api.entity.EntityType;
import gg.warcraft.monolith.api.entity.service.EntityCommandService;
import gg.warcraft.monolith.api.entity.service.EntityQueryService;
import gg.warcraft.monolith.api.item.Item;
import gg.warcraft.monolith.api.util.Duration;
import gg.warcraft.monolith.api.world.Location;
import gg.warcraft.monolith.api.world.service.WorldCommandService;

import java.util.List;
import java.util.UUID;

public class DefaultGatherableCommandService implements GatherableCommandService {
    private final GatherableRepository gatherableRepository;
    private final EntityQueryService entityQueryService;
    private final EntityCommandService entityCommandService;
    private final WorldCommandService worldCommandService;
    private final EventService eventService;
    private final TaskService taskService;

    @Inject
    public DefaultGatherableCommandService(GatherableRepository gatherableRepository,
                                           EntityQueryService entityQueryService,
                                           EntityCommandService entityCommandService,
                                           WorldCommandService worldCommandService,
                                           EventService eventService, TaskService taskService) {
        this.gatherableRepository = gatherableRepository;
        this.entityQueryService = entityQueryService;
        this.entityCommandService = entityCommandService;
        this.worldCommandService = worldCommandService;
        this.eventService = eventService;
        this.taskService = taskService;
    }

    void spawnEntityDrops(EntityGatherable gatherable, Entity entity) {
        List<Item> drops = gatherable.generateDrops();
        Location spawnLocation = entity.getLocation();
        worldCommandService.dropItemsAt(drops, spawnLocation);
    }

    @Override
    public EntityGatherable gatherEntity(List<EntityGatherable> gatherables, UUID entityId) {
        Entity entity = entityQueryService.getEntity(entityId);
        EntityGatherable entityGatherable = gatherables.stream()
                .filter(gatherable -> gatherable.getEntityType() == entity.getType())
                .findAny()
                .orElse(null);
        if (entityGatherable == null) {
            return null;
        }

        spawnEntityDrops(entityGatherable, entity);
        gatherableRepository.deleteEntity(entityId);
        return entityGatherable;
    }

    @Override
    public void respawnEntity(EntityGatherable gatherable, UUID gatheringSpotId) {
        Location respawnLocation = gatherable.generateSpawnLocation();
        Duration cooldown = gatherable.generateCooldown();
        taskService.runLater(() -> {
            EntityType type = gatherable.getEntityType();
            UUID entityId = entityCommandService.spawnEntity(type, respawnLocation);
            gatherableRepository.saveEntity(entityId);

            GatherableEntitySpawnedEvent gatherableEntitySpawnedEvent =
                    new SimpleGatherableEntitySpawnedEvent(entityId, gatheringSpotId);
            eventService.publish(gatherableEntitySpawnedEvent);
        }, cooldown);
    }

    @Override
    public void removeAllEntities() {
        gatherableRepository.getSpawnedEntityIds().forEach(entityId -> {
            entityCommandService.removeEntity(entityId);
            gatherableRepository.deleteEntity(entityId);
        });
    }
}
