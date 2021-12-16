package xuan.cat.packetwhitelistnbt.code.branch.v17.packet;

import net.minecraft.network.protocol.game.PacketPlayOutRecipeUpdate;
import net.minecraft.world.item.crafting.IRecipe;
import org.bukkit.inventory.Recipe;
import xuan.cat.packetwhitelistnbt.code.branch.v17.Branch_17_RecipeSerializer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class Branch_17_PacketRecipeUpdate {
    private final PacketPlayOutRecipeUpdate packet;

    public Branch_17_PacketRecipeUpdate(PacketPlayOutRecipeUpdate packet) {
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
        packet.b().forEach(recipe -> recipeList.add(recipe.toBukkitRecipe()));
        return recipeList;
    }
    public void setRecipeList(List<org.bukkit.inventory.Recipe> recipeList) {
        List<IRecipe<?>> list = new ArrayList<>();
        recipeList.forEach(recipe -> list.add(Branch_17_RecipeSerializer.fromBukkit(recipe)));
        try {
            field_ClientboundUpdateRecipesPacket_recipes.set(packet, list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
