package gg.warcraft.gathering.api.gatherable

import gg.warcraft.monolith.api.core.event.{
  Event, EventHandler, PreEvent, ServerShutdownEvent
}
import gg.warcraft.monolith.api.entity.EntityPreFatalDamageEvent

class EntityGatherableEventHandler extends EventHandler {
  override def reduce[T <: PreEvent](event: T): T = event match {
    case entityPreFatal: EntityPreFatalDamageEvent => event
    /*
          @Subscribe
          public void onEntityPreFatalDamageEvent(EntityPreFatalDamageEvent event) {
              UUID entityId = event.getEntityId();
              gatheringSpotQueryService.getEntityGatheringSpots().stream()
                      .filter(gatheringSpot -> gatheringSpot.getEntityIds().contains(entityId))
                      .findAny()
                      .ifPresent(gatheringSpot -> {
                          Entity entity = entityQueryService.getEntity(entityId);
                          gatheringSpot.getEntityGatherables().stream()
                                  .filter(gatherable -> gatherable.getEntityType() == entity.getType())
                                  .findAny()
                                  .ifPresent(gatherable -> event.explicitlyAllow());
                      });
          }

          @Subscribe
          public void EntityFatalDamageEvent(EntityFatalDamageEvent event) {
              UUID attackerId = event.getDamage().getSource().getEntityId();
              if (attackerId == null) {
                  return;
              }

              Player attacker = playerQueryService.getPlayer(attackerId);
              if (attacker == null) {
                  return;
              }

              UUID entityId = event.getEntityId();
              gatheringSpotQueryService.getEntityGatheringSpots().stream()
                      .filter(gatheringSpot -> gatheringSpot.getEntityIds().contains(entityId))
                      .findAny()
                      .ifPresent(gatheringSpot -> {
                          Entity entity = entityQueryService.getEntity(entityId);
                          gatheringSpot.getEntityGatherables().stream()
                                  .filter(gatherable -> gatherable.getEntityType() == entity.getType())
                                  .findAny()
                                  .ifPresent(gatherable -> {
                                      String gatheringSpotId = gatheringSpot.getId();
                                      gatheringSpotCommandService.removeEntityFromGatheringSpot(gatheringSpotId, entityId);
                                      final boolean entityGathered = entityGatherableCommandService.gatherEntity(gatherable,
                                              entityId, gatheringSpotId, attackerId);

                                      if (entityGathered) {
                                          entityGatherableCommandService.respawnEntity(gatherable, gatheringSpotId);
                                      }
                                  });
                      });
          }

          @Subscribe
          public void onEntityDeathEvent(EntityDeathEvent event) {
              UUID entityId = event.getEntityId();
              gatheringSpotQueryService.getEntityGatheringSpots().stream()
                      .filter(gatheringSpot -> gatheringSpot.getEntityIds().contains(entityId))
                      .findAny()
                      .ifPresent(gatheringSpot -> {
                          Entity entity = entityQueryService.getEntity(entityId);
                          gatheringSpot.getEntityGatherables().stream()
                                  .filter(gatherable -> gatherable.getEntityType() == entity.getType())
                                  .findAny()
                                  .ifPresent(gatherable -> event.setDrops(new ArrayList<>()));
                      });
          }

          @Subscribe
          public void onGatherableEntitySpawnedEvent(GatherableEntityRespawnedEvent event) {
              String gatheringSpotId = event.getGatheringSpotId();
              UUID entityId = event.getEntityId();
              gatheringSpotCommandService.addEntityToGatheringSpot(gatheringSpotId, entityId);
          }
     */
    case _ => event
  }
}
