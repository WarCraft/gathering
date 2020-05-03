package gg.warcraft.gathering

class GatheringSpotService {
  private var _gatheringSpots: List[GatheringSpot] = Nil

  def gatheringSpots: List[GatheringSpot] = _gatheringSpots

  def addGatheringSpot(spot: GatheringSpot): Boolean =
    if (!_gatheringSpots.exists { _.id == spot.id }) {
      _gatheringSpots ::= spot
      true
    } else false

  def removeGatheringSpot(id: String): Boolean =
    if (_gatheringSpots.contains(id)) {
      _gatheringSpots = _gatheringSpots.filter { _.id == id }
      true
    } else false
}
