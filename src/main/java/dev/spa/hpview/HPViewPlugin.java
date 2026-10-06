package dev.spa.hpview;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class HPViewPlugin extends JavaPlugin {

    private final Set<UUID> disabled = new HashSet<>();
    private HealthFormatter formatter;
    private File playersFile;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadSettings();
        playersFile = new File(getDataFolder(), "players.yml");
        loadDisabled();
        getServer().getPluginManager().registerEvents(new DamageListener(this), this);
        HPViewCommand command = new HPViewCommand(this);
        getCommand("hpview").setExecutor(command);
        getCommand("hpview").setTabCompleter(command);
    }

    public void reloadSettings() {
        reloadConfig();
        formatter = new HealthFormatter(getConfig());
    }

    public HealthFormatter formatter() {
        return formatter;
    }

    public boolean showPlayers() {
        return getConfig().getBoolean("show-players", true);
    }

    public boolean isEnabledFor(UUID player) {
        return !disabled.contains(player);
    }

    public void setEnabledFor(UUID player, boolean enabled) {
        boolean changed = enabled ? disabled.remove(player) : disabled.add(player);
        if (changed) {
            saveDisabled();
        }
    }

    private void loadDisabled() {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(playersFile);
        for (String id : yaml.getStringList("disabled")) {
            try {
                disabled.add(UUID.fromString(id));
            } catch (IllegalArgumentException ignored) {
                getLogger().warning("Ignored invalid UUID in players.yml: " + id);
            }
        }
    }

    private void saveDisabled() {
        YamlConfiguration yaml = new YamlConfiguration();
        yaml.set("disabled", disabled.stream().map(UUID::toString).sorted().toList());
        try {
            yaml.save(playersFile);
        } catch (IOException error) {
            getLogger().log(Level.WARNING, "Could not save players.yml", error);
        }
    }
}
