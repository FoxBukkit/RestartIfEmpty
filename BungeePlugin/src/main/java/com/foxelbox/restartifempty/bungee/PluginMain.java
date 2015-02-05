/**
 * This file is part of RestartIfEmptyBungee.
 *
 * RestartIfEmptyBungee is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RestartIfEmptyBungee is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with RestartIfEmptyBungee.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.foxelbox.restartifempty.bungee;

import com.foxelbox.dependencies.threading.IThreadCreator;
import com.foxelbox.restartifempty.base.PlayerGetter;
import com.foxelbox.restartifempty.base.RestarterRunnable;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.GroupedThreadFactory;

public class PluginMain extends Plugin {
    @Override
    public void onEnable() {
        RestarterRunnable.startMe(new IThreadCreator() {
			private final GroupedThreadFactory groupedThreadFactory = new GroupedThreadFactory(PluginMain.this, "RestartIfEmpty-Restarter");
			@Override
			public Thread createThread(Runnable runnable) {
				return groupedThreadFactory.newThread(runnable);
			}
		}, getDataFolder(), new PlayerGetter() {
			@Override
			public boolean isEmpty() {
				return getProxy().getOnlineCount() == 0;
			}
		});
        getProxy().getPluginManager().registerCommand(this, new Command("gqueuerb", "restartifempty.queue") {
            @Override
            public void execute(CommandSender commandSender, String[] strings) {
                RestarterRunnable.initiateRestart();
                commandSender.sendMessage(new TextComponent("[RIE] Queued restart for next time the Bungee server is empty!"));
            }
        });
    }

    @Override
    public void onDisable() {
        RestarterRunnable.stopMe();
    }
}
