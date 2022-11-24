package xuan.cat.packetwhitelistnbt.code.branch.v19;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xuan.cat.packetwhitelistnbt.api.branch.packet.*;

public final class Branch_19_ProxyPlayerConnection {
    public static boolean read(Player player, Packet<?> packet) {
        return true;
    }


    public static boolean write(Player player, Packet<?> packet) {
        try {
            if (packet instanceof ClientboundContainerSetSlotPacket) {
                PacketSetSlotEvent event = new PacketSetSlotEvent(player, packet);
                Bukkit.getPluginManager().callEvent(event);
                return !event.isCancelled();
            } else if (packet instanceof ClientboundContainerSetContentPacket) {
                PacketWindowItemsEvent event = new PacketWindowItemsEvent(player, packet);
                Bukkit.getPluginManager().callEvent(event);
                return !event.isCancelled();
            } else if (packet instanceof ClientboundSetEquipmentPacket) {
                PacketEntityEquipmentEvent event = new PacketEntityEquipmentEvent(player, packet);
                Bukkit.getPluginManager().callEvent(event);
                return !event.isCancelled();
            } else if (packet instanceof ClientboundUpdateRecipesPacket) {
                PacketRecipeUpdateEvent event = new PacketRecipeUpdateEvent(player, packet);
                Bukkit.getPluginManager().callEvent(event);
                return !event.isCancelled();
            } else if (packet instanceof ClientboundSetEntityDataPacket) {
                PacketEntityMetadataEvent event = new PacketEntityMetadataEvent(player, packet);
                Bukkit.getPluginManager().callEvent(event);
                return !event.isCancelled();
            } else if (packet instanceof ClientboundMerchantOffersPacket) {
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
