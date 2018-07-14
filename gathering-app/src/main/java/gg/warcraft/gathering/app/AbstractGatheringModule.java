package gg.warcraft.gathering.app;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Names;
import gg.warcraft.gathering.api.gatherable.BlockGatherable;
import gg.warcraft.gathering.api.gatherable.EntityGatherable;
import gg.warcraft.gathering.api.gatherable.GatherableFactory;
import gg.warcraft.gathering.api.item.ResourceBuilder;
import gg.warcraft.gathering.api.item.ResourceBuilderFactory;
import gg.warcraft.gathering.api.spot.service.GatheringSpotCommandService;
import gg.warcraft.gathering.api.spot.service.GatheringSpotQueryService;
import gg.warcraft.gathering.api.spot.service.GatheringSpotRepository;
import gg.warcraft.gathering.app.gatherable.SimpleBlockGatherable;
import gg.warcraft.gathering.app.gatherable.SimpleEntityGatherable;
import gg.warcraft.gathering.app.item.SimpleResourceBuilder;
import gg.warcraft.gathering.app.spot.service.DefaultGatheringSpotCommandService;
import gg.warcraft.gathering.app.spot.service.DefaultGatheringSpotQueryService;
import gg.warcraft.gathering.app.spot.service.DefaultGatheringSpotRepository;

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

        install(new FactoryModuleBuilder()
                .implement(ResourceBuilder.class, SimpleResourceBuilder.class)
                .build(ResourceBuilderFactory.class));
    }
}
