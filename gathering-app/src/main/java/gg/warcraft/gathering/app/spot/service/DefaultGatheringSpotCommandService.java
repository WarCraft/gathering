package gg.warcraft.gathering.app.spot.service;

import com.google.inject.Inject;
import gg.warcraft.gathering.api.config.BlockGatheringSpotConfiguration;
import gg.warcraft.gathering.api.config.EntityGatheringSpotConfiguration;
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
    private final BlockGatheringSpotConfiguration blockGatheringSpotConfiguration;
    private final EntityGatheringSpotConfiguration entityGatheringSpotConfiguration;

    @Inject
    public DefaultGatheringSpotCommandService(GatheringSpotRepository gatheringSpotRepository,
                                              EntityGatherableCommandService entityGatherableCommandService,
                                              BlockGatheringSpotConfiguration blockGatheringSpotConfiguration,
                                              EntityGatheringSpotConfiguration entityGatheringSpotConfiguration) {
        this.gatheringSpotRepository = gatheringSpotRepository;
        this.entityGatherableCommandService = entityGatherableCommandService;
        this.blockGatheringSpotConfiguration = blockGatheringSpotConfiguration;
        this.entityGatheringSpotConfiguration = entityGatheringSpotConfiguration;
    }

    @Override
    public String createBlockGatheringSpot(Predicate<Block> containsBlock, List<BlockGatherable> gatherables) {
        String id = blockGatheringSpotConfiguration.getId();
        BlockGatheringSpot gatheringSpot = new SimpleBlockGatheringSpot(id, containsBlock, gatherables);
        gatheringSpotRepository.save(gatheringSpot);
        return id;
    }

    @Override
    public String createEntityGatheringSpot(List<EntityGatherable> gatherables) {
        String gatheringSpotId = entityGatheringSpotConfiguration.getId();
        gatherables.forEach(gatherable -> {
            int entityCount = gatherable.getEntityCount();
            for (int i = 0; i < entityCount; i += 1) {
                entityGatherableCommandService.respawnEntity(gatherable, gatheringSpotId);
            }
        });

        Set<UUID> entityIds = Collections.emptySet();
        EntityGatheringSpot gatheringSpot = new SimpleEntityGatheringSpot(gatheringSpotId, gatherables, entityIds);
        gatheringSpotRepository.save(gatheringSpot);
        return gatheringSpotId;
    }

    @Override
    public void addEntityToGatheringSpot(String gatheringSpotId, UUID entityId) {
        EntityGatheringSpot gatheringSpot = gatheringSpotRepository.getEntityGatheringSpot(gatheringSpotId);
        Set<UUID> entityIds = gatheringSpot.getEntityIds();
        entityIds.add(entityId);
        EntityGatheringSpot newGatheringSpot = new SimpleEntityGatheringSpot(gatheringSpotId,
                gatheringSpot.getEntityGatherables(), entityIds);
        gatheringSpotRepository.save(newGatheringSpot);
    }

    @Override
    public void removeEntityFromGatheringSpot(String gatheringSpotId, UUID entityId) {
        EntityGatheringSpot gatheringSpot = gatheringSpotRepository.getEntityGatheringSpot(gatheringSpotId);
        Set<UUID> entityIds = gatheringSpot.getEntityIds();
        entityIds.remove(entityId);
        EntityGatheringSpot newGatheringSpot = new SimpleEntityGatheringSpot(gatheringSpotId,
                gatheringSpot.getEntityGatherables(), entityIds);
        gatheringSpotRepository.save(newGatheringSpot);
    }
}
