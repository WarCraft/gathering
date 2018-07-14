package gg.warcraft.gathering.api.gatherable.service;

import java.util.List;
import java.util.UUID;

public interface EntityGatherableRepository {

    List<UUID> getSpawnedEntityIds();

    void saveEntity(UUID entityId);

    void deleteEntity(UUID entityId);
}
