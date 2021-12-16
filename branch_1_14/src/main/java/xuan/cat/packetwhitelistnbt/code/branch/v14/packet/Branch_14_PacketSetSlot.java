package xuan.cat.packetwhitelistnbt.code.branch.v14.packet;

import net.minecraft.server.v1_14_R1.ItemStack;
import net.minecraft.server.v1_14_R1.PacketPlayOutSetSlot;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;

import java.lang.reflect.Field;

public final class Branch_14_PacketSetSlot {
    private final PacketPlayOutSetSlot packet;

    public Branch_14_PacketSetSlot(PacketPlayOutSetSlot packet) {
        this.packet = packet;
    }

    private static Field field_itemStack;
    static {
        try {
            field_itemStack = PacketPlayOutSetSlot.class.getDeclaredField("c"); // TODO 映射 itemStack
            field_itemStack.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public org.bukkit.inventory.ItemStack getItem() {
        try {
            return CraftItemStack.asBukkitCopy((ItemStack) field_itemStack.get(packet));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public void setItem(org.bukkit.inventory.ItemStack item) {
        try {
            field_itemStack.set(packet, CraftItemStack.asNMSCopy(item));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
