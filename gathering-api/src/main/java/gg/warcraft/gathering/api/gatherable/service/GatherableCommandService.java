package gg.warcraft.gathering.api.gatherable.service;

import gg.warcraft.gathering.api.gatherable.EntityGatherable;

import java.util.List;
import java.util.UUID;

public interface GatherableCommandService {

    EntityGatherable gatherEntity(List<EntityGatherable> gatherables, UUID entityId);

    void spawnEntity(EntityGatherable gatherable, UUID gatheringSpotId);

    void removeAllEntities();
}
