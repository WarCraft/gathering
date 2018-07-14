package gg.warcraft.gathering.api.gatherable.service;

import java.util.List;
import java.util.UUID;

public interface EntityGatherableQueryService {

    List<UUID> getSpawnedEntityIds();
}
