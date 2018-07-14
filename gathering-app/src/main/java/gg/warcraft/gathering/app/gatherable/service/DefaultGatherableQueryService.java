package gg.warcraft.gathering.app.gatherable.service;

import com.google.inject.Inject;
import gg.warcraft.gathering.api.gatherable.service.GatherableQueryService;
import gg.warcraft.gathering.api.gatherable.service.GatherableRepository;

import java.util.List;
import java.util.UUID;

public class DefaultGatherableQueryService implements GatherableQueryService {
    private final GatherableRepository gatherableRepository;

    @Inject
    public DefaultGatherableQueryService(GatherableRepository gatherableRepository) {
        this.gatherableRepository = gatherableRepository;
    }

    @Override
    public List<UUID> getSpawnedEntityIds() {
        return gatherableRepository.getSpawnedEntityIds();
    }
}
