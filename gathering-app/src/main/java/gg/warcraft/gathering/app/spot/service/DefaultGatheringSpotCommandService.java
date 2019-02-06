package gg.warcraft.gathering.app.spot.service;

import com.google.inject.Inject;
import gg.warcraft.gathering.api.gatherable.BlockGatherable;
import gg.warcraft.gathering.api.gatherable.EntityGatherable;
import gg.warcraft.gathering.api.gatherable.service.EntityGatherableCommandService;
import gg.warcraft.gathering.api.spot.BlockGatheringSpot;
import gg.warcraft.gathering.api.spot.EntityGatheringSpot;
import gg.warcraft.gathering.api.spot.service.GatheringSpotCommandService;
import gg.warcraft.gathering.api.spot.service.GatheringSpotRepository;
import gg.warcraft.gathering.app.spot.SimpleBlockGatheringSpot;
import gg.warcraft.gathering.app.spot.SimpleEntityGatheringSpot;
import gg.warcraft.monolith.api.world.block.Block;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

public class DefaultGatheringSpotCommandService implements GatheringSpotCommandService {
    private final GatheringSpotRepository gatheringSpotRepository;
    private final EntityGatherableCommandService entityGatherableCommandService;

    @Inject
    public DefaultGatheringSpotCommandService(GatheringSpotRepository gatheringSpotRepository,
                                              EntityGatherableCommandService entityGatherableCommandService) {
        this.gatheringSpotRepository = gatheringSpotRepository;
        this.entityGatherableCommandService = entityGatherableCommandService;
    }

    @Override
    public UUID createBlockGatheringSpot(Predicate<Block> containsBlock, List<BlockGatherable> gatherables) {
        UUID id = UUID.randomUUID();
        BlockGatheringSpot gatheringSpot = new SimpleBlockGatheringSpot(id.toString(), containsBlock, gatherables);
        gatheringSpotRepository.save(gatheringSpot);
        return id;
    }

    @Override
    public UUID createEntityGatheringSpot(List<EntityGatherable> gatherables) {
        UUID gatheringSpotId = UUID.randomUUID();
        gatherables.forEach(gatherable -> {
            int entityCount = gatherable.getEntityCount();
            for (int i = 0; i < entityCount; i += 1) {
                entityGatherableCommandService.respawnEntity(gatherable, gatheringSpotId);
            }
        });

        Set<UUID> entityIds = Collections.emptySet();
        EntityGatheringSpot gatheringSpot = new SimpleEntityGatheringSpot(gatheringSpotId.toString(), gatherables, entityIds);
        gatheringSpotRepository.save(gatheringSpot);
        return gatheringSpotId;
    }

    @Override
    public void addEntityToGatheringSpot(UUID gatheringSpotId, UUID entityId) {
        EntityGatheringSpot gatheringSpot = gatheringSpotRepository.getEntityGatheringSpot(gatheringSpotId.toString());
        Set<UUID> entityIds = gatheringSpot.getEntityIds();
        entityIds.add(entityId);
        EntityGatheringSpot newGatheringSpot = new SimpleEntityGatheringSpot(gatheringSpotId.toString(),
                gatheringSpot.getEntityGatherables(), entityIds);
        gatheringSpotRepository.save(newGatheringSpot);
    }

    @Override
    public void removeEntityFromGatheringSpot(UUID gatheringSpotId, UUID entityId) {
        EntityGatheringSpot gatheringSpot = gatheringSpotRepository.getEntityGatheringSpot(gatheringSpotId.toString());
        Set<UUID> entityIds = gatheringSpot.getEntityIds();
        entityIds.remove(entityId);
        EntityGatheringSpot newGatheringSpot = new SimpleEntityGatheringSpot(gatheringSpotId.toString(),
                gatheringSpot.getEntityGatherables(), entityIds);
        gatheringSpotRepository.save(newGatheringSpot);
    }
}
