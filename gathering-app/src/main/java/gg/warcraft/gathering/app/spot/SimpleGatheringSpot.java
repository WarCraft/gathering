package gg.warcraft.gathering.app.spot;

import gg.warcraft.gathering.api.spot.GatheringSpot;

import java.util.UUID;

public class SimpleGatheringSpot implements GatheringSpot {
    private final UUID id;

    public SimpleGatheringSpot(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }
}
