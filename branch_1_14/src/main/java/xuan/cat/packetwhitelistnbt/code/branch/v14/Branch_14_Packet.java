package xuan.cat.packetwhitelistnbt.code.branch.v14;

import com.comphenix.protocol.events.PacketContainer;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.inventory.Recipe;
import xuan.cat.packetwhitelistnbt.api.branch.BranchPacket;
import xuan.cat.packetwhitelistnbt.code.branch.v14.packet.Branch_14_PacketEntityEquipment;
import xuan.cat.packetwhitelistnbt.code.branch.v14.packet.Branch_14_PacketRecipeUpdate;
import xuan.cat.packetwhitelistnbt.code.branch.v14.packet.Branch_14_PacketSetSlot;
import xuan.cat.packetwhitelistnbt.code.branch.v14.packet.Branch_14_PacketWindowItems;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class Branch_14_Packet implements BranchPacket {
    @Override
    public void convertSetSlot(PacketContainer container, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        Branch_14_PacketSetSlot packet = new Branch_14_PacketSetSlot((PacketPlayOutSetSlot) container.getHandle());
        packet.setItem(convert.apply(packet.getItem()));
    }

    @Override
    public void convertWindowItems(PacketContainer container, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        Branch_14_PacketWindowItems packet = new Branch_14_PacketWindowItems((PacketPlayOutWindowItems) container.getHandle());
        List<org.bukkit.inventory.ItemStack> list = new ArrayList<>();
        packet.getItemList().forEach((item) -> list.add(convert.apply(item)));
        packet.setItemList(list);
    }

    @Override
    public void convertEntityEquipment(PacketContainer container, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        Branch_14_PacketEntityEquipment packet = new Branch_14_PacketEntityEquipment((PacketPlayOutEntityEquipment) container.getHandle());
        packet.setItem(convert.apply(packet.getItem()));
    }

    @Override
    public void convertRecipeUpdate(PacketContainer container, Function<Recipe, Recipe> convert) {
        Branch_14_PacketRecipeUpdate packet = new Branch_14_PacketRecipeUpdate((PacketPlayOutRecipeUpdate) container.getHandle());
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
    public void convertEntityMetadata(PacketContainer container, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        PacketPlayOutEntityMetadata packet = (PacketPlayOutEntityMetadata) container.getHandle();
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
