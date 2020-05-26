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

package gg.warcraft.gathering.gatherable

import java.util.UUID

import gg.warcraft.monolith.api.util.chaining._
import io.getquill.{SnakeCase, SqliteDialect}
import io.getquill.context.jdbc.JdbcContext

import scala.concurrent.{ExecutionContext, Future}

private case class EntityGatherableItem(
    spotId: String,
    entityId: UUID
)

class EntityGatherableRepository(implicit
    database: JdbcContext[SqliteDialect, SnakeCase]
) {
  import database._

  private implicit val executionContext: ExecutionContext =
    ExecutionContext.global

  private var _gatherableEntities: Map[String, Set[UUID]] = Map.empty

  def gatherableEntities(spotId: String): Set[UUID] = _gatherableEntities(spotId)

  def load(spotId: String): Set[UUID] = {
    val entityIds = database
      .run { query[EntityGatherableItem].filter(_.spotId == lift(spotId)) }
      .map { _.entityId }
      .toSet
    _gatherableEntities += (spotId -> entityIds)
    entityIds
  }

  def save(spotId: String, entityId: UUID): Unit = {
    val item = EntityGatherableItem(spotId, entityId)
    _gatherableEntities.updatedWith(spotId) {
      case Some(value) => value + entityId |> Some.apply
      case None        => Set(entityId) |> Some.apply
    }
    Future { database.run { query[EntityGatherableItem].insert(lift(item)) } }
  }

  def delete(entityId: UUID): Unit = Future {
    database.run {
      query[EntityGatherableItem].filter(_.entityId == lift(entityId)).delete
    }
  }
}
