package xuan.cat.packetwhitelistnbt.code.branch.v14;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import xuan.cat.packetwhitelistnbt.api.branch.BranchMinecraft;

import java.util.function.Function;

public final class Branch_14_Minecraft implements BranchMinecraft {
    @Override
    public Recipe filtrationRecipe(Recipe recipe, Function<ItemStack, ItemStack> convert) {
        throw new UnsupportedOperationException();
    }
}