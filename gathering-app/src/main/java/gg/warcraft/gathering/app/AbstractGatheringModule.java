package gg.warcraft.gathering.app;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Names;
import gg.warcraft.gathering.api.BlockGatherable;
import gg.warcraft.gathering.api.EntityGatherable;
import gg.warcraft.gathering.api.factory.GatherableFactory;
import gg.warcraft.gathering.api.service.GatheringSpotCommandService;
import gg.warcraft.gathering.api.service.GatheringSpotQueryService;
import gg.warcraft.gathering.api.service.GatheringSpotRepository;
import gg.warcraft.gathering.app.service.DefaultGatheringSpotCommandService;
import gg.warcraft.gathering.app.service.DefaultGatheringSpotQueryService;
import gg.warcraft.gathering.app.service.DefaultGatheringSpotRepository;

public abstract class AbstractGatheringModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(GatheringSpotCommandService.class).to(DefaultGatheringSpotCommandService.class);
        bind(GatheringSpotQueryService.class).to(DefaultGatheringSpotQueryService.class);
        bind(GatheringSpotRepository.class).to(DefaultGatheringSpotRepository.class);

        install(new FactoryModuleBuilder()
                .implement(BlockGatherable.class, Names.named("block"), SimpleBlockGatherable.class)
                .implement(EntityGatherable.class, Names.named("entity"), SimpleEntityGatherable.class)
                .build(GatherableFactory.class));
    }
}
