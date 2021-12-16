package xuan.cat.packetwhitelistnbt.code.branch.v18.packet;

import net.minecraft.network.protocol.game.PacketPlayOutSetSlot;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;

import java.lang.reflect.Field;

public class Branch_18_PacketSetSlot {
    private final PacketPlayOutSetSlot packet;

    public Branch_18_PacketSetSlot(PacketPlayOutSetSlot packet) {
        this.packet = packet;
    }

    private static Field field_itemStack;
    static {
        try {
            field_itemStack = PacketPlayOutSetSlot.class.getDeclaredField("f"); // TODO 映射 itemStack
            field_itemStack.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public org.bukkit.inventory.ItemStack getItem() {
        try {
            return ((ItemStack) field_itemStack.get(packet)).getBukkitStack();
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
