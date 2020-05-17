package gg.warcraft.gathering.spigot

import gg.warcraft.gathering.GatheringConfig
import gg.warcraft.gathering.gatherable.{
  BlockGatherableEventHandler, EntityGatherableEventHandler
}
import gg.warcraft.monolith.spigot.SpigotMonolithPlugin
import gg.warcraft.monolith.spigot.implicits._
import io.circe.generic.auto._
import io.circe.parser._
import io.getquill.{SnakeCase, SqliteDialect}

class GatheringPlugin extends SpigotMonolithPlugin {
  import implicits._

  override def onLoad(): Unit = {
    super.onLoad()

    implicit val databaseContext: DatabaseContext =
      initDatabase(SqliteDialect, SnakeCase, getDataFolder)
    upgradeDatabase(getDataFolder, getClassLoader)

    implicits.init()
  }

  override def onEnable(): Unit = {
    decode[GatheringConfig](getConfig.saveToString()) match {
      case Left(err) =>
        getLogger.severe(s"Failed to parse configuration with $err")
      case Right(config) =>
        config.gatheringSpots.foreach(gatheringSpotService.addGatheringSpot)
    }

    eventService.subscribe(new BlockGatherableEventHandler)
    eventService.subscribe(new EntityGatherableEventHandler)
  }
}
