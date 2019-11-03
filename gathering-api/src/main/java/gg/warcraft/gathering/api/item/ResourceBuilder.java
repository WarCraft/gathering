package gg.warcraft.gathering.api.item;

import gg.warcraft.monolith.api.world.item.Item;

import java.util.List;

public interface ResourceBuilder {

    ResourceBuilder addFlavorText(String flavorText);

    ResourceBuilder withFlavorText(List<String> flavorText);

    Item build();
}
