package xuan.cat.packetwhitelistnbt.code.branch.v17;

import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.DataWatcher;
import net.minecraft.network.syncher.DataWatcherRegistry;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Recipe;
import xuan.cat.packetwhitelistnbt.api.branch.BranchPacket;
import xuan.cat.packetwhitelistnbt.api.branch.packet.*;
import xuan.cat.packetwhitelistnbt.code.branch.v17.packet.Branch_17_PacketEntityEquipment;
import xuan.cat.packetwhitelistnbt.code.branch.v17.packet.Branch_17_PacketRecipeUpdate;
import xuan.cat.packetwhitelistnbt.code.branch.v17.packet.Branch_17_PacketSetSlot;
import xuan.cat.packetwhitelistnbt.code.branch.v17.packet.Branch_17_PacketWindowItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class Branch_17_Packet implements BranchPacket {
    @Override
    public void convertSetSlot(PacketSetSlotEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        Branch_17_PacketSetSlot packet = new Branch_17_PacketSetSlot((PacketPlayOutSetSlot) event.getPacket());
        packet.setItem(convert.apply(packet.getItem()));
    }

    @Override
    public void convertWindowItems(PacketWindowItemsEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        Branch_17_PacketWindowItems packet = new Branch_17_PacketWindowItems((PacketPlayOutWindowItems) event.getPacket());
        List<org.bukkit.inventory.ItemStack> list = new ArrayList<>();
        packet.getItemList().forEach((item) -> list.add(convert.apply(item)));
        packet.setItemList(list);
    }

    @Override
    public void convertEntityEquipment(PacketEntityEquipmentEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        Branch_17_PacketEntityEquipment packet = new Branch_17_PacketEntityEquipment((PacketPlayOutEntityEquipment) event.getPacket());
        Map<EquipmentSlot, org.bukkit.inventory.ItemStack> map = new HashMap<>();
        packet.getEquipmentItemMap().forEach((equipmentSlot, item) -> map.put(equipmentSlot, convert.apply(item)));
        packet.setEquipmentItemMap(map);
    }

    @Override
    public void convertRecipeUpdate(PacketRecipeUpdateEvent event, Function<Recipe, Recipe> convert) {
        Branch_17_PacketRecipeUpdate packet = new Branch_17_PacketRecipeUpdate((PacketPlayOutRecipeUpdate) event.getPacket());
        List<Recipe> list = new ArrayList<>();
        packet.getRecipeList().forEach((item) -> list.add(convert.apply(item)));
        packet.setRecipeList(list);
    }

    @Override
    public void convertEntityMetadata(PacketEntityMetadataEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        PacketPlayOutEntityMetadata packet = (PacketPlayOutEntityMetadata) event.getPacket();
        packet.b().forEach(entry -> {
            if (entry.a().b() == DataWatcherRegistry.g) { // ITEM
                DataWatcher.Item<ItemStack> dataWatcher = (DataWatcher.Item<ItemStack>) entry;
                dataWatcher.a(CraftItemStack.asNMSCopy(convert.apply(CraftItemStack.asBukkitCopy(dataWatcher.b()))));
            }
        });
    }
}
