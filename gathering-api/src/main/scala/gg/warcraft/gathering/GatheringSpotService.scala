package gg.warcraft.gathering

class GatheringSpotService {
  private var _gatheringSpots: Map[String, GatheringSpot] = Map.empty

  def gatheringSpots: Map[String, GatheringSpot] = _gatheringSpots

  def addGatheringSpot(spot: GatheringSpot): Boolean =
    if (!_gatheringSpots.contains(spot.id)) {
      _gatheringSpots += (spot.id -> spot)
      true
    } else false

  def removeGatheringSpot(id: String): Boolean =
    if (_gatheringSpots.contains(id)) {
      _gatheringSpots -= id
      true
    } else false
}
