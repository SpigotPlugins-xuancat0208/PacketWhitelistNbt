package xuan.cat.packetwhitelistnbt.code.branch.v16.packet;

import net.minecraft.server.v1_16_R3.ItemStack;
import net.minecraft.server.v1_16_R3.NonNullList;
import net.minecraft.server.v1_16_R3.PacketPlayOutWindowItems;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class Branch_16_PacketWindowItems {
    private final PacketPlayOutWindowItems packet;

    public Branch_16_PacketWindowItems(PacketPlayOutWindowItems packet) {
        this.packet = packet;
    }

    private static Field field_Items;
    static {
        try {
            field_Items = PacketPlayOutWindowItems.class.getDeclaredField("b"); // TODO 映射 items
            field_Items.setAccessible(true);
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
}
