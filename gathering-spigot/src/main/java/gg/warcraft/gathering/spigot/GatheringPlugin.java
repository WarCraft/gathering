package gg.warcraft.gathering.spigot;

import com.google.inject.AbstractModule;
import gg.warcraft.monolith.api.Monolith;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class GatheringPlugin extends JavaPlugin {

    @Override
    public void onLoad() {
        saveDefaultConfig();
        FileConfiguration localConfig = getConfig();

        AbstractModule spigotGatheringModule = new SpigotGatheringModule();
        Monolith.registerModule(spigotGatheringModule);
    }

    @Override
    public void onEnable() {

    }
}
