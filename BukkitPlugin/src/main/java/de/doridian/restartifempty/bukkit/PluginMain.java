package de.doridian.restartifempty.bukkit;

import de.doridian.restartifempty.base.PlayerGetter;
import de.doridian.restartifempty.base.RestarterThread;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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

        getServer().getPluginCommand("queuerb").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
                RestarterThread.initiateRestart();
                commandSender.sendMessage("[RIE] Queued restart for next time the Bukkit server is empty!");
                return true;
            }
        });
    }


    @Override
    public void onDisable() {
        RestarterThread.stopMe();
    }
}
