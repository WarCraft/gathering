package gg.warcraft.gathering.app.gatherable.service;

import com.google.inject.Inject;
import gg.warcraft.gathering.api.gatherable.EntityGatherable;
import gg.warcraft.gathering.api.gatherable.event.EntityGatheredEvent;
import gg.warcraft.gathering.api.gatherable.event.EntityPreGatheredEvent;
import gg.warcraft.gathering.api.gatherable.event.GatherableEntityRespawnedEvent;
import gg.warcraft.gathering.api.gatherable.service.EntityGatherableCommandService;
import gg.warcraft.gathering.api.gatherable.service.EntityGatherableRepository;
import gg.warcraft.gathering.app.gatherable.event.SimpleEntityGatheredEvent;
import gg.warcraft.gathering.app.gatherable.event.SimpleEntityPreGatheredEvent;
import gg.warcraft.gathering.app.gatherable.event.SimpleGatherableEntityRespawnedEvent;
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

public class DefaultEntityGatherableCommandService implements EntityGatherableCommandService {
    private final EntityGatherableRepository entityGatherableRepository;
    private final EntityQueryService entityQueryService;
    private final EntityCommandService entityCommandService;
    private final WorldCommandService worldCommandService;
    private final EventService eventService;
    private final TaskService taskService;

    @Inject
    public DefaultEntityGatherableCommandService(EntityGatherableRepository entityGatherableRepository,
                                                 EntityQueryService entityQueryService,
                                                 EntityCommandService entityCommandService,
                                                 WorldCommandService worldCommandService,
                                                 EventService eventService, TaskService taskService) {
        this.entityGatherableRepository = entityGatherableRepository;
        this.entityQueryService = entityQueryService;
        this.entityCommandService = entityCommandService;
        this.worldCommandService = worldCommandService;
        this.eventService = eventService;
        this.taskService = taskService;
    }

    void spawnDrops(EntityGatherable gatherable, Entity entity) {
        List<Item> drops = gatherable.generateDrops();
        Location dropLocation = entity.getLocation();
        worldCommandService.dropItemsAt(drops, dropLocation);
    }

    @Override
    public boolean gatherEntity(EntityGatherable gatherable, UUID entityId, String gatheringSpotId, UUID playerId) {
        Entity entity = entityQueryService.getEntity(entityId);
        EntityPreGatheredEvent entityPreGatheredEvent = new SimpleEntityPreGatheredEvent(
                entityId, entity.getType(), gatheringSpotId, playerId, false);
        eventService.publish(entityPreGatheredEvent);
        if (entityPreGatheredEvent.isCancelled() && !entityPreGatheredEvent.isExplicitlyAllowed()) {
            return false;
        }

        spawnDrops(gatherable, entity);
        entityGatherableRepository.deleteEntity(entityId);

        EntityGatheredEvent entityGatheredEvent = new SimpleEntityGatheredEvent(
                entityId, entity.getType(), gatheringSpotId, playerId);
        eventService.publish(entityGatheredEvent);

        return true;
    }

    @Override
    public void respawnEntity(EntityGatherable gatherable, String gatheringSpotId) {
        Location respawnLocation = gatherable.generateSpawnLocation();
        Duration cooldown = gatherable.generateCooldown();
        taskService.runLater(() -> {
            EntityType type = gatherable.getEntityType();
            UUID entityId = entityCommandService.spawnEntity(type, respawnLocation);
            entityGatherableRepository.saveEntity(entityId);

            GatherableEntityRespawnedEvent gatherableEntityRespawnedEvent =
                    new SimpleGatherableEntityRespawnedEvent(entityId, gatheringSpotId);
            eventService.publish(gatherableEntityRespawnedEvent);
        }, cooldown);
    }

    @Override
    public void removeAllEntities() {
        entityGatherableRepository.getSpawnedEntityIds().forEach(entityId -> {
            entityCommandService.removeEntity(entityId);
            entityGatherableRepository.deleteEntity(entityId);
        });
    }
}
