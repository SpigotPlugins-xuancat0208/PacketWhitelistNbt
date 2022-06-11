package xuan.cat.packetwhitelistnbt.code.branch.v19;

import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Recipe;
import xuan.cat.packetwhitelistnbt.api.branch.BranchPacket;
import xuan.cat.packetwhitelistnbt.api.branch.packet.*;
import xuan.cat.packetwhitelistnbt.code.branch.v19.packet.Branch_19_PacketEntityEquipment;
import xuan.cat.packetwhitelistnbt.code.branch.v19.packet.Branch_19_PacketRecipeUpdate;
import xuan.cat.packetwhitelistnbt.code.branch.v19.packet.Branch_19_PacketSetSlot;
import xuan.cat.packetwhitelistnbt.code.branch.v19.packet.Branch_19_PacketWindowItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class Branch_19_Packet implements BranchPacket {
    @Override
    public void convertSetSlot(PacketSetSlotEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        Branch_19_PacketSetSlot packet = new Branch_19_PacketSetSlot((ClientboundContainerSetSlotPacket) event.getPacket());
        packet.setItem(convert.apply(packet.getItem()));
    }

    @Override
    public void convertWindowItems(PacketWindowItemsEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        Branch_19_PacketWindowItems packet = new Branch_19_PacketWindowItems((ClientboundContainerSetContentPacket) event.getPacket());
        List<org.bukkit.inventory.ItemStack> list = new ArrayList<>();
        packet.getItemList().forEach((item) -> list.add(convert.apply(item)));
        packet.setItemList(list);
    }

    @Override
    public void convertEntityEquipment(PacketEntityEquipmentEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        Branch_19_PacketEntityEquipment packet = new Branch_19_PacketEntityEquipment((ClientboundSetEquipmentPacket) event.getPacket());
        Map<EquipmentSlot, org.bukkit.inventory.ItemStack> map = new HashMap<>();
        packet.getEquipmentItemMap().forEach((equipmentSlot, item) -> map.put(equipmentSlot, convert.apply(item)));
        packet.setEquipmentItemMap(map);
    }

    @Override
    public void convertRecipeUpdate(PacketRecipeUpdateEvent event, Function<Recipe, Recipe> convert) {
        Branch_19_PacketRecipeUpdate packet = new Branch_19_PacketRecipeUpdate((ClientboundUpdateRecipesPacket) event.getPacket());
        List<Recipe> list = new ArrayList<>();
        packet.getRecipeList().forEach((item) -> list.add(convert.apply(item)));
        packet.setRecipeList(list);
    }

    @Override
    public void convertEntityMetadata(PacketEntityMetadataEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        ClientboundSetEntityDataPacket packet = (ClientboundSetEntityDataPacket) event.getPacket();
        packet.getUnpackedData().forEach(entry -> {
            if (entry.getAccessor().getSerializer() == EntityDataSerializers.ITEM_STACK) {
                SynchedEntityData.DataItem<ItemStack> dataWatcher = (SynchedEntityData.DataItem<ItemStack>) entry;
                dataWatcher.setValue(CraftItemStack.asNMSCopy(convert.apply(CraftItemStack.asBukkitCopy(dataWatcher.getValue()))));
            }
        });
    }
}
