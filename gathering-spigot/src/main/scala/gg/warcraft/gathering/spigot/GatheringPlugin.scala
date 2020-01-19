package gg.warcraft.gathering.spigot

import gg.warcraft.gathering.gatherable.{
  BlockGatherableEventHandler, BlockGatherableService, EntityGatherableEventHandler,
  EntityGatherableService
}
import gg.warcraft.gathering.{GatheringConfig, GatheringSpotService}
import gg.warcraft.monolith.api.Implicits._
import gg.warcraft.monolith.api.core.event.EventService
import org.bukkit.plugin.java.JavaPlugin

class GatheringPlugin extends JavaPlugin {
  override def onLoad(): Unit = saveDefaultConfig()

  override def onEnable()(
      implicit eventService: EventService
  ): Unit = {
    val gatheringSpotService = new GatheringSpotService
    val config: GatheringConfig = null // TODO
    config.gatheringSpots
      .map(_.toGatheringSpot)
      .foreach(gatheringSpotService.addGatheringSpot)

    val blockGatherableHandler = new BlockGatherableEventHandler(
      new BlockGatherableService,
      gatheringSpotService
    )
    eventService.subscribe(blockGatherableHandler)

    val entityGatherableHandler = new EntityGatherableEventHandler(
      new EntityGatherableService,
      gatheringSpotService
    )
    eventService.subscribe(entityGatherableHandler)
  }
}
