package gg.warcraft.gathering.api.gatherable

import java.util.UUID

import gg.warcraft.monolith.api.core.event.{CancellableEvent, Event}
import gg.warcraft.monolith.api.entity.EntityType
import gg.warcraft.monolith.api.world.block.Block

// BLOCK
case class BlockPreGatherEvent(
    block: Block,
    gatheringSpotId: String,
    playerId: UUID,
    cancelled: Boolean = false,
    explicitlyAllowed: Boolean = false
) extends CancellableEvent

case class BlockGatherEvent(
    block: Block,
    gatheringSpotId: String,
    playerId: UUID
) extends Event

// ENTITY
case class EntityGatherEvent(
    entityId: UUID,
    entityType: EntityType,
    gatheringSpotId: String,
    playerId: UUID,
    cancelled: Boolean = false,
    explicitlyAllowed: Boolean = false
) extends CancellableEvent

case class EntityPreGatherEvent(
    entityId: UUID,
    entityType: EntityType,
    gatheringSpotId: String,
    playerId: UUID
) extends Event
