package gg.warcraft.gathering.gatherable

import gg.warcraft.gathering.GatheringSpotService
import gg.warcraft.monolith.api.block.BlockPreBreakEvent
import gg.warcraft.monolith.api.core.event.{ Event, PreEvent }

class BlockGatherableEventHandler(
    private implicit val gatheringSpotService: GatheringSpotService,
    private implicit val gatherableService: BlockGatherableService
) extends Event.Handler {
  override def reduce[T <: PreEvent](event: T): T = event match {
    case it: BlockPreBreakEvent => reducePreBreak(it).asInstanceOf[T]
    case _                      => event
  }

  private def reducePreBreak(event: BlockPreBreakEvent): BlockPreBreakEvent = {
    import event.{block, playerId}
      gatheringSpotService.getGatheringSpots
        .filter(_.contains(block))
        .foreach(spot => {
          spot.blockGatherables
            .find(_.matches(block))
            .map(gatherableService.gatherBlock(spot, _, block, playerId))
            .map(if (_) {
              return event
                .copy(alternativeDrops = Some(List()), explicitlyAllowed = true)
            })
        })

    event
  }
}
