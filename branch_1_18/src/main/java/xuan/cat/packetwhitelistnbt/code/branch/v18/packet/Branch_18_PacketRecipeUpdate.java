package xuan.cat.packetwhitelistnbt.code.branch.v18.packet;

import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import org.bukkit.inventory.Recipe;
import xuan.cat.packetwhitelistnbt.code.branch.v18.Branch_18_RecipeSerializer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class Branch_18_PacketRecipeUpdate {
    private final ClientboundUpdateRecipesPacket packet;

    public Branch_18_PacketRecipeUpdate(ClientboundUpdateRecipesPacket packet) {
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
        recipeList.forEach(recipe -> list.add(Branch_18_RecipeSerializer.fromBukkit(recipe)));
        try {
            field_recipes.set(packet, list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
