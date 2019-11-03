package gg.warcraft.gathering.app.item;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import gg.warcraft.gathering.api.item.ResourceBuilder;
import gg.warcraft.monolith.api.util.ColorCode;
import gg.warcraft.monolith.api.util.FormatCode;
import gg.warcraft.monolith.api.world.item.Item;
import gg.warcraft.monolith.api.world.item.ItemFactory;
import gg.warcraft.monolith.api.world.item.ItemType;

import java.util.ArrayList;
import java.util.List;

public class SimpleResourceBuilder implements ResourceBuilder {
    private final ItemFactory itemFactory;
    private final ItemType type;
    private final String name;

    private List<String> flavorText;

    @Inject
    public SimpleResourceBuilder(ItemFactory itemFactory, @Assisted ItemType type, @Assisted String name) {
        this.itemFactory = itemFactory;
        this.type = type;
        this.name = name;
        this.flavorText = new ArrayList<>();
    }

    @Override
    public ResourceBuilder addFlavorText(String flavorText) {
        this.flavorText.add(flavorText);
        return this;
    }

    @Override
    public ResourceBuilder withFlavorText(List<String> flavorText) {
        this.flavorText = flavorText;
        return this;
    }

    @Override
    public Item build() {
        ItemBuilder itemBuilder = itemFactory.createItemBuilder(type, ColorCode.WHITE + name);

        // set type at top of tooltip
        itemBuilder.addLore(ColorCode.GRAY + "Resource");

        // add flavor text
        if (!flavorText.isEmpty()) {
            itemBuilder.addLore("");
            for (String text : flavorText) {
                itemBuilder.addLore("" + FormatCode.ITALIC + ColorCode.GRAY + text);
            }
        }

        return itemBuilder.build();
    }
}
