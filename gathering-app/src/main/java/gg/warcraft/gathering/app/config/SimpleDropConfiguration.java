package gg.warcraft.gathering.app.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gg.warcraft.gathering.api.config.DropConfiguration;

public class SimpleDropConfiguration implements DropConfiguration {
    private final String type;
    private final String name;

    @JsonCreator
    public SimpleDropConfiguration(@JsonProperty("type") String type, @JsonProperty("name") String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }
}
