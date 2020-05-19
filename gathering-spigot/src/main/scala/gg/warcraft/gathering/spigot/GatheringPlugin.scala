package gg.warcraft.gathering.spigot

import gg.warcraft.gathering.GatheringConfig
import gg.warcraft.gathering.gatherable.{ BlockGatherableEventHandler, EntityGatherableEventHandler }
import gg.warcraft.monolith.api.core.Codecs.Circe.enumDecoder
import gg.warcraft.monolith.api.world.World
import gg.warcraft.monolith.spigot.SpigotMonolithPlugin
import gg.warcraft.monolith.spigot.implicits._
import io.circe.generic.auto._
import io.circe.Decoder
import io.getquill.{ SnakeCase, SqliteDialect }

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
    implicit val worldDec: Decoder[World] = enumDecoder(World.valueOf)

    val config = parseConfig[GatheringConfig](getConfig.saveToString())
    config.gatheringSpots.foreach(gatheringSpotService.addGatheringSpot)

    eventService.subscribe(new BlockGatherableEventHandler)
    eventService.subscribe(new EntityGatherableEventHandler)
  }
}
