package xuan.cat.packetwhitelistnbt.api.branch.packet;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public final class PacketEntityMetadataEvent extends PacketEvent {
    private static final HandlerList handlers = new HandlerList();
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }

    private final Object packet;

    public PacketEntityMetadataEvent(Player player, Object packet) {
        super(player);
        this.packet = packet;
    }

    public Object getPacket() {
        return packet;
    }
}