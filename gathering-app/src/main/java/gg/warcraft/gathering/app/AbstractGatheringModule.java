package gg.warcraft.gathering.app;

import com.google.inject.assistedinject.FactoryModuleBuilder;
import gg.warcraft.gathering.api.BlockGatherable;
import gg.warcraft.gathering.api.BlockGatheringSpot;
import gg.warcraft.gathering.api.EntityGatherable;
import gg.warcraft.gathering.api.EntityGatheringSpot;
import gg.warcraft.gathering.api.factory.GatherableFactory;
import gg.warcraft.gathering.api.factory.GatheringSpotFactory;
import gg.warcraft.gathering.api.service.GatheringSpotCommandService;
import gg.warcraft.gathering.api.service.GatheringSpotQueryService;
import gg.warcraft.gathering.api.service.GatheringSpotRepository;
import gg.warcraft.gathering.app.service.DefaultGatheringSpotCommandService;
import gg.warcraft.gathering.app.service.DefaultGatheringSpotQueryService;
import gg.warcraft.gathering.app.service.DefaultGatheringSpotRepository;
import gg.warcraft.monolith.api.MonolithModule;

public class AbstractGatheringModule extends MonolithModule {

    @Override
    protected void configure() {
        bind(GatheringSpotCommandService.class).to(DefaultGatheringSpotCommandService.class);
        bind(GatheringSpotQueryService.class).to(DefaultGatheringSpotQueryService.class);
        bind(GatheringSpotRepository.class).to(DefaultGatheringSpotRepository.class);

        install(new FactoryModuleBuilder()
                .implement(BlockGatherable.class, SimpleBlockGatherable.class)
                .implement(EntityGatherable.class, SimpleEntityGatherable.class)
                .build(GatherableFactory.class));
        install(new FactoryModuleBuilder()
                .implement(BlockGatheringSpot.class, SimpleBlockGatheringSpot.class)
                .implement(EntityGatheringSpot.class, SimpleEntityGatheringSpot.class)
                .build(GatheringSpotFactory.class));
    }
}
