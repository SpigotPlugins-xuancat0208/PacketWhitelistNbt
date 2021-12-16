package xuan.cat.packetwhitelistnbt.code.branch.v14.packet;

import net.minecraft.server.v1_14_R1.ItemStack;
import net.minecraft.server.v1_14_R1.PacketPlayOutEntityEquipment;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;

import java.lang.reflect.Field;

public final class Branch_14_PacketEntityEquipment {
    private final PacketPlayOutEntityEquipment packet;

    public Branch_14_PacketEntityEquipment(PacketPlayOutEntityEquipment packet) {
        this.packet = packet;
    }

    private static Field field_item;
    static {
        try {
            field_item = PacketPlayOutEntityEquipment.class.getDeclaredField("c"); // TODO 映射 item
            field_item.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public org.bukkit.inventory.ItemStack getItem() {
        try {
            return CraftItemStack.asBukkitCopy((ItemStack) field_item.get(packet));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public void setItem(org.bukkit.inventory.ItemStack item) {
        try {
            field_item.set(packet, CraftItemStack.asNMSCopy(item));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
