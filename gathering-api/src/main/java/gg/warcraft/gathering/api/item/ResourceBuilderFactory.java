package gg.warcraft.gathering.api.item;

import gg.warcraft.monolith.api.world.item.ItemType;

public interface ResourceBuilderFactory {

    ResourceBuilder createResourceBuilder(ItemType type, String name);
}
