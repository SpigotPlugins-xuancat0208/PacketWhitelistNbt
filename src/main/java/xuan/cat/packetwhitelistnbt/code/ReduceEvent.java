package xuan.cat.packetwhitelistnbt.code;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import xuan.cat.packetwhitelistnbt.code.data.ConfigData;

public final class ReduceEvent implements Listener {
    private final ConfigData configData;
    private final ReduceServer reduceServer;

    public ReduceEvent(ConfigData configData, ReduceServer reduceServer) {
        this.configData = configData;
        this.reduceServer = reduceServer;
    }

    /**
     * @param event 玩家切換遊戲模式
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void on(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE && event.getNewGameMode() == GameMode.CREATIVE) {
            Bukkit.getScheduler().runTask(Index.getPlugin(), player::updateInventory);
        } else if (player.getGameMode() == GameMode.CREATIVE && event.getNewGameMode() != GameMode.CREATIVE) {
            Bukkit.getScheduler().runTask(Index.getPlugin(), player::updateInventory);
        }
    }



}
