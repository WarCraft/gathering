package gg.warcraft.gathering.spigot;

import com.google.inject.AbstractModule;
import gg.warcraft.monolith.api.core.PluginLogger;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public class PrivateSpigotGatheringModule extends AbstractModule {
    private final Plugin plugin;

    public PrivateSpigotGatheringModule(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected void configure() {
        bind(Plugin.class).toInstance(plugin);
        bind(Logger.class).annotatedWith(PluginLogger.class).toProvider(plugin::getLogger);
    }
}
