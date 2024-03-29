package xuan.cat.packetwhitelistnbt.code.branch.v16;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xuan.cat.packetwhitelistnbt.api.branch.packet.*;

public final class Branch_16_ProxyPlayerConnection {
    public static boolean read(Player player, Packet<?> packet) {
        return true;
    }


    public static boolean write(Player player, Packet<?> packet) {
        try {
            if (packet instanceof PacketPlayOutSetSlot) {
                PacketSetSlotEvent event = new PacketSetSlotEvent(player, packet);
                Bukkit.getPluginManager().callEvent(event);
                return !event.isCancelled();
            } else if (packet instanceof PacketPlayOutWindowItems) {
                PacketWindowItemsEvent event = new PacketWindowItemsEvent(player, packet);
                Bukkit.getPluginManager().callEvent(event);
                return !event.isCancelled();
            } else if (packet instanceof PacketPlayOutEntityEquipment) {
                PacketEntityEquipmentEvent event = new PacketEntityEquipmentEvent(player, packet);
                Bukkit.getPluginManager().callEvent(event);
                return !event.isCancelled();
            } else if (packet instanceof PacketPlayOutRecipeUpdate) {
                PacketRecipeUpdateEvent event = new PacketRecipeUpdateEvent(player, packet);
                Bukkit.getPluginManager().callEvent(event);
                return !event.isCancelled();
            } else if (packet instanceof PacketPlayOutEntityMetadata) {
                PacketEntityMetadataEvent event = new PacketEntityMetadataEvent(player, packet);
                Bukkit.getPluginManager().callEvent(event);
                return !event.isCancelled();
            } else if (packet instanceof PacketPlayOutOpenWindowMerchant) {
                PacketOpenWindowMerchantEvent event = new PacketOpenWindowMerchantEvent(player, packet);
                Bukkit.getPluginManager().callEvent(event);
                return !event.isCancelled();
            } else {
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return true;
        }
    }
}
