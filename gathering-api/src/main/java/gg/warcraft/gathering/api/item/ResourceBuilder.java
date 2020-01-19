package gg.warcraft.gathering.api.item;

import gg.warcraft.monolith.api.world.item.Item;

import java.util.List;

public interface ResourceBuilder {

    ResourceBuilder addFlavorText(String flavorText);

    ResourceBuilder withFlavorText(List<String> flavorText);

    Item build();
}

/*
public class SimpleResourceBuilder implements ResourceBuilder {
    private final ItemService itemService;
    private final ItemTypeOrVariant type;
    private final String name;

    private List<String> flavorText;

    @Inject
    public SimpleResourceBuilder(ItemService itemService, @Assisted ItemTypeOrVariant type, @Assisted String name) {
        this.itemService = itemService;
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
        Item item = itemService
                .create(type)
                .withName(ColorCode.WHITE + name)
                .addTooltip(ColorCode.GRAY + "Resource");

        // add flavor text
        if (!flavorText.isEmpty()) {
            item = item.addTooltip("");
            for (String text : flavorText) {
                item = item.addTooltip("" + FormatCode.ITALIC + ColorCode.GRAY + text);
            }
        }

        return item;
    }
}

 */