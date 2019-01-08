package gg.warcraft.gathering.app.event.handler;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import gg.warcraft.gathering.api.gatherable.service.EntityGatherableQueryService;
import gg.warcraft.monolith.api.core.event.ServerShutdownEvent;
import gg.warcraft.monolith.api.entity.service.EntityCommandService;

public class ServerShutdownEventHandler {
    private final EntityGatherableQueryService entityGatherableQueryService;
    private final EntityCommandService entityCommandService;

    @Inject
    public ServerShutdownEventHandler(EntityGatherableQueryService entityGatherableQueryService,
                                      EntityCommandService entityCommandService) {
        this.entityGatherableQueryService = entityGatherableQueryService;
        this.entityCommandService = entityCommandService;
    }

    @Subscribe
    public void onServerShutdownEvent(ServerShutdownEvent event) {
        entityGatherableQueryService.getSpawnedEntityIds().forEach(entityCommandService::removeEntity);
    }
}
