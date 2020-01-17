package gg.warcraft.gathering.api.gatherable

import gg.warcraft.monolith.api.entity.EntityType
import gg.warcraft.monolith.api.world.item.ItemTypeOrVariant

case class EntityGatherable(
    `type`: EntityType,
    count: Int,
    dropData: ItemTypeOrVariant,
    dropName: String,
    cooldown: Int,
    cooldownDelta: Int
)
