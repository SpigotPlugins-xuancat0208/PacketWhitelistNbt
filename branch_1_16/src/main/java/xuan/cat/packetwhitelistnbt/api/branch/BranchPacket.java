package xuan.cat.packetwhitelistnbt.api.branch;

import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.function.Function;

public interface BranchPacket {
    void convertSetSlot(PacketContainer container, Function<ItemStack, ItemStack> convert);

    void convertWindowItems(PacketContainer container, Function<ItemStack, ItemStack> convert);

    void convertEntityEquipment(PacketContainer container, Function<ItemStack, ItemStack> convert);

    void convertRecipeUpdate(PacketContainer container, Function<Recipe, Recipe> convert);

    void convertEntityMetadata(PacketContainer container, Function<ItemStack, ItemStack> convert);
}
