package xuan.cat.packetwhitelistnbt.api.branch;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.Recipe;
import xuan.cat.packetwhitelistnbt.api.branch.packet.*;

import java.util.function.Function;

public interface BranchPacket {
    void convertSetSlot(PacketSetSlotEvent event, Function<ItemStack, ItemStack> convert);

    void convertWindowItems(PacketWindowItemsEvent event, Function<ItemStack, ItemStack> convert);

    void convertWindowMerchants(PacketOpenWindowMerchantEvent event, Function<MerchantRecipe, MerchantRecipe> convert);

    void convertEntityEquipment(PacketEntityEquipmentEvent event, Function<ItemStack, ItemStack> convert);

    void convertRecipeUpdate(PacketRecipeUpdateEvent event, Function<Recipe, Recipe> convert);

    void convertEntityMetadata(PacketEntityMetadataEvent event, Function<ItemStack, ItemStack> convert);
}
