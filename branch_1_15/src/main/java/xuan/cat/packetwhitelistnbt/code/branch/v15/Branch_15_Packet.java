package xuan.cat.packetwhitelistnbt.code.branch.v15;

import net.minecraft.server.v1_15_R1.*;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.Recipe;
import xuan.cat.packetwhitelistnbt.api.branch.BranchPacket;
import xuan.cat.packetwhitelistnbt.api.branch.packet.*;
import xuan.cat.packetwhitelistnbt.code.branch.v15.packet.Branch_15_PacketEntityEquipment;
import xuan.cat.packetwhitelistnbt.code.branch.v15.packet.Branch_15_PacketRecipeUpdate;
import xuan.cat.packetwhitelistnbt.code.branch.v15.packet.Branch_15_PacketSetSlot;
import xuan.cat.packetwhitelistnbt.code.branch.v15.packet.Branch_15_PacketWindowItems;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class Branch_15_Packet implements BranchPacket {
    @Override
    public void convertSetSlot(PacketSetSlotEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        Branch_15_PacketSetSlot packet = new Branch_15_PacketSetSlot((PacketPlayOutSetSlot) event.getPacket());
        packet.setItem(convert.apply(packet.getItem()));
    }


    @Override
    public void convertWindowItems(PacketWindowItemsEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        Branch_15_PacketWindowItems packet = new Branch_15_PacketWindowItems((PacketPlayOutWindowItems) event.getPacket());
        List<org.bukkit.inventory.ItemStack> list = new ArrayList<>();
        packet.getItemList().forEach((item) -> list.add(convert.apply(item)));
        packet.setItemList(list);
    }

    @Override
    public void convertEntityEquipment(PacketEntityEquipmentEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        Branch_15_PacketEntityEquipment packet = new Branch_15_PacketEntityEquipment((PacketPlayOutEntityEquipment) event.getPacket());
        packet.setItem(convert.apply(packet.getItem()));
    }

    @Override
    public void convertRecipeUpdate(PacketRecipeUpdateEvent event, Function<Recipe, Recipe> convert) {
        Branch_15_PacketRecipeUpdate packet = new Branch_15_PacketRecipeUpdate((PacketPlayOutRecipeUpdate) event.getPacket());
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
