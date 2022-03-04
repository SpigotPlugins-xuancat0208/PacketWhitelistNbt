package xuan.cat.packetwhitelistnbt.code.branch.v15;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import xuan.cat.packetwhitelistnbt.api.branch.BranchPacket;
import xuan.cat.packetwhitelistnbt.api.branch.packet.*;

import java.util.function.Function;

public final class Branch_15_Packet implements BranchPacket {
    @Override
    public void convertSetSlot(PacketSetSlotEvent event, Function<ItemStack, ItemStack> convert) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void convertWindowItems(PacketWindowItemsEvent event, Function<ItemStack, ItemStack> convert) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void convertEntityEquipment(PacketEntityEquipmentEvent event, Function<ItemStack, ItemStack> convert) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void convertRecipeUpdate(PacketRecipeUpdateEvent event, Function<Recipe, Recipe> convert) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void convertEntityMetadata(PacketEntityMetadataEvent event, Function<ItemStack, ItemStack> convert) {
        throw new UnsupportedOperationException();
    }
}
