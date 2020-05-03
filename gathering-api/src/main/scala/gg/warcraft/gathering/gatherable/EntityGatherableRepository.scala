package gg.warcraft.gathering.gatherable

import java.util.UUID

import com.typesafe.config.Config

import scala.collection.mutable
import scala.concurrent.Future

private case class EntityGatherableItem(
    spotId: String,
    entityId: UUID
)

object EntityGatherableRepository {
  private val _entities = mutable.Map[String, mutable.Set[UUID]]()
}

class EntityGatherableRepository(
    override protected implicit val dbConfig: Config
) extends Repository {
  import EntityGatherableRepository._
  import db._

  def entities(spotId: String): Set[UUID] =
    _entities(spotId).asInstanceOf[Set[UUID]]

  def load(spotId: String): Set[UUID] = {
    val entityIds = db
      .run(query[EntityGatherableItem].filter(_.spotId == lift(spotId)))
      .map(_.entityId)
      .toSet
    _entities += (spotId -> entityIds.to(collection.mutable.Set))
    entityIds
  }

  def save(spotId: String, entityId: UUID): Unit = {
    val item = EntityGatherableItem(spotId, entityId)
    Future { db.run(query[EntityGatherableItem].insert(lift(item))) }
    _entities(spotId).add(entityId)
  }

  def delete(entityId: UUID): Unit = Future {
    db.run(query[EntityGatherableItem].filter(_.entityId == lift(entityId)).delete)
  }
}
