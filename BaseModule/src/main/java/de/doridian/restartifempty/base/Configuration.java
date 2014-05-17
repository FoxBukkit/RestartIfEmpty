package de.doridian.restartifempty.base;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private final File configFile;
	private final HashMap<String,String> configValues;

	public Configuration(File configFolder) {
        configValues = new HashMap<>();
        configFolder.mkdirs();
        configFile = new File(configFolder, "config.txt");
        load();
    }

    public void load() {
        synchronized (configValues) {
            configValues.clear();
            try {
                BufferedReader stream = new BufferedReader(new FileReader(configFile));
                String line;
                int lpos;
                while ((line = stream.readLine()) != null) {
                    lpos = line.lastIndexOf('=');
                    if (lpos > 0)
                        configValues.put(line.substring(0, lpos), line.substring(lpos + 1));
                }
                stream.close();
            } catch (FileNotFoundException e) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

    public void save() {
        synchronized (configValues) {
            try {
                PrintWriter stream = new PrintWriter(new FileWriter(configFile));
                for (Map.Entry<String, String> configEntry : configValues.entrySet()) {
                    stream.println(configEntry.getKey() + "=" + configEntry.getValue());
                }
                stream.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

	public String getValue(String key, String def) {
        synchronized (configValues) {
            if (configValues.containsKey(key)) {
                return configValues.get(key);
            }
            configValues.put(key, def);
        }
        save();
		return def;
	}
}
