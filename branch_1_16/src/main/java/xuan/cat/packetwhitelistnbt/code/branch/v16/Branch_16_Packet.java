package xuan.cat.packetwhitelistnbt.code.branch.v16;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Recipe;
import xuan.cat.packetwhitelistnbt.api.branch.BranchPacket;
import xuan.cat.packetwhitelistnbt.api.branch.packet.*;
import xuan.cat.packetwhitelistnbt.code.branch.v16.packet.Branch_16_PacketEntityEquipment;
import xuan.cat.packetwhitelistnbt.code.branch.v16.packet.Branch_16_PacketRecipeUpdate;
import xuan.cat.packetwhitelistnbt.code.branch.v16.packet.Branch_16_PacketSetSlot;
import xuan.cat.packetwhitelistnbt.code.branch.v16.packet.Branch_16_PacketWindowItems;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class Branch_16_Packet implements BranchPacket {
    @Override
    public void convertSetSlot(PacketSetSlotEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        Branch_16_PacketSetSlot packet = new Branch_16_PacketSetSlot((PacketPlayOutSetSlot) event.getPacket());
        packet.setItem(convert.apply(packet.getItem()));
    }

    @Override
    public void convertWindowItems(PacketWindowItemsEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        Branch_16_PacketWindowItems packet = new Branch_16_PacketWindowItems((PacketPlayOutWindowItems) event.getPacket());
        List<org.bukkit.inventory.ItemStack> list = new ArrayList<>();
        packet.getItemList().forEach((item) -> list.add(convert.apply(item)));
        packet.setItemList(list);
    }

    @Override
    public void convertEntityEquipment(PacketEntityEquipmentEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        Branch_16_PacketEntityEquipment packet = new Branch_16_PacketEntityEquipment((PacketPlayOutEntityEquipment) event.getPacket());
        Map<EquipmentSlot, org.bukkit.inventory.ItemStack> map = new HashMap<>();
        packet.getEquipmentItemMap().forEach((equipmentSlot, item) -> map.put(equipmentSlot, convert.apply(item)));
        packet.setEquipmentItemMap(map);
    }

    @Override
    public void convertRecipeUpdate(PacketRecipeUpdateEvent event, Function<Recipe, Recipe> convert) {
        Branch_16_PacketRecipeUpdate packet = new Branch_16_PacketRecipeUpdate((PacketPlayOutRecipeUpdate) event.getPacket());
        List<Recipe> list = new ArrayList<>();
        packet.getRecipeList().forEach((item) -> list.add(convert.apply(item)));
        packet.setRecipeList(list);
    }


    private static Field field_PacketPlayOutEntityMetadata_dataWatcherList;
    static {
        try {
            field_PacketPlayOutEntityMetadata_dataWatcherList = PacketPlayOutEntityMetadata.class.getDeclaredField("b"); // TODO 映射 dataWatcherList
            field_PacketPlayOutEntityMetadata_dataWatcherList.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void convertEntityMetadata(PacketEntityMetadataEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        PacketPlayOutEntityMetadata packet = (PacketPlayOutEntityMetadata) event.getPacket();
        try {
            ((List<DataWatcher.Item<?>>) field_PacketPlayOutEntityMetadata_dataWatcherList.get(packet)).forEach(entry -> {
                if (entry.a().b() == DataWatcherRegistry.g) { // ITEM
                    DataWatcher.Item<ItemStack> dataWatcher = (DataWatcher.Item<ItemStack>) entry;
                    dataWatcher.a(CraftItemStack.asNMSCopy(convert.apply(CraftItemStack.asBukkitCopy(dataWatcher.b()))));
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
