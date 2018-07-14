package gg.warcraft.gathering.app.config;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import gg.warcraft.gathering.api.config.BlockGatheringSpotConfiguration;
import gg.warcraft.gathering.api.config.DropConfiguration;
import gg.warcraft.gathering.api.config.EntityGatheringSpotConfiguration;
import gg.warcraft.gathering.api.config.GatheringConfiguration;

public class GatheringMapperModule extends SimpleModule {

    public GatheringMapperModule() {
        super("GatheringMapperModule", Version.unknownVersion());
        SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
        resolver.addMapping(DropConfiguration.class, SimpleDropConfiguration.class);
        resolver.addMapping(BlockGatheringSpotConfiguration.class, SimpleBlockGatheringSpotConfiguration.class);
        resolver.addMapping(EntityGatheringSpotConfiguration.class, SimpleEntityGatheringSpotConfiguration.class);
        resolver.addMapping(GatheringConfiguration.class, SimpleGatheringConfiguration.class);
        setAbstractTypes(resolver);
    }
}
