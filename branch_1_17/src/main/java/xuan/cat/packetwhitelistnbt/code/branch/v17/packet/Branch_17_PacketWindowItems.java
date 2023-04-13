package xuan.cat.packetwhitelistnbt.code.branch.v17.packet;

import net.minecraft.core.NonNullList;
import net.minecraft.network.protocol.game.PacketPlayOutWindowItems;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class Branch_17_PacketWindowItems {
    private final PacketPlayOutWindowItems packet;

    public Branch_17_PacketWindowItems(PacketPlayOutWindowItems packet) {
        this.packet = packet;
    }

    private static Field field_Items;
    private static Field field_CarriedItem;
    static {
        try {
            field_Items = PacketPlayOutWindowItems.class.getDeclaredField("c"); // TODO 映射 items
            field_CarriedItem = PacketPlayOutWindowItems.class.getDeclaredField("d"); // TODO 映射 carriedItem
            field_Items.setAccessible(true);
            field_CarriedItem.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public List<org.bukkit.inventory.ItemStack> getItemList() {
        List<org.bukkit.inventory.ItemStack> items = new ArrayList<>();
        try {
            List<ItemStack> nonNullList = ((List<ItemStack>) field_Items.get(packet));
            for (ItemStack item : nonNullList) {
                items.add(CraftItemStack.asBukkitCopy(item));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return items;
    }
    public void setItemList(List<org.bukkit.inventory.ItemStack> itemList) {
        NonNullList<ItemStack> nonNullList = NonNullList.a(itemList.size(), ItemStack.b);
        for (int slot = 0 ; slot < itemList.size() ; slot++) {
            org.bukkit.inventory.ItemStack item = itemList.get(slot);
            if (item != null) {
                nonNullList.set(slot, CraftItemStack.asNMSCopy(item));
            }
        }
        try {
            field_Items.set(packet, nonNullList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public org.bukkit.inventory.ItemStack getCarriedItem() {
        try {
            return CraftItemStack.asBukkitCopy((ItemStack) field_CarriedItem.get(packet));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public void setCarriedItem(org.bukkit.inventory.ItemStack item) {
        try {
            field_CarriedItem.set(packet, CraftItemStack.asNMSCopy(item));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
