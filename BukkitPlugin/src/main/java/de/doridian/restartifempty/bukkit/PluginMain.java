package de.doridian.restartifempty.bukkit;

import de.doridian.restartifempty.base.RestarterThread;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginMain extends JavaPlugin {
    @Override
    public void onEnable() {
        RestarterThread.startMe(getDataFolder());
    }

    @Override
    public void onDisable() {
        RestarterThread.stopMe();
    }
}
