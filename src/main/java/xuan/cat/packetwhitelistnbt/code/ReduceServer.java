package xuan.cat.packetwhitelistnbt.code;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import xuan.cat.packetwhitelistnbt.code.data.ConfigData;
import xuan.cat.packetwhitelistnbt.code.data.PlayerPermissions;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class ReduceServer {
    private final ConfigData configData;
    private final Plugin plugin;
    private final Set<BukkitTask> bukkitTasks = ConcurrentHashMap.newKeySet();
    private final Map<Player, PlayerPermissions> playerPermissionsMap = new ConcurrentHashMap<>();


    public ReduceServer(ConfigData configData, Plugin plugin) {
        this.configData = configData;
        this.plugin = plugin;

        BukkitScheduler scheduler = Bukkit.getScheduler();
        bukkitTasks.add(scheduler.runTaskTimer(plugin, this::syncRun, 0, 1));
    }


    private void syncRun() {
        playerPermissionsMap.forEach((player, permissions) -> {
            if (player.isOnline()) {
                permissions.check();
            } else {
                playerPermissionsMap.remove(player);
            }
        });
    }


    public PlayerPermissions getPermissions(Player player) {
        return playerPermissionsMap.computeIfAbsent(player, key -> new PlayerPermissions(configData, key));
    }

    public void clearPermissions(Player player) {
        playerPermissionsMap.remove(player);
    }


    public void close() {
        for (BukkitTask task : bukkitTasks)
            task.cancel();
    }
}
