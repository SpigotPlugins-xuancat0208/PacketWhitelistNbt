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


//    /**
//     * @param event 庫存欄位
//     */
//    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
//    public void on(ServerSetSlotPacketEvent event) {
//        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
//            event.setItem(configData.filtrationItem(event.getItem()));
//        }
//    }
//
//    /**
//     * @param event 庫存全部物品
//     */
//    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
//    public void on(ServerWindowItemsPacketEvent event) {
//        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
//            List<ItemStack> itemList = new ArrayList<>();
//            event.getItemList().forEach(item -> itemList.add(configData.filtrationItem(item)));
//            event.setItemList(itemList);
//        }
//    }
//
//    /**
//     * @param event 實體裝備物品
//     */
//    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
//    public void on(ServerEntityEquipmentPacketEvent event) {
//        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
//            Map<EquipmentSlot, ItemStack> equipmentSlotMap = new HashMap<>();
//            event.getEquipmentItemMap().forEach((equipmentSlot, item) -> equipmentSlotMap.put(equipmentSlot, configData.filtrationItem(item)));
//            event.setEquipmentItemMap(equipmentSlotMap);
//        }
//    }
//
//    /**
//     * @param event 合成配方更新
//     */
//    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
//    public void on(ServerRecipeUpdatePacketEvent event) {
//        List<Recipe> recipeList = new ArrayList<>();
//        event.getRecipeList().forEach(recipe -> recipeList.add(configData.filtrationRecipe(recipe)));
//        event.setRecipeList(recipeList);
//    }

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
