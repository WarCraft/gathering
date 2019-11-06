package gg.warcraft.gathering.api.item;

import com.google.inject.assistedinject.Assisted;

public interface ResourceBuilderFactory {

    ResourceBuilder createResourceBuilder(@Assisted("type") String type, @Assisted("name") String name);
}
