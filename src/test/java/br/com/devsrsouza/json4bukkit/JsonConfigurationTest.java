package br.com.devsrsouza.json4bukkit;

import org.bukkit.configuration.InvalidConfigurationException;
import org.junit.Test;

public class JsonConfigurationTest {

    @Test
    public void test() {
        String json = "{\"arroz\": 152, \"feijao\": \"branco\", \"saladas\": {\"tomate\": \"verde\"}}";
        try {
            JsonConfiguration config = new JsonConfiguration();
            config.loadFromString(json);

            System.out.println(config.getValues(true));

            config.set("saladas.alface", 5000);

            System.out.println(config.saveToString());
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
