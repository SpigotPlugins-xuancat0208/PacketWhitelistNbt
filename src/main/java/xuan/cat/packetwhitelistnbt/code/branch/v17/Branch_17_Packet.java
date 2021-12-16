package xuan.cat.packetwhitelistnbt.code.branch.v17;

import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import xuan.cat.packetwhitelistnbt.api.branch.BranchPacket;

import java.util.function.Function;

public final class Branch_17_Packet implements BranchPacket {
    @Override
    public void convertSetSlot(PacketContainer container, Function<ItemStack, ItemStack> convert) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void convertWindowItems(PacketContainer container, Function<ItemStack, ItemStack> convert) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void convertEntityEquipment(PacketContainer container, Function<ItemStack, ItemStack> convert) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void convertRecipeUpdate(PacketContainer container, Function<Recipe, Recipe> convert) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void convertEntityMetadata(PacketContainer container, Function<ItemStack, ItemStack> convert) {
        throw new UnsupportedOperationException();
    }
}
