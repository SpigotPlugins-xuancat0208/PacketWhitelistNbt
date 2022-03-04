package xuan.cat.packetwhitelistnbt.code.branch.v17;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.kyori.adventure.text.Component;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import xuan.cat.packetwhitelistnbt.api.branch.packet.*;

import javax.annotation.Nullable;
import java.util.Set;

public final class Branch_17_ProxyPlayerConnection extends PlayerConnection {
//    public static final Set<Field> fields_ServerGamePacketListenerImpl = new HashSet<>();
//    static {
//        for (Field field : PlayerConnection.class.getDeclaredFields()) {
//            if (!Modifier.isStatic(field.getModifiers())) {
//                field.setAccessible(true);
//                fields_ServerGamePacketListenerImpl.add(field);
//            }
//        }
//    }

    private final PlayerConnection connection;

    public Branch_17_ProxyPlayerConnection(PlayerConnection connection, EntityPlayer player) {
        super(((CraftServer) Bukkit.getServer()).getServer(), connection.a, player);
        this.connection = connection;
//        try {
//            for (Field field : fields_ServerGamePacketListenerImpl) {
//                field.set(this, field.get(connection));
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }

    @Override
    public void sendPacket(Packet<?> packet) {
        a(packet, null);
    }
    @Override
    public void a(Packet<?> packet, GenericFutureListener<? extends Future<? super Void>> listener) {
        try {
            if (packet instanceof PacketPlayOutSetSlot) {
                PacketSetSlotEvent event = new PacketSetSlotEvent(getCraftPlayer(), packet);
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled())
                    return;
            } else if (packet instanceof PacketPlayOutWindowItems) {
                PacketWindowItemsEvent event = new PacketWindowItemsEvent(getCraftPlayer(), packet);
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled())
                    return;
            } else if (packet instanceof PacketPlayOutEntityEquipment) {
                PacketEntityEquipmentEvent event = new PacketEntityEquipmentEvent(getCraftPlayer(), packet);
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled())
                    return;
            } else if (packet instanceof PacketPlayOutRecipeUpdate) {
                PacketRecipeUpdateEvent event = new PacketRecipeUpdateEvent(getCraftPlayer(), packet);
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled())
                    return;
            } else if (packet instanceof PacketPlayOutEntityMetadata) {
                PacketEntityMetadataEvent event = new PacketEntityMetadataEvent(getCraftPlayer(), packet);
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled())
                    return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        connection.a(packet, listener);
    }

    @Override
    public void a(PacketPlayInKeepAlive packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInChat packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInBEdit packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInTrSel packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInBeacon packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInFlying packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInStruct packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInUseItem packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInBlockDig packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInBoatMove packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInItemName packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInPickItem packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInSettings packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInSpectate packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInAbilities packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInSetJigsaw packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInUseEntity packet) {
        connection.a(packet);
    }

    @Override
    public void a(ServerboundPongPacket packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInAutoRecipe packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInBlockPlace packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInUpdateSign packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInCloseWindow packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInEnchantItem packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInTabComplete packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInVehicleMove packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInWindowClick packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInAdvancements packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInArmAnimation packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInEntityAction packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInHeldItemSlot packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInSteerVehicle packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInTileNBTQuery packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInClientCommand packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInCustomPayload packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInDifficultyLock packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInEntityNBTQuery packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInJigsawGenerate packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInRecipeSettings packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInTeleportAccept packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInRecipeDisplayed packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInSetCommandBlock packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInSetCreativeSlot packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInDifficultyChange packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInResourcePackStatus packet) {
        connection.a(packet);
    }

    @Override
    public void a(PacketPlayInSetCommandMinecart packet) {
        connection.a(packet);
    }

    @Override
    public void a(IChatBaseComponent reason) {
        connection.a(reason);
    }

    @Override
    public void a(double x, double y, double z, float yaw, float pitch) {
        connection.a(x, y, z, yaw, pitch);
    }

    @Override
    public void a(double x, double y, double z, float yaw, float pitch, Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> flags) {
        connection.a(x, y, z, yaw, pitch, flags);
    }

    @Override
    public void a(double d0, double d1, double d2, float f, float f1, PlayerTeleportEvent.TeleportCause cause) {
        connection.a(d0, d1, d2, f, f1, cause);
    }

    @Override
    public NetworkManager a() {
        return connection.a();
    }

    @Override
    public void a(double d0, double d1, double d2, float f, float f1, Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> set, PlayerTeleportEvent.TeleportCause cause) {
        connection.a(d0, d1, d2, f, f1, set, cause);
    }

    @Override
    public boolean a(double d0, double d1, double d2, float f, float f1, Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> set, boolean flag, PlayerTeleportEvent.TeleportCause cause) {
        return connection.a(d0, d1, d2, f, f1, set, flag, cause);
    }

    @Override
    public void chat(String s, boolean async) {
        connection.chat(s, async);
    }

    @Override
    public void handleContainerClose(PacketPlayInCloseWindow packetplayinclosewindow, InventoryCloseEvent.Reason reason) {
        connection.handleContainerClose(packetplayinclosewindow, reason);
    }

    @Override
    public String getClientBrandName() {
        return connection.getClientBrandName();
    }

    @Override
    public CraftPlayer getCraftPlayer() {
        return connection.getCraftPlayer();
    }

    @Override
    public EntityPlayer d() {
        return connection.d();
    }

    @Override
    public void b(double x, double y, double z, float yaw, float pitch) {
        connection.b(x, y, z, yaw, pitch);
    }

    @Override
    public void disconnect(String s) {
        connection.disconnect(s);
    }

    @Override
    public void disconnect(String s, PlayerKickEvent.Cause cause) {
        connection.disconnect(s, cause);
    }

    @Override
    public void disconnect(Component reason, PlayerKickEvent.Cause cause) {
        connection.disconnect(reason, cause);
    }

    @Override
    public void disconnect(IChatBaseComponent reason, PlayerKickEvent.Cause cause) {
        connection.disconnect(reason, cause);
    }

    @Override
    public void teleport(Location dest) {
        connection.teleport(dest);
    }

    @Override
    public void onDisconnect(IChatBaseComponent reason, @Nullable Component quitMessage) {
        connection.onDisconnect(reason, quitMessage);
    }

    @Override
    public void b(double d0, double d1, double d2, float f, float f1, PlayerTeleportEvent.TeleportCause cause) {
        connection.b(d0, d1, d2, f, f1, cause);
    }

    @Override
    public void disconnect(IChatBaseComponent reason) {
        connection.disconnect(reason);
    }

    @Override
    public void syncPosition() {
        connection.syncPosition();
    }

    @Override
    public void tick() {
        connection.tick();
    }
}
