package xuan.cat.packetwhitelistnbt.api.branch;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.function.Function;

public interface BranchMinecraft {
    Recipe filtrationRecipe(Recipe recipe, Function<ItemStack, ItemStack> convert);

    void injectPlayer(Player player);
}
