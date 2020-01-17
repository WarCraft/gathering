package gg.warcraft.gathering.api

class GatheringSpotService {
def getGatheringSpot(id: String): Option[GatheringSpot] = {
  None
}

  def getGatheringSpots(): List[GatheringSpot] = {
    List()
  }

  def addGatheringSpot(spot: GatheringSpot): Boolean = {
    false
  }

  def removeGatheringSpot(id: String): Boolean = {
    false
  }
}
