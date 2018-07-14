package gg.warcraft.gathering.app.gatherable.service;

import com.google.inject.Inject;
import gg.warcraft.gathering.api.gatherable.service.EntityGatherableQueryService;
import gg.warcraft.gathering.api.gatherable.service.EntityGatherableRepository;

import java.util.List;
import java.util.UUID;

public class DefaultEntityGatherableQueryService implements EntityGatherableQueryService {
    private final EntityGatherableRepository entityGatherableRepository;

    @Inject
    public DefaultEntityGatherableQueryService(EntityGatherableRepository entityGatherableRepository) {
        this.entityGatherableRepository = entityGatherableRepository;
    }

    @Override
    public List<UUID> getSpawnedEntityIds() {
        return entityGatherableRepository.getSpawnedEntityIds();
    }
}
