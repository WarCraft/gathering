package gg.warcraft.gathering.api.gatherable

import gg.warcraft.monolith.api.core.Duration
import gg.warcraft.monolith.api.world.WorldService
import gg.warcraft.monolith.api.world.block.{Block, BlockTypeVariantOrState}
import gg.warcraft.monolith.api.world.item.{Item, ItemService}

import scala.util.Random

class BlockGatherable(
    blockDataString: String,
    dropDataString: String,
    dropName: String,
    cooldown: Int,
    cooldownDelta: Int,
    cooldownDataString: String
)(
    private implicit val itemService: ItemService,
    private implicit val worldService: WorldService
) {
  private val blockData = worldService.parseData(blockDataString)
  private val dropData = itemService.parseData(dropDataString)

  val cooldownData: BlockTypeVariantOrState =
    worldService.parseData(cooldownDataString)

  def matches(block: Block): Boolean =
    block.hasData(blockData)

  def generateDrop: Item =
    itemService.create(dropData).withName(dropName)

  def generateCooldown: Duration =
    Duration.ofSeconds(cooldown + Random.nextInt(cooldownDelta))
}
