package gg.warcraft.gathering.spigot;

import gg.warcraft.gathering.app.AbstractGatheringModule;
import gg.warcraft.monolith.api.core.PluginLogger;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class SpigotGatheringModule extends AbstractGatheringModule {
    private final Plugin plugin;

    public SpigotGatheringModule(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void configure() {
        super.configure();
        bind(Plugin.class).toInstance(plugin);
        bind(Logger.class).annotatedWith(PluginLogger.class).toProvider(plugin::getLogger);
    }
}
