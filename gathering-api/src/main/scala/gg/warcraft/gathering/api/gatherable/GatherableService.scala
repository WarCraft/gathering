package gg.warcraft.gathering.api.gatherable

import gg.warcraft.gathering.api.ResourceFactory
import gg.warcraft.monolith.api.math.Vector3f
import gg.warcraft.monolith.api.world.Location
import gg.warcraft.monolith.api.world.item.ItemService

trait GatherableService {
  protected implicit val itemService: ItemService

  protected val resourceFactory = new ResourceFactory()
  protected val dropOffset: Vector3f

  def spawnDrops(gatherable: Gatherable, loc: Location): Unit = {
    val drop = resourceFactory.create(gatherable)
    val dropLocation = loc.add(dropOffset)
    itemService.dropItems(dropLocation, drop)
  }
}
