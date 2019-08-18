package br.com.devsrsouza.json4bukkit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

public class JsonConfiguration extends FileConfiguration {
    protected static final String BLANK_CONFIG = "{}\n";
    protected final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public String saveToString() {
        Map values = convertSectionsToMap(this);

        return gson.toJson(values);
    }

    @Override
    public void loadFromString(String contents) throws InvalidConfigurationException {
        Map<?, ?> input;
        try {
            input = gson.fromJson(contents, Map.class);
        } catch (JsonSyntaxException e) {
            throw new InvalidConfigurationException(e);
        }

        if (input != null) {
            convertMapsToSections(input, this);
        }
    }

    protected void convertMapsToSections(Map<?, ?> input, ConfigurationSection section) {
        for (Map.Entry<?, ?> entry : input.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();

            if (value instanceof Map) {
                convertMapsToSections((Map<?, ?>) value, section.createSection(key));
            } else {
                section.set(key, value);
            }
        }
    }

    protected Map convertSectionsToMap(ConfigurationSection section) {
        Map<?, ?> oldMap = section.getValues(false);
        Map newMap = new LinkedHashMap();
        for (Map.Entry<?, ?> entry : oldMap.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();

            if (value instanceof ConfigurationSection) {
                newMap.put(key, convertSectionsToMap((ConfigurationSection) value));
            } else {
                newMap.put(key, value);
            }
        }
        return newMap;
    }

    @Override
    protected String buildHeader() { return ""; }

    @Override
    public FileConfigurationOptions options() {
        return super.options();
    }

    public static JsonConfiguration loadConfiguration(File file) {
        Validate.notNull(file, "File cannot be null");

        JsonConfiguration config = new JsonConfiguration();

        try {
            config.load(file);
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, ex);
        } catch (InvalidConfigurationException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file , ex);
        }

        return config;
    }
}
