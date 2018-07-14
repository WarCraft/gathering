package gg.warcraft.gathering.api.item;

import gg.warcraft.monolith.api.item.ItemType;

public interface ResourceBuilderFactory {

    ResourceBuilder createResourceBuilder(ItemType type, String name);
}
