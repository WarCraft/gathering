package gg.warcraft.gathering.app.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gg.warcraft.gathering.api.config.DropConfiguration;
import gg.warcraft.monolith.api.world.item.ItemType;

public class SimpleDropConfiguration implements DropConfiguration {
    private final ItemType type;
    private final String name;

    @JsonCreator
    public SimpleDropConfiguration(@JsonProperty("type") ItemType type, @JsonProperty("name") String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public ItemType getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }
}
