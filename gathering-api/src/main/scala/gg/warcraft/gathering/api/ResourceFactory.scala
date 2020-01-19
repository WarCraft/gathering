package gg.warcraft.gathering.api

import gg.warcraft.gathering.api.gatherable.Gatherable
import gg.warcraft.monolith.api.util.FormatCode
import gg.warcraft.monolith.api.world.item.{Item, ItemService}

class ResourceFactory(
    private implicit val itemService: ItemService
) {
  private final val resourceTooltip = s"${FormatCode.ITALIC}Resource"

  def create(gatherable: Gatherable): Item = itemService
    .create(gatherable.dropData)
    .withName(gatherable.dropName)
    .withTooltip(resourceTooltip)
}
