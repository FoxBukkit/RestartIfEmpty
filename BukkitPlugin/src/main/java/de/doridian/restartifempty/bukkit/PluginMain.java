package de.doridian.restartifempty.bukkit;

import de.doridian.restartifempty.base.PlayerGetter;
import de.doridian.restartifempty.base.RestarterThread;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginMain extends JavaPlugin {
    @Override
    public void onEnable() {
        RestarterThread.startMe(getDataFolder(), new PlayerGetter() {
            @Override
            public boolean isEmpty() {
                return getServer().getOnlinePlayers().length == 0;
            }
        });
    }

    @Override
    public void onDisable() {
        RestarterThread.stopMe();
    }
}
