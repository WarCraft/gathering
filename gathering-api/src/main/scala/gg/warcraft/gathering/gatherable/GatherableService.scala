package gg.warcraft.gathering.gatherable

import gg.warcraft.gathering.ResourceFactory
import gg.warcraft.monolith.api.item.ItemService
import gg.warcraft.monolith.api.math.Vector3f
import gg.warcraft.monolith.api.world.Location

trait GatherableService {
  protected implicit val itemService: ItemService

  protected val resourceFactory = new ResourceFactory()
  protected val dropOffset: Vector3f

  def spawnDrops(gatherable: Gatherable, location: Location): Unit = {
    val drop = resourceFactory.create(gatherable)
    val dropLocation = location + dropOffset
    itemService.dropItems(dropLocation, drop)
  }
}
