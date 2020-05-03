package gg.warcraft.gathering.gatherable

import gg.warcraft.gathering.GatheringSpot
import gg.warcraft.monolith.api.block.Block
import gg.warcraft.monolith.api.core.event.{CancellableEvent, Event}
import gg.warcraft.monolith.api.entity.Entity
import gg.warcraft.monolith.api.player.Player

// BLOCK
case class BlockPreGatherEvent(
    block: Block,
    gatheringSpot: GatheringSpot,
    player: Player,
    cancelled: Boolean = false,
    explicitlyAllowed: Boolean = false
) extends CancellableEvent

case class BlockGatherEvent(
    block: Block,
    gatheringSpot: GatheringSpot,
    player: Player
) extends Event

// ENTITY
case class EntityPreGatherEvent(
    entity: Entity,
    gatheringSpot: GatheringSpot,
    player: Player,
    cancelled: Boolean = false,
    explicitlyAllowed: Boolean = false
) extends CancellableEvent

case class EntityGatherEvent(
    entity: Entity,
    gatheringSpot: GatheringSpot,
    player: Player
) extends Event
