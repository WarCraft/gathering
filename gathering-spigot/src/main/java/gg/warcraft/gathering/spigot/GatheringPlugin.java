package gg.warcraft.gathering.spigot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Key;
import gg.warcraft.gathering.api.config.GatheringConfiguration;
import gg.warcraft.gathering.api.gatherable.BlockGatherable;
import gg.warcraft.gathering.api.gatherable.EntityGatherable;
import gg.warcraft.gathering.api.gatherable.GatherableFactory;
import gg.warcraft.gathering.api.gatherable.service.GatherableCommandService;
import gg.warcraft.gathering.api.item.ResourceBuilder;
import gg.warcraft.gathering.api.item.ResourceBuilderFactory;
import gg.warcraft.gathering.api.spot.service.GatheringSpotCommandService;
import gg.warcraft.gathering.app.config.GatheringMapperModule;
import gg.warcraft.gathering.app.event.handler.BlockGatheredEventHandler;
import gg.warcraft.gathering.app.event.handler.EntityGatheredEventHandler;
import gg.warcraft.monolith.api.Monolith;
import gg.warcraft.monolith.api.config.service.ConfigurationCommandService;
import gg.warcraft.monolith.api.config.service.ConfigurationQueryService;
import gg.warcraft.monolith.api.core.EventService;
import gg.warcraft.monolith.api.core.Yaml;
import gg.warcraft.monolith.api.item.ItemType;
import gg.warcraft.monolith.api.util.TimeUtils;
import gg.warcraft.monolith.api.world.Location;
import gg.warcraft.monolith.api.world.block.box.BoundingBlockBox;
import gg.warcraft.monolith.api.world.block.box.BoundingBlockBoxFactory;
import gg.warcraft.monolith.api.world.service.WorldQueryService;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.joml.Vector3i;

import java.io.IOException;
import java.util.Collections;
import java.util.logging.Logger;

public class GatheringPlugin extends JavaPlugin {

    void removeExistingEntityGatherables(Injector injector) {
        GatherableCommandService gatherableCommandService =
                injector.getInstance(GatherableCommandService.class);
        gatherableCommandService.removeAllEntities();
    }

    GatheringConfiguration loadLocalGatheringConfiguration(FileConfiguration localConfig, ObjectMapper yamlMapper) {
        try {
            return yamlMapper.readValue(localConfig.saveToString(), GatheringConfiguration.class);
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to load local gathering configuration: " + ex.getMessage());
        }
    }

    GatheringConfiguration loadRemoteGatheringConfiguration(FileConfiguration localConfiguration, Injector injector) {
        String configurationFileName = localConfiguration.getString("configurationFileName");
        ConfigurationQueryService configQueryService = injector.getInstance(ConfigurationQueryService.class);
        GatheringConfiguration gatheringConfiguration = configQueryService.getConfiguration(GatheringConfiguration.class);
        if (gatheringConfiguration == null) {
            Logger logger = Bukkit.getLogger();
            logger.info("Remote Chat configuration missing from cache, attempting to load...");
            try {
                ConfigurationCommandService configCommandService = injector.getInstance(ConfigurationCommandService.class);
                configCommandService.reloadConfiguration(configurationFileName, GatheringConfiguration.class);
                gatheringConfiguration = configQueryService.getConfiguration(GatheringConfiguration.class);
                logger.info("Successfully loaded remote Chat configuration.");
            } catch (IOException ex) {
                logger.warning("Exception loading remote Chat configuration: " + ex.getMessage());
                return null;
            }
        }
        return gatheringConfiguration;
    }

    GatheringConfiguration loadGatheringConfiguration(FileConfiguration localConfiguration, Injector injector) {
        ObjectMapper yamlMapper = injector.getInstance(Key.get(ObjectMapper.class, Yaml.class));
        SimpleModule gatheringMapperModule = new GatheringMapperModule();
        yamlMapper.registerModule(gatheringMapperModule);

        String configurationType = localConfiguration.getString("configurationType");
        switch (configurationType) {
            case "REMOTE":
                GatheringConfiguration gatheringConfiguration = loadRemoteGatheringConfiguration(localConfiguration, injector);
                if (gatheringConfiguration != null) {
                    return gatheringConfiguration;
                } else {
                    Logger logger = Bukkit.getLogger();
                    logger.warning("Failed to load remote Chat configuration.");
                    logger.warning("Falling back to LOCAL.");
                }
            case "LOCAL":
                return loadLocalGatheringConfiguration(localConfiguration, yamlMapper);
            default:
                Logger logger = Bukkit.getLogger();
                logger.warning("Illegal configurationType in Chat configuration: " + configurationType);
                logger.warning("Falling back to LOCAL.");
                return loadLocalGatheringConfiguration(localConfiguration, yamlMapper);
        }
    }

    void readGatheringConfiguration(GatheringConfiguration configuration, Injector injector) {
        GatherableFactory gatherableFactory = injector.getInstance(GatherableFactory.class);
        BoundingBlockBoxFactory boundingBoxFactory = injector.getInstance(BoundingBlockBoxFactory.class);
        ResourceBuilderFactory resourceBuilderFactory = injector.getInstance(ResourceBuilderFactory.class);
        TimeUtils timeUtils = injector.getInstance(TimeUtils.class);
        WorldQueryService worldQueryService = injector.getInstance(WorldQueryService.class);
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
            gatheringSpotCommandService.createBlockGatheringSpot(boundingBox, Collections.singletonList(gatherable));
        });
        configuration.getEntityGatheringSpots().forEach(entityGatheringSpotConfiguration -> {
            Location spawnLocation = worldQueryService.getLocation(
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
            gatheringSpotCommandService.createEntityGatheringSpot(Collections.singletonList(gatherable));
        });
    }

    void initializeMonolithEventHandlers(Injector injector) {
        EventService eventService = injector.getInstance(EventService.class);
        BlockGatheredEventHandler blockGatheredEventHandler = injector.getInstance(BlockGatheredEventHandler.class);
        EntityGatheredEventHandler entityGatheredEventHandler = injector.getInstance(EntityGatheredEventHandler.class);
        eventService.subscribe(blockGatheredEventHandler);
        eventService.subscribe(entityGatheredEventHandler);
    }

    @Override
    public void onLoad() {
        saveDefaultConfig();
        AbstractModule spigotGatheringModule = new SpigotGatheringModule();
        Monolith.registerModule(spigotGatheringModule);
    }

    @Override
    public void onEnable() {
        FileConfiguration localConfig = getConfig();
        Injector injector = Monolith.getInstance().getInjector();

        removeExistingEntityGatherables(injector);

        GatheringConfiguration gatheringConfiguration = loadGatheringConfiguration(localConfig, injector);
        readGatheringConfiguration(gatheringConfiguration, injector);

        initializeMonolithEventHandlers(injector);
    }
}
