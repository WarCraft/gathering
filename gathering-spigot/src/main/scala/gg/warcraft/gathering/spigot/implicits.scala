/*
 * MIT License
 *
 * Copyright (c) 2020 WarCraft <https://github.com/WarCraft>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package gg.warcraft.gathering.spigot

import gg.warcraft.gathering.gatherable.{
  BlockGatherableService, EntityGatherableService
}
import gg.warcraft.gathering.{GatheringConfig, GatheringSpotService}
import gg.warcraft.monolith.api.core.command.CommandService
import gg.warcraft.monolith.api.core.event.EventService
import gg.warcraft.monolith.api.core.task.TaskService
import gg.warcraft.monolith.spigot.implicits._
import org.bukkit.Server
import org.bukkit.plugin.Plugin

import java.util.logging.Logger

object implicits {
  private var _config: GatheringConfig = _

  private var _server: Server = _
  private var _plugin: Plugin = _
  private var _logger: Logger = _

  private var _commandService: CommandService = _
  private var _eventService: EventService = _
  private var _taskService: TaskService = _

  private[spigot] def init()(implicit
      server: Server,
      plugin: Plugin,
      logger: Logger,
      commandService: CommandService,
      eventService: EventService,
      taskService: TaskService
  ): Unit = {
    _server = server
    _plugin = plugin
    _logger = logger
    _commandService = commandService
    _eventService = eventService
    _taskService = taskService
  }

  private[spigot] def configure(config: GatheringConfig): Unit =
    _config = config

  // Spigot
  private implicit lazy val server: Server = _server
  private implicit lazy val plugin: Plugin = _plugin
  private implicit lazy val logger: Logger = _logger

  // Core
  private implicit lazy val commandService: CommandService = _commandService
  private implicit lazy val eventService: EventService = _eventService
  private implicit lazy val taskService: TaskService = _taskService

  // Gathering
  implicit lazy val gatheringSpotService: GatheringSpotService =
    new GatheringSpotService
  implicit lazy val entityGatherableService: EntityGatherableService =
    new EntityGatherableService
  implicit lazy val blockGatherableService: BlockGatherableService =
    new BlockGatherableService
}
