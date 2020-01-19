package gg.warcraft.gathering.api.gatherable

import gg.warcraft.monolith.api.entity.{Entity, EntityType}
import gg.warcraft.monolith.api.world.item.ItemTypeOrVariant

case class EntityGatherable(
    entityType: EntityType,
    entityCount: Int,
    dropData: ItemTypeOrVariant,
    dropName: String,
    cooldown: Int,
    cooldownDelta: Int
) {
  def matches(entity: Entity): Boolean =
    entity.getType == entityType
}
