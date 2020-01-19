package gg.warcraft.gathering.spigot;

import com.google.inject.Injector;
import com.google.inject.Module;
import gg.warcraft.gathering.api.config.GatheringConfiguration;
import gg.warcraft.gathering.gatherable.BlockGatherable;
import gg.warcraft.gathering.gatherable.EntityGatherable;
import gg.warcraft.gathering.api.gatherable.GatherableFactory;
import gg.warcraft.gathering.api.gatherable.service.EntityGatherableCommandService;
import gg.warcraft.gathering.api.item.ResourceBuilder;
import gg.warcraft.gathering.api.item.ResourceBuilderFactory;
import gg.warcraft.gathering.api.spot.service.GatheringSpotCommandService;
import gg.warcraft.gathering.app.config.GatheringMapperModule;
import gg.warcraft.gathering.app.event.handler.BlockGatheredEventHandler;
import gg.warcraft.gathering.app.event.handler.EntityGatheredEventHandler;
import gg.warcraft.gathering.app.event.handler.ServerShutdownEventHandler;
import gg.warcraft.monolith.api.Monolith;
import gg.warcraft.monolith.api.MonolithPluginUtils;
import gg.warcraft.monolith.api.core.EventService;
import gg.warcraft.monolith.api.util.TimeUtils;
import gg.warcraft.monolith.api.world.Location;
import gg.warcraft.monolith.api.world.WorldService;
import gg.warcraft.monolith.api.world.block.Block;
import gg.warcraft.monolith.api.world.block.BlockTypeVariantOrState;
import gg.warcraft.monolith.api.world.block.box.BoundingBlockBox;
import gg.warcraft.monolith.api.world.block.box.BoundingBlockBoxFactory;
import gg.warcraft.monolith.api.world.item.ItemService;
import gg.warcraft.monolith.api.world.item.ItemTypeOrVariant;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.function.Predicate;

public class GatheringPlugin extends JavaPlugin {

    private void readGatheringConfiguration(GatheringConfiguration configuration, Injector injector) {
        GatherableFactory gatherableFactory = injector.getInstance(GatherableFactory.class);
        BoundingBlockBoxFactory boundingBoxFactory = injector.getInstance(BoundingBlockBoxFactory.class);
        ResourceBuilderFactory resourceBuilderFactory = injector.getInstance(ResourceBuilderFactory.class);
        WorldService worldService = injector.getInstance(WorldService.class);
        ItemService itemService = injector.getInstance(ItemService.class);
        TimeUtils timeUtils = injector.getInstance(TimeUtils.class);
        GatheringSpotCommandService gatheringSpotCommandService = injector.getInstance(GatheringSpotCommandService.class);
        configuration.getBlockGatheringSpots().forEach(blockGatheringSpotConfiguration -> {
            BoundingBlockBox boundingBox = boundingBoxFactory.createBoundingBlockBox(
                    blockGatheringSpotConfiguration.getBoundingBox().getWorld(),
                    blockGatheringSpotConfiguration.getBoundingBox().getMinimumcorner().toVector3i(),
                    blockGatheringSpotConfiguration.getBoundingBox().getMaximumcorner().toVector3i());
            BlockTypeVariantOrState gatherableBlockData = worldService.parseData(blockGatheringSpotConfiguration.getBlockType());
            BlockGatherable gatherable = gatherableFactory.createBlockGatherable(
                    block -> block.hasData(gatherableBlockData),
                    worldService.parseData(blockGatheringSpotConfiguration.getCooldownType()),
                    () -> {
                        ItemTypeOrVariant type = itemService.parseData(blockGatheringSpotConfiguration.getDrop().getType());
                        String name = blockGatheringSpotConfiguration.getDrop().getName();
                        ResourceBuilder builder = resourceBuilderFactory.createResourceBuilder(type, name);
                        return Collections.singletonList(builder.build());
                    },
                    () -> timeUtils.createDurationInSeconds(blockGatheringSpotConfiguration.getCooldownInSeconds()));
            Predicate<Block> containsBlock = block -> boundingBox.test(block.location());
            String gatheringSpotId = blockGatheringSpotConfiguration.getId();
            gatheringSpotCommandService.createBlockGatheringSpot(gatheringSpotId, containsBlock, Collections.singletonList(gatherable));
        });
        configuration.getEntityGatheringSpots().forEach(entityGatheringSpotConfiguration -> {
            Location spawnLocation = entityGatheringSpotConfiguration.getSpawnLocation().toBlockLocation().toLocation();
            EntityGatherable gatherable = gatherableFactory.createEntityGatherable(
                    entityGatheringSpotConfiguration.getEntityType(),
                    entityGatheringSpotConfiguration.getEntityCount(),
                    () -> spawnLocation,
                    () -> {
                        ItemTypeOrVariant type = itemService.parseData(entityGatheringSpotConfiguration.getDrop().getType());
                        String name = entityGatheringSpotConfiguration.getDrop().getName();
                        ResourceBuilder builder = resourceBuilderFactory.createResourceBuilder(type, name);
                        return Collections.singletonList(builder.build());
                    },
                    () -> timeUtils.createDurationInSeconds(entityGatheringSpotConfiguration.getCooldownInSeconds()));
            String gatheringSpotId = entityGatheringSpotConfiguration.getId();
            gatheringSpotCommandService.createEntityGatheringSpot(gatheringSpotId, Collections.singletonList(gatherable));
        });
    }

    @Override
    public void onEnable() {
        FileConfiguration localConfig = getConfig();
        Injector injector = Monolith.getInstance().getInjector();

        removeExistingEntityGatherables(injector);

        MonolithPluginUtils pluginUtils = injector.getInstance(MonolithPluginUtils.class);
        String configurationType = localConfig.getString("configurationType");
        String configurationFileName = localConfig.getString("configurationFileName");
        GatheringConfiguration configuration = pluginUtils.loadConfiguration(configurationType, configurationFileName,
                localConfig.saveToString(), GatheringConfiguration.class, new GatheringMapperModule());
        readGatheringConfiguration(configuration, injector);
    }
}
