package gg.warcraft.gathering.spigot

import gg.warcraft.gathering.gatherable.{
  BlockGatherableEventHandler, BlockGatherableService, EntityGatherableEventHandler,
  EntityGatherableService
}
import gg.warcraft.gathering.{GatheringConfig, GatheringSpotService}
import gg.warcraft.monolith.api.Implicits.{eventService, _}
import gg.warcraft.monolith.api.core.TaskService
import gg.warcraft.monolith.api.core.event.EventService
import gg.warcraft.monolith.api.entity.player.service.PlayerQueryService
import gg.warcraft.monolith.api.entity.service.{
  EntityCommandService, EntityQueryService
}
import gg.warcraft.monolith.api.world.block.backup.BlockBackupService
import org.bukkit.plugin.java.JavaPlugin

class GatheringPlugin extends JavaPlugin {
  override def onLoad(): Unit = saveDefaultConfig()

  override def onEnable(): Unit = {
    val config: GatheringConfig = null // TODO
    init(config)
  }

  private def init(
      config: GatheringConfig
  )(
      implicit eventService: EventService,
      taskService: TaskService,
      blackBackupService: BlockBackupService,
      entityService: EntityQueryService,
      entityCommandService: EntityCommandService,
      playerService: PlayerQueryService
  ): Unit = {
    val gatheringSpotService = new GatheringSpotService
    config.gatheringSpots
      .map(_.toGatheringSpot)
      .foreach(gatheringSpotService.addGatheringSpot)

    val blockGatherableHandler = new BlockGatherableEventHandler(
      gatheringSpotService,
      new BlockGatherableService
    )
    eventService.subscribe(blockGatherableHandler)

    val entityGatherableHandler = new EntityGatherableEventHandler(
      gatheringSpotService,
      new EntityGatherableService
    )
    eventService.subscribe(entityGatherableHandler)
  }
}
