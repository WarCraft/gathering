package gg.warcraft.gathering.api.gatherable.service;

import gg.warcraft.gathering.api.gatherable.EntityGatherable;

import java.util.UUID;

public interface EntityGatherableCommandService {

    void gatherEntity(EntityGatherable gatherable, UUID entityId, UUID playerId);

    void respawnEntity(EntityGatherable gatherable, UUID gatheringSpotId);

    void removeAllEntities();
}
