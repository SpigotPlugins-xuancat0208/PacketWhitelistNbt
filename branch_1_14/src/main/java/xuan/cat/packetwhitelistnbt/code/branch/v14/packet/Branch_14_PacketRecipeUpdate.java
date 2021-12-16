package xuan.cat.packetwhitelistnbt.code.branch.v14.packet;

import net.minecraft.server.v1_14_R1.IRecipe;
import net.minecraft.server.v1_14_R1.PacketPlayOutRecipeUpdate;
import org.bukkit.inventory.Recipe;
import xuan.cat.packetwhitelistnbt.code.branch.v14.Branch_14_RecipeSerializer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class Branch_14_PacketRecipeUpdate {
    private final PacketPlayOutRecipeUpdate packet;

    public Branch_14_PacketRecipeUpdate(PacketPlayOutRecipeUpdate packet) {
        this.packet = packet;
    }


    private static Field field_ClientboundUpdateRecipesPacket_recipes;
    static {
        try {
            field_ClientboundUpdateRecipesPacket_recipes = PacketPlayOutRecipeUpdate.class.getDeclaredField("a"); // TODO 映射 recipes
            field_ClientboundUpdateRecipesPacket_recipes.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public List<Recipe> getRecipeList() {
        List<org.bukkit.inventory.Recipe> recipeList = new ArrayList<>();
        try {
            ((List<IRecipe<?>>) field_ClientboundUpdateRecipesPacket_recipes.get(packet)).forEach(recipe -> recipeList.add(recipe.toBukkitRecipe()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return recipeList;
    }
    public void setRecipeList(List<org.bukkit.inventory.Recipe> recipeList) {
        List<IRecipe<?>> list = new ArrayList<>();
        recipeList.forEach(recipe -> list.add(Branch_14_RecipeSerializer.fromBukkit(recipe)));
        try {
            field_ClientboundUpdateRecipesPacket_recipes.set(packet, list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
