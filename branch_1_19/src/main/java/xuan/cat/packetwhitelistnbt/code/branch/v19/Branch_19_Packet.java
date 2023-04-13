package xuan.cat.packetwhitelistnbt.code.branch.v19;

import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.Recipe;
import xuan.cat.packetwhitelistnbt.api.branch.BranchPacket;
import xuan.cat.packetwhitelistnbt.api.branch.packet.*;
import xuan.cat.packetwhitelistnbt.code.branch.v19.packet.*;

import java.lang.reflect.Field;
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
        packet.setCarriedItem(convert.apply(packet.getCarriedItem()));
    }

    @Override
    public void convertWindowMerchants(PacketOpenWindowMerchantEvent event, Function<MerchantRecipe, MerchantRecipe> convert) {
        Branch_19_PacketOpenWindowMerchant packet = new Branch_19_PacketOpenWindowMerchant((ClientboundMerchantOffersPacket) event.getPacket());
        List<MerchantRecipe> list = new ArrayList<>();
        packet.getRecipeList().forEach((item) -> list.add(convert.apply(item)));
        packet.setRecipeList(list);
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

    private static Field field_DataValue_value;
    static {
        try {
            field_DataValue_value = SynchedEntityData.DataValue.class.getDeclaredField("c"); // value
            field_DataValue_value.setAccessible(true);
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void convertEntityMetadata(PacketEntityMetadataEvent event, Function<org.bukkit.inventory.ItemStack, org.bukkit.inventory.ItemStack> convert) {
        ClientboundSetEntityDataPacket packet = (ClientboundSetEntityDataPacket) event.getPacket();
        packet.packedItems().forEach(entry -> {
            if (entry.serializer() == EntityDataSerializers.ITEM_STACK) {
                SynchedEntityData.DataValue<ItemStack> dataWatcher = (SynchedEntityData.DataValue<ItemStack>) entry;
                try {
                    field_DataValue_value.set(dataWatcher, CraftItemStack.asNMSCopy(convert.apply(CraftItemStack.asBukkitCopy(dataWatcher.value()))));
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
