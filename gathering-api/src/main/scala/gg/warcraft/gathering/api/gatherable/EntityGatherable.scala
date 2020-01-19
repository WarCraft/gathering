package gg.warcraft.gathering.api.gatherable

import gg.warcraft.monolith.api.core.Duration
import gg.warcraft.monolith.api.entity.{Entity, EntityType}
import gg.warcraft.monolith.api.world.item.{Item, ItemService}

import scala.util.Random

class EntityGatherable(
    entityTypeString: String,
    entityCount: Int,
    dropDataString: String,
    dropName: String,
    cooldown: Int,
    cooldownDelta: Int
)(
    private implicit val itemService: ItemService
) {
  private val dropData = itemService.parseData(dropDataString)

  val entityType: EntityType = EntityType.valueOf(entityTypeString)

  def matches(entity: Entity): Boolean =
    entity.getType == entityType

  def generateDrop: Item =
    itemService.create(dropData).withName(dropName)

  def generateCooldown: Duration =
    Duration.ofSeconds(cooldown + Random.nextInt(cooldownDelta))
}
