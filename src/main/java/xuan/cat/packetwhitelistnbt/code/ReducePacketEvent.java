package xuan.cat.packetwhitelistnbt.code;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import xuan.cat.packetwhitelistnbt.api.branch.BranchMinecraft;
import xuan.cat.packetwhitelistnbt.api.branch.BranchPacket;
import xuan.cat.packetwhitelistnbt.code.data.ConfigData;

public final class ReducePacketEvent extends PacketAdapter {
    private final ConfigData configData;
    private final BranchPacket branchPacket;
    private final BranchMinecraft branchMinecraft;

    public ReducePacketEvent(Plugin plugin, ConfigData configData, BranchPacket branchPacket, BranchMinecraft branchMinecraft) {
        super(plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.SET_SLOT, PacketType.Play.Server.WINDOW_ITEMS, PacketType.Play.Server.ENTITY_EQUIPMENT, PacketType.Play.Server.RECIPE_UPDATE, PacketType.Play.Server.ENTITY_METADATA);
        this.configData = configData;
        this.branchPacket = branchPacket;
        this.branchMinecraft = branchMinecraft;
    }

    public void onPacketSending(PacketEvent event) {
        if (event.isCancelled() || event.isReadOnly())
            return;
        Player player = event.getPlayer();
        if (player.hasPermission("packetwhitelist.ignore_item_allowed_tag"))
            return;

        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            PacketType packetType = event.getPacketType();

            if (packetType == PacketType.Play.Server.SET_SLOT) {
                branchPacket.convertSetSlot(event.getPacket(), configData::filtrationItem);
            } else if (packetType == PacketType.Play.Server.WINDOW_ITEMS) {
                branchPacket.convertWindowItems(event.getPacket(), configData::filtrationItem);
            } else if (packetType == PacketType.Play.Server.ENTITY_EQUIPMENT) {
                branchPacket.convertEntityEquipment(event.getPacket(), configData::filtrationItem);
            } else if (packetType == PacketType.Play.Server.RECIPE_UPDATE) {
                branchPacket.convertRecipeUpdate(event.getPacket(), recipe -> branchMinecraft.filtrationRecipe(recipe, configData::filtrationItem));
            } else if (packetType == PacketType.Play.Server.ENTITY_METADATA) {
                branchPacket.convertEntityMetadata(event.getPacket(), configData::filtrationItem);
            }
        }
    }

    public void onPacketReceiving(PacketEvent event) {
    }
}
