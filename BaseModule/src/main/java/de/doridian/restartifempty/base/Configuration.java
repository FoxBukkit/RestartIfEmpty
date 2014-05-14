package de.doridian.restartifempty.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class Configuration {
	private HashMap<String,String> configValues = new HashMap<>();

	public Configuration(File configFile) {
		configValues.clear();
		try {
			BufferedReader stream = new BufferedReader(new FileReader(configFile));
			String line; int lpos;
			while((line = stream.readLine()) != null) {
				lpos = line.lastIndexOf('=');
				if(lpos > 0)
					configValues.put(line.substring(0,lpos), line.substring(lpos+1));
			}
			stream.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

    public String getValue(String key) {
        return getValue(key, "");
    }

	public String getValue(String key, String def) {
		if(configValues.containsKey(key)) {
			return configValues.get(key);
		}
		return def;
	}
}
