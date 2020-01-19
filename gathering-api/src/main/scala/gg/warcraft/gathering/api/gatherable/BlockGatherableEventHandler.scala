package gg.warcraft.gathering.api.gatherable

import gg.warcraft.gathering.api.GatheringSpotService
import gg.warcraft.monolith.api.core.event.{EventHandler, PreEvent}
import gg.warcraft.monolith.api.world.block.BlockPreBreakEvent

class BlockGatherableEventHandler(
    private implicit val blockGatherableService: BlockGatherableService,
    private implicit val gatheringSpotService: GatheringSpotService
) extends EventHandler {
  override def reduce[T <: PreEvent](event: T): T = event match {
    case event: BlockPreBreakEvent =>
      val playerId = event.playerId
      if (playerId == null) return event

      val block = event.block
      gatheringSpotService
        .getGatheringSpots
        .filter(_.contains(block))
        .foreach(spot => {
          spot.blockGatherables
            .find(_.matches(block))
            .map(blockGatherableService.gatherBlock(spot, _, block, playerId))
            .map(if (_) {
              return event
                .copy(alternativeDrops = Some(List()), explicitlyAllowed = true)
                .asInstanceOf[T]
            })
        })

      event
    case _ => event
  }
}
