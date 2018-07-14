package gg.warcraft.gathering.api.gatherable.service;

import java.util.List;
import java.util.UUID;

public interface GatherableQueryService {

    List<UUID> getSpawnedEntityIds();
}
