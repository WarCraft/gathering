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
