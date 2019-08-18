# Json4Bukkit
Json FileConfiguration support (Bukkit).

## Usage
```java
File file = File(plugin.getDataFolder(), "myconfig.json")

FileConfiguration config = JsonConfiguration.loadConfiguration(file);

config.set("test", "my little test");

config.save(file);
```
