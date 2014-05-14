package de.doridian.restartifempty.bungee;

import de.doridian.restartifempty.base.PlayerGetter;
import de.doridian.restartifempty.base.RestarterThread;
import net.md_5.bungee.api.plugin.Plugin;

public class PluginMain extends Plugin {
    @Override
    public void onEnable() {
        RestarterThread.startMe(getDataFolder(), new PlayerGetter() {
            @Override
            public boolean isEmpty() {
                return getProxy().getOnlineCount() == 0;
            }
        });
    }

    @Override
    public void onDisable() {
        RestarterThread.stopMe();
    }
}
