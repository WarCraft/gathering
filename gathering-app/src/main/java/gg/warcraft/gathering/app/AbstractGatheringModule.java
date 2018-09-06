package gg.warcraft.gathering.app;

import com.google.inject.PrivateModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Names;
import gg.warcraft.gathering.api.gatherable.BlockGatherable;
import gg.warcraft.gathering.api.gatherable.EntityGatherable;
import gg.warcraft.gathering.api.gatherable.GatherableFactory;
import gg.warcraft.gathering.api.gatherable.service.BlockGatherableCommandService;
import gg.warcraft.gathering.api.gatherable.service.EntityGatherableCommandService;
import gg.warcraft.gathering.api.gatherable.service.EntityGatherableQueryService;
import gg.warcraft.gathering.api.gatherable.service.EntityGatherableRepository;
import gg.warcraft.gathering.api.item.ResourceBuilder;
import gg.warcraft.gathering.api.item.ResourceBuilderFactory;
import gg.warcraft.gathering.api.spot.service.GatheringSpotCommandService;
import gg.warcraft.gathering.api.spot.service.GatheringSpotQueryService;
import gg.warcraft.gathering.api.spot.service.GatheringSpotRepository;
import gg.warcraft.gathering.app.gatherable.SimpleBlockGatherable;
import gg.warcraft.gathering.app.gatherable.SimpleEntityGatherable;
import gg.warcraft.gathering.app.gatherable.service.DefaultBlockGatherableCommandService;
import gg.warcraft.gathering.app.gatherable.service.DefaultEntityGatherableCommandService;
import gg.warcraft.gathering.app.gatherable.service.DefaultEntityGatherableQueryService;
import gg.warcraft.gathering.app.gatherable.service.DefaultEntityGatherableRepository;
import gg.warcraft.gathering.app.item.SimpleResourceBuilder;
import gg.warcraft.gathering.app.spot.service.DefaultGatheringSpotCommandService;
import gg.warcraft.gathering.app.spot.service.DefaultGatheringSpotQueryService;
import gg.warcraft.gathering.app.spot.service.DefaultGatheringSpotRepository;

public abstract class AbstractGatheringModule extends PrivateModule {

    @Override
    protected void configure() {
        // Gathering spot bindings
        bind(GatheringSpotCommandService.class).to(DefaultGatheringSpotCommandService.class);
        expose(GatheringSpotCommandService.class);

        bind(GatheringSpotQueryService.class).to(DefaultGatheringSpotQueryService.class);
        expose(GatheringSpotQueryService.class);

        bind(GatheringSpotRepository.class).to(DefaultGatheringSpotRepository.class);
        expose(GatheringSpotRepository.class);

        // Gatherable bindings
        bind(BlockGatherableCommandService.class).to(DefaultBlockGatherableCommandService.class);
        expose(BlockGatherableCommandService.class);

        bind(EntityGatherableCommandService.class).to(DefaultEntityGatherableCommandService.class);
        expose(EntityGatherableCommandService.class);

        bind(EntityGatherableQueryService.class).to(DefaultEntityGatherableQueryService.class);
        expose(EntityGatherableQueryService.class);

        bind(EntityGatherableRepository.class).to(DefaultEntityGatherableRepository.class);
        expose(EntityGatherableRepository.class);

        install(new FactoryModuleBuilder()
                .implement(BlockGatherable.class, Names.named("block"), SimpleBlockGatherable.class)
                .implement(EntityGatherable.class, Names.named("entity"), SimpleEntityGatherable.class)
                .build(GatherableFactory.class));
        expose(GatherableFactory.class);

        install(new FactoryModuleBuilder()
                .implement(ResourceBuilder.class, SimpleResourceBuilder.class)
                .build(ResourceBuilderFactory.class));
        expose(ResourceBuilderFactory.class);
    }
}
