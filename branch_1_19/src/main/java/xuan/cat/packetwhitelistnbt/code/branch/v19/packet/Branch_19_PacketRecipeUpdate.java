package xuan.cat.packetwhitelistnbt.code.branch.v19.packet;

import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import org.bukkit.inventory.Recipe;
import xuan.cat.packetwhitelistnbt.code.branch.v19.Branch_19_RecipeSerializer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class Branch_19_PacketRecipeUpdate {
    private final ClientboundUpdateRecipesPacket packet;

    public Branch_19_PacketRecipeUpdate(ClientboundUpdateRecipesPacket packet) {
        this.packet = packet;
    }

    private static Field field_recipes;
    static {
        try {
            field_recipes = ClientboundUpdateRecipesPacket.class.getDeclaredField("a"); // TODO 映射 recipes
            field_recipes.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public List<Recipe> getRecipeList() {
        List<org.bukkit.inventory.Recipe> recipeList = new ArrayList<>();
        packet.getRecipes().forEach(recipe -> recipeList.add(recipe.toBukkitRecipe()));
        return recipeList;
    }
    public void setRecipeList(List<org.bukkit.inventory.Recipe> recipeList) {
        List<net.minecraft.world.item.crafting.Recipe<?>> list = new ArrayList<>();
        recipeList.forEach(recipe -> list.add(Branch_19_RecipeSerializer.fromBukkit(recipe)));
        try {
            field_recipes.set(packet, list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
