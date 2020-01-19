package gg.warcraft.gathering.api

import scala.collection.mutable

object GatheringSpotService {
  private val spots = mutable.Map[String, GatheringSpot]()
}

class GatheringSpotService {
  import GatheringSpotService._

  def getGatheringSpot(id: String): Option[GatheringSpot] =
    spots.get(id)

  def getGatheringSpots: Iterable[GatheringSpot] =
    spots.values

  def addGatheringSpot(spot: GatheringSpot): Boolean =
    spots.put(spot.id, spot).isEmpty

  def removeGatheringSpot(id: String): Boolean =
    spots.remove(id).isDefined
}
