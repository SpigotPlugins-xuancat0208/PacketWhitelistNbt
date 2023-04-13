package xuan.cat.packetwhitelistnbt.code.branch.v16.packet;

import net.minecraft.server.v1_16_R3.IRecipe;
import net.minecraft.server.v1_16_R3.PacketPlayOutRecipeUpdate;
import org.bukkit.inventory.Recipe;
import xuan.cat.packetwhitelistnbt.code.branch.v16.Branch_16_RecipeSerializer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class Branch_16_PacketRecipeUpdate {
    private final PacketPlayOutRecipeUpdate packet;

    public Branch_16_PacketRecipeUpdate(PacketPlayOutRecipeUpdate packet) {
        this.packet = packet;
    }

    private static Field field_recipes;
    static {
        try {
            field_recipes = PacketPlayOutRecipeUpdate.class.getDeclaredField("a"); // TODO 映射 recipes
            field_recipes.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public List<Recipe> getRecipeList() {
        List<org.bukkit.inventory.Recipe> recipeList = new ArrayList<>();
        try {
            ((List<IRecipe<?>>) field_recipes.get(packet)).forEach(recipe -> recipeList.add(recipe.toBukkitRecipe()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return recipeList;
    }
    public void setRecipeList(List<org.bukkit.inventory.Recipe> recipeList) {
        List<IRecipe<?>> list = new ArrayList<>();
        recipeList.forEach(recipe -> list.add(Branch_16_RecipeSerializer.fromBukkit(recipe)));
        try {
            field_recipes.set(packet, list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
