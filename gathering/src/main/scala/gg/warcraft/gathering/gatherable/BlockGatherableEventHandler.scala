package gg.warcraft.gathering.gatherable

import gg.warcraft.gathering.GatheringSpotService
import gg.warcraft.monolith.api.core.event.{EventHandler, PreEvent}
import gg.warcraft.monolith.api.world.block.BlockPreBreakEvent

class BlockGatherableEventHandler(
    private val blockGatherableService: BlockGatherableService,
    private val gatheringSpotService: GatheringSpotService
) extends EventHandler {
  override def reduce[T <: PreEvent](event: T): T = event match {
    case preBreak: BlockPreBreakEvent =>
      val playerId = preBreak.playerId
      if (playerId == null) return event

      val block = preBreak.block
      gatheringSpotService.getGatheringSpots
        .filter(_.contains(block))
        .foreach(spot => {
          spot.blockGatherables
            .find(_.matches(block))
            .map(blockGatherableService.gatherBlock(spot, _, block, playerId))
            .map(if (_) {
              return preBreak
                .copy(alternativeDrops = Some(List()), explicitlyAllowed = true)
                .asInstanceOf[T]
            })
        })

      event
    case _ => event
  }
}
