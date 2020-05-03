package gg.warcraft.gathering

import gg.warcraft.gathering.gatherable.Gatherable
import gg.warcraft.monolith.api.core.FormatCode
import gg.warcraft.monolith.api.item.{Item, ItemService}

class ResourceFactory(implicit itemService: ItemService) {
  private final val resourceTooltip = s"${FormatCode.ITALIC}Resource"

  def create(gatherable: Gatherable): Item = itemService
    .create(gatherable.dropData)
    .withName(gatherable.dropName)
    .withTooltip(resourceTooltip)
}
