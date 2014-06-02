/**
 * This file is part of RestartIfEmptyBukkit.
 *
 * RestartIfEmptyBukkit is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RestartIfEmptyBukkit is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with RestartIfEmptyBukkit.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.foxelbox.restartifempty.bukkit;

import com.foxelbox.restartifempty.base.PlayerGetter;
import com.foxelbox.restartifempty.base.RestarterThread;
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
