package de.doridian.restartifempty.bungee;

import de.doridian.restartifempty.base.PlayerGetter;
import de.doridian.restartifempty.base.RestarterThread;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
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
        getProxy().getPluginManager().registerCommand(this, new Command("gqueuerb", "restartifempty.queue") {
            @Override
            public void execute(CommandSender commandSender, String[] strings) {
                RestarterThread.initiateRestart();
                commandSender.sendMessage(new TextComponent("[RIE] Queued restart for next time the Bungee server is empty!"));
            }
        });
    }

    @Override
    public void onDisable() {
        RestarterThread.stopMe();
    }
}
