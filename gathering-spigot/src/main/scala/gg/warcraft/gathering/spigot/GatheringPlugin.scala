package gg.warcraft.gathering.spigot

import gg.warcraft.gathering.{GatheringConfig, GatheringSpotService}
import gg.warcraft.gathering.gatherable.{
  BlockGatherableEventHandler, BlockGatherableService, EntityGatherableEventHandler,
  EntityGatherableRepository, EntityGatherableService
}
import gg.warcraft.monolith.spigot.Implicits._
import io.circe.generic.auto._
import io.circe.parser.decode
import org.bukkit.plugin.java.JavaPlugin

class GatheringPlugin extends JavaPlugin {
  override def onLoad(): Unit = saveDefaultConfig()

  override def onEnable(): Unit = {
    // spot implicits
    implicit val gatheringSpotService: GatheringSpotService =
      new GatheringSpotService

    // block implicits
    implicit val blockGatherableService: BlockGatherableService =
      new BlockGatherableService

    // entity implicits
    implicit val entityGatherableRepository: EntityGatherableRepository =
      new EntityGatherableRepository
    implicit val entityGatherableService: EntityGatherableService =
      new EntityGatherableService

    // parse config
    decode[GatheringConfig](getConfig.saveToString()) match {
      case Left(err) =>
        getLogger.severe("Failed to parse configuration: " + err.getMessage)
      case Right(config) =>
        config.gatheringSpots
          .map(_.toGatheringSpot)
          .foreach(gatheringSpotService.addGatheringSpot)
    }

    // init
    eventService.subscribe(new BlockGatherableEventHandler)
    eventService.subscribe(new EntityGatherableEventHandler)
  }
}
