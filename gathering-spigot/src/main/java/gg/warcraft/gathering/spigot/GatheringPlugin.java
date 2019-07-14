package gg.warcraft.gathering.spigot;

import com.google.inject.Injector;
import com.google.inject.Module;
import gg.warcraft.gathering.api.config.GatheringConfiguration;
import gg.warcraft.gathering.api.gatherable.BlockGatherable;
import gg.warcraft.gathering.api.gatherable.EntityGatherable;
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
import gg.warcraft.monolith.api.item.ItemType;
import gg.warcraft.monolith.api.util.TimeUtils;
import gg.warcraft.monolith.api.world.block.Block;
import gg.warcraft.monolith.api.world.block.box.BoundingBlockBox;
import gg.warcraft.monolith.api.world.block.box.BoundingBlockBoxFactory;
import gg.warcraft.monolith.api.world.location.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.joml.Vector3i;

import java.util.Collections;
import java.util.function.Predicate;

public class GatheringPlugin extends JavaPlugin {

    private void removeExistingEntityGatherables(Injector injector) {
        EntityGatherableCommandService entityGatherableCommandService =
                injector.getInstance(EntityGatherableCommandService.class);
        entityGatherableCommandService.removeAllEntities();
    }

    private void readGatheringConfiguration(GatheringConfiguration configuration, Injector injector) {
        GatherableFactory gatherableFactory = injector.getInstance(GatherableFactory.class);
        BoundingBlockBoxFactory boundingBoxFactory = injector.getInstance(BoundingBlockBoxFactory.class);
        ResourceBuilderFactory resourceBuilderFactory = injector.getInstance(ResourceBuilderFactory.class);
        TimeUtils timeUtils = injector.getInstance(TimeUtils.class);
        GatheringSpotCommandService gatheringSpotCommandService = injector.getInstance(GatheringSpotCommandService.class);
        configuration.getBlockGatheringSpots().forEach(blockGatheringSpotConfiguration -> {
            BoundingBlockBox boundingBox = boundingBoxFactory.createBoundingBlockBox(
                    blockGatheringSpotConfiguration.getBoundingBox().getWorld(),
                    new Vector3i(blockGatheringSpotConfiguration.getBoundingBox().getMinimumcorner().getX(),
                            blockGatheringSpotConfiguration.getBoundingBox().getMinimumcorner().getY(),
                            blockGatheringSpotConfiguration.getBoundingBox().getMinimumcorner().getZ()),
                    new Vector3i(blockGatheringSpotConfiguration.getBoundingBox().getMaximumcorner().getX(),
                            blockGatheringSpotConfiguration.getBoundingBox().getMaximumcorner().getY(),
                            blockGatheringSpotConfiguration.getBoundingBox().getMaximumcorner().getZ()));
            BlockGatherable gatherable = gatherableFactory.createBlockGatherable(
                    blockGatheringSpotConfiguration.getBlockType()::equals,
                    blockGatheringSpotConfiguration.getCooldownType(),
                    () -> {
                        ItemType type = blockGatheringSpotConfiguration.getDrop().getType();
                        String name = blockGatheringSpotConfiguration.getDrop().getName();
                        ResourceBuilder builder = resourceBuilderFactory.createResourceBuilder(type, name);
                        return Collections.singletonList(builder.build());
                    },
                    () -> timeUtils.createDurationInSeconds(blockGatheringSpotConfiguration.getCooldownInSeconds()));
            Predicate<Block> containsBlock = block -> boundingBox.test(block.getLocation());
            String gatheringSpotId = blockGatheringSpotConfiguration.getId();
            gatheringSpotCommandService.createBlockGatheringSpot(gatheringSpotId, containsBlock, Collections.singletonList(gatherable));
        });
        configuration.getEntityGatheringSpots().forEach(entityGatheringSpotConfiguration -> {
            Location spawnLocation = new Location(
                    entityGatheringSpotConfiguration.getSpawnLocation().getWorld(),
                    entityGatheringSpotConfiguration.getSpawnLocation().getX(),
                    entityGatheringSpotConfiguration.getSpawnLocation().getY(),
                    entityGatheringSpotConfiguration.getSpawnLocation().getZ());
            EntityGatherable gatherable = gatherableFactory.createEntityGatherable(
                    entityGatheringSpotConfiguration.getEntityType(),
                    entityGatheringSpotConfiguration.getEntityCount(),
                    () -> spawnLocation,
                    () -> {
                        ItemType type = entityGatheringSpotConfiguration.getDrop().getType();
                        String name = entityGatheringSpotConfiguration.getDrop().getName();
                        ResourceBuilder builder = resourceBuilderFactory.createResourceBuilder(type, name);
                        return Collections.singletonList(builder.build());
                    },
                    () -> timeUtils.createDurationInSeconds(entityGatheringSpotConfiguration.getCooldownInSeconds()));
            String gatheringSpotId = entityGatheringSpotConfiguration.getId();
            gatheringSpotCommandService.createEntityGatheringSpot(gatheringSpotId, Collections.singletonList(gatherable));
        });
    }

    private void initializeMonolithEventHandlers(Injector injector) {
        EventService eventService = injector.getInstance(EventService.class);
        BlockGatheredEventHandler blockGatheredEventHandler = injector.getInstance(BlockGatheredEventHandler.class);
        EntityGatheredEventHandler entityGatheredEventHandler = injector.getInstance(EntityGatheredEventHandler.class);
        ServerShutdownEventHandler serverShutdownEventHandler = injector.getInstance(ServerShutdownEventHandler.class);
        eventService.subscribe(blockGatheredEventHandler);
        eventService.subscribe(entityGatheredEventHandler);
        eventService.subscribe(serverShutdownEventHandler);
    }

    @Override
    public void onLoad() {
        saveDefaultConfig();
        Module spigotGatheringModule = new SpigotGatheringModule(this);
        Monolith.registerModule(spigotGatheringModule);
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

        initializeMonolithEventHandlers(injector);
    }
}
