package gg.warcraft.gathering.app.gatherable.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import gg.warcraft.gathering.api.gatherable.service.GatherableRepository;
import gg.warcraft.monolith.api.persistence.PersistenceService;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class DefaultGatherableRepository implements GatherableRepository {
    private static final String GATHERING_ENTITY_IDS_KEY = "gathering:readonly:entityids";

    private final PersistenceService persistenceService;

    @Inject
    public DefaultGatherableRepository(PersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    @Override
    public List<UUID> getSpawnedEntityIds() {
        return persistenceService.getSet(GATHERING_ENTITY_IDS_KEY).stream()
                .map(UUID::fromString)
                .collect(Collectors.toList());
    }

    @Override
    public void saveEntity(UUID entityId) {
        String entityIdAsString = entityId.toString();
        Set<String> entityIds = persistenceService.getSet(GATHERING_ENTITY_IDS_KEY);
        if (entityIds.add(entityId.toString())) {
            List<String> valuesToAdd = Collections.singletonList(entityIdAsString);
            persistenceService.addSet(GATHERING_ENTITY_IDS_KEY, valuesToAdd);
        }
    }

    @Override
    public void deleteEntity(UUID entityId) {
        String entityIdAsString = entityId.toString();
        Set<String> entityIds = persistenceService.getSet(GATHERING_ENTITY_IDS_KEY);
        if (entityIds.remove(entityId.toString())) {
            List<String> valuesToRemove = Collections.singletonList(entityIdAsString);
            persistenceService.removeSet(GATHERING_ENTITY_IDS_KEY, valuesToRemove);
        }
    }
}
