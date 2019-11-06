package gg.warcraft.gathering.api.item;

import com.google.inject.assistedinject.Assisted;
import gg.warcraft.monolith.api.world.item.ItemTypeOrVariant;

public interface ResourceBuilderFactory {

    ResourceBuilder createResourceBuilder(@Assisted ItemTypeOrVariant type, @Assisted String name);
}
