package de.doridian.restartifempty.bungee;

import de.doridian.restartifempty.base.RestarterThread;
import net.md_5.bungee.api.plugin.Plugin;

public class PluginMain extends Plugin {
    @Override
    public void onEnable() {
        RestarterThread.startMe(getDataFolder());
    }

    @Override
    public void onDisable() {
        RestarterThread.stopMe();
    }
}
