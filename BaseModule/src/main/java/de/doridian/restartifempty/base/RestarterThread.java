/**
 * This file is part of RestartIfEmptyBase.
 *
 * RestartIfEmptyBase is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * RestartIfEmptyBase is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with RestartIfEmptyBase.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.doridian.restartifempty.base;

import de.doridian.dependencies.config.Configuration;
import de.doridian.multicraft.api.MulticraftAPI;

import java.io.File;
import java.util.Collections;

public class RestarterThread extends Thread {
    private boolean running;

    private static RestarterThread instance = null;
    private static final File restarterFile = new File(new File(new File(System.getProperty("user.home")), "deploy"), "restart_if_empty");

    private final String apiURL;
    private final String apiUser;
    private final String apiKey;
    private final String serverID;

    private final PlayerGetter playerGetter;

    public static void startMe(File configFolder, PlayerGetter playerGetter) {
        try {
            restarterFile.delete();
        } catch (Exception e) { }
        Configuration config = new Configuration(configFolder);
        stopMe();
        instance = new RestarterThread(config.getValue("api-url", "http://multicraft.example.com/api.php"), config.getValue("api-user", "admin"), config.getValue("api-key", "invalid"), config.getValue("server-id", "1"), playerGetter);
        instance.start();
    }

    public static void initiateRestart() {
        try {
            restarterFile.createNewFile();
        } catch (Exception e) { }
    }

    private RestarterThread(String apiURL, String apiUser, String apiKey, String serverID, PlayerGetter playerGetter) {
        this.running = true;
        this.apiURL = apiURL;
        this.apiUser = apiUser;
        this.apiKey = apiKey;
        this.serverID = serverID;
        this.playerGetter = playerGetter;
        setDaemon(true);
        setName("RestartIfEmpty-RestarterThread");
    }

    public static void stopMe() {
        if(instance != null)
            instance.running = false;
    }

    @Override
    public void run() {
        while(running) {
            try {
                Thread.sleep(5000);
            } catch (Exception e) { }
            if(restarterFile.exists() && playerGetter.isEmpty() && restarterFile.delete()) {
                final MulticraftAPI api = new MulticraftAPI(apiURL, apiUser, apiKey);
                api.call("restartServer", Collections.singletonMap("id", serverID));
                return;
            }
        }
    }
}
