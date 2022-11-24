package xuan.cat.packetwhitelistnbt.code;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.MerchantRecipe;
import xuan.cat.packetwhitelistnbt.api.branch.BranchMinecraft;
import xuan.cat.packetwhitelistnbt.api.branch.BranchPacket;
import xuan.cat.packetwhitelistnbt.api.branch.packet.*;
import xuan.cat.packetwhitelistnbt.code.data.ConfigData;

public final class ReduceEvent implements Listener {
    private final ConfigData configData;
    private final ReduceServer reduceServer;
    private final BranchPacket branchPacket;
    private final BranchMinecraft branchMinecraft;

    public ReduceEvent(ConfigData configData, ReduceServer reduceServer, BranchPacket branchPacket, BranchMinecraft branchMinecraft) {
        this.configData = configData;
        this.reduceServer = reduceServer;
        this.branchPacket = branchPacket;
        this.branchMinecraft = branchMinecraft;
    }

    /**
     * @param event 玩家登入
     */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void event(PlayerJoinEvent event) {
        // 注入代碼
        branchMinecraft.injectPlayer(event.getPlayer());
    }
    /**
     * @param event 玩家登出
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void on(PlayerQuitEvent event) {
        reduceServer.clearPermissions(event.getPlayer());
    }


    /**
     * @param event 設置欄位封包
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void on(PacketSetSlotEvent event) {
        Player player = event.getPlayer();
        if (reduceServer.getPermissions(player).ignoreItemTagLimit)
            return;
        if (player.getGameMode() != GameMode.CREATIVE) {
            branchPacket.convertSetSlot(event, configData::filtrationItem);
        }
    }

    /**
     * @param event 視窗物品封包
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void on(PacketWindowItemsEvent event) {
        Player player = event.getPlayer();
        if (reduceServer.getPermissions(player).ignoreItemTagLimit)
            return;
        if (player.getGameMode() != GameMode.CREATIVE) {
            branchPacket.convertWindowItems(event, configData::filtrationItem);
        }
    }

    /**
     * @param event 實體裝備封包
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void on(PacketEntityEquipmentEvent event) {
        Player player = event.getPlayer();
        if (reduceServer.getPermissions(player).ignoreItemTagLimit)
            return;
        if (player.getGameMode() != GameMode.CREATIVE) {
            branchPacket.convertEntityEquipment(event, configData::filtrationItem);
        }
    }

    /**
     * @param event 合成更新封包
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void on(PacketRecipeUpdateEvent event) {
        Player player = event.getPlayer();
        if (reduceServer.getPermissions(player).ignoreItemTagLimit)
            return;
        if (player.getGameMode() != GameMode.CREATIVE) {
            branchPacket.convertRecipeUpdate(event, recipe -> branchMinecraft.filtrationRecipe(recipe, configData::filtrationItem));
        }
    }

    /**
     * @param event 村民交易
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void on(PacketOpenWindowMerchantEvent event) {
        Player player = event.getPlayer();
        if (reduceServer.getPermissions(player).ignoreItemTagLimit)
            return;
        if (player.getGameMode() != GameMode.CREATIVE) {
            branchPacket.convertWindowMerchants(event, recipe -> (MerchantRecipe) branchMinecraft.filtrationRecipe(recipe, configData::filtrationItem));
        }
    }


    /**
     * @param event 實體資料封包
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void on(PacketEntityMetadataEvent event) {
        Player player = event.getPlayer();
        if (reduceServer.getPermissions(player).ignoreItemTagLimit)
            return;
        if (player.getGameMode() != GameMode.CREATIVE) {
            branchPacket.convertEntityMetadata(event, configData::filtrationItem);
        }
    }


    /**
     * @param event 玩家切換遊戲模式
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void on(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE && event.getNewGameMode() == GameMode.CREATIVE) {
            Bukkit.getScheduler().runTask(ReduceIndex.getPlugin(), player::updateInventory);
        } else if (player.getGameMode() == GameMode.CREATIVE && event.getNewGameMode() != GameMode.CREATIVE) {
            Bukkit.getScheduler().runTask(ReduceIndex.getPlugin(), player::updateInventory);
        }
    }

}
