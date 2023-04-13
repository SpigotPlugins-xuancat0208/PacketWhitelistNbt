package xuan.cat.packetwhitelistnbt.code.branch.v18;

import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.Recipe;
import xuan.cat.packetwhitelistnbt.api.branch.BranchPacket;
import xuan.cat.packetwhitelistnbt.api.branch.packet.*;
import xuan.cat.packetwhitelistnbt.code.branch.v18.packet.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class Branch_18_Packet implements BranchPacket {
    @Override
    public void convertSetSlot(PacketSetSlotEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        Branch_18_PacketSetSlot packet = new Branch_18_PacketSetSlot((ClientboundContainerSetSlotPacket) event.getPacket());
        packet.setItem(convert.apply(packet.getItem()));
    }

    @Override
    public void convertWindowItems(PacketWindowItemsEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        Branch_18_PacketWindowItems packet = new Branch_18_PacketWindowItems((ClientboundContainerSetContentPacket) event.getPacket());
        List<org.bukkit.inventory.ItemStack> list = new ArrayList<>();
        packet.getItemList().forEach((item) -> list.add(convert.apply(item)));
        packet.setItemList(list);
        packet.setCarriedItem(convert.apply(packet.getCarriedItem()));
    }

    @Override
    public void convertWindowMerchants(PacketOpenWindowMerchantEvent event, Function<MerchantRecipe, MerchantRecipe> convert) {
        Branch_18_PacketOpenWindowMerchant packet = new Branch_18_PacketOpenWindowMerchant((ClientboundMerchantOffersPacket) event.getPacket());
        List<MerchantRecipe> list = new ArrayList<>();
        packet.getRecipeList().forEach((item) -> list.add(convert.apply(item)));
        packet.setRecipeList(list);
    }

    @Override
    public void convertEntityEquipment(PacketEntityEquipmentEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        Branch_18_PacketEntityEquipment packet = new Branch_18_PacketEntityEquipment((ClientboundSetEquipmentPacket) event.getPacket());
        Map<EquipmentSlot, org.bukkit.inventory.ItemStack> map = new HashMap<>();
        packet.getEquipmentItemMap().forEach((equipmentSlot, item) -> map.put(equipmentSlot, convert.apply(item)));
        packet.setEquipmentItemMap(map);
    }

    @Override
    public void convertRecipeUpdate(PacketRecipeUpdateEvent event, Function<Recipe, Recipe> convert) {
        Branch_18_PacketRecipeUpdate packet = new Branch_18_PacketRecipeUpdate((ClientboundUpdateRecipesPacket) event.getPacket());
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
