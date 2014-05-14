package de.doridian.restartifempty.base;

import de.doridian.multicraft.api.MulticraftAPI;

import java.io.File;
import java.util.Collections;

public class RestarterThread extends Thread {
    private boolean running;

    private static RestarterThread instance = null;

    private final String apiURL;
    private final String apiUser;
    private final String apiKey;
    private final String serverID;

    public static void startMe(File configFolder) {
        Configuration config = new Configuration(new File(configFolder, "config.txt"));
        stopMe();
        instance = new RestarterThread(config.getValue("api-url"), config.getValue("api-user"), config.getValue("api-key"), config.getValue("server-id"));
        instance.start();
    }

    private RestarterThread(String apiURL, String apiUser, String apiKey, String serverID) {
        this.running = true;
        this.apiURL = apiURL;
        this.apiUser = apiUser;
        this.apiKey = apiKey;
        this.serverID = serverID;
        setDaemon(true);
    }

    public static void stopMe() {
        if(instance != null)
            instance.running = false;
    }

    @Override
    public void run() {
        File checkForMe = new File(System.getProperty("user.home") + "/deploy/restart_if_empty");
        while(running) {
            try {
                Thread.sleep(5000);
            } catch (Exception e) { }
            if(checkForMe.exists()) {
                final MulticraftAPI api = new MulticraftAPI(apiURL, apiUser, apiKey);
                api.call("restartServer", Collections.singletonMap("id", serverID));
                return;
            }
        }
    }
}
