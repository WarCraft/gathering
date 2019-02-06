package gg.warcraft.gathering.api.gatherable.service;

import gg.warcraft.gathering.api.gatherable.EntityGatherable;

import java.util.UUID;

public interface EntityGatherableCommandService {

    void gatherEntity(EntityGatherable gatherable, UUID entityId);

    void respawnEntity(EntityGatherable gatherable, String gatheringSpotId);

    void removeAllEntities();
}
