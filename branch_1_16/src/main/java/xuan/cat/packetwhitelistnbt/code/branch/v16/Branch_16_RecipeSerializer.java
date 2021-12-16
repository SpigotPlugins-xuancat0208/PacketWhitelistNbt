package xuan.cat.packetwhitelistnbt.code.branch.v16;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.craftbukkit.v1_16_R3.inventory.*;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public final class Branch_16_RecipeSerializer {
    private static Field field_CraftComplexRecipe_recipe;
    static {
        try {
            field_CraftComplexRecipe_recipe = CraftComplexRecipe.class.getDeclaredField("recipe");
            field_CraftComplexRecipe_recipe.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static IRecipe fromBukkit(Recipe recipe) {
        if (recipe instanceof BlastingRecipe) {
            CraftBlastingRecipe craftRecipe = CraftBlastingRecipe.fromBukkitRecipe((BlastingRecipe)recipe);
            return new RecipeBlasting(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.getGroup(), craftRecipe.toNMS(craftRecipe.getInputChoice(), true), CraftItemStack.asNMSCopy(craftRecipe.getResult()), craftRecipe.getExperience(), craftRecipe.getCookingTime());
        } else if (recipe instanceof CampfireRecipe) {
            CraftCampfireRecipe craftRecipe = CraftCampfireRecipe.fromBukkitRecipe((CampfireRecipe)recipe);
            return new RecipeCampfire(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.getGroup(), craftRecipe.toNMS(craftRecipe.getInputChoice(), true), CraftItemStack.asNMSCopy(craftRecipe.getResult()), craftRecipe.getExperience(), craftRecipe.getCookingTime());
        } else if (recipe instanceof ComplexRecipe) {
            try {
                return (IRecipeComplex) field_CraftComplexRecipe_recipe.get(recipe);
            } catch (IllegalAccessException var9) {
                var9.printStackTrace();
                return null;
            }
        } else if (recipe instanceof FurnaceRecipe) {
            CraftFurnaceRecipe craftRecipe = CraftFurnaceRecipe.fromBukkitRecipe((FurnaceRecipe)recipe);
            return new net.minecraft.server.v1_16_R3.FurnaceRecipe(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.getGroup(), craftRecipe.toNMS(craftRecipe.getInputChoice(), true), CraftItemStack.asNMSCopy(craftRecipe.getResult()), craftRecipe.getExperience(), craftRecipe.getCookingTime());
        } else if (recipe instanceof ShapedRecipe) {
            int width;
            CraftShapedRecipe craftRecipe = CraftShapedRecipe.fromBukkitRecipe((ShapedRecipe)recipe);
            String[] shapeList = craftRecipe.getShape();
            Map<Character, RecipeChoice> choiceMap = craftRecipe.getChoiceMap();
            width = shapeList[0].length();
            NonNullList<RecipeItemStack> data = NonNullList.a(shapeList.length * width, RecipeItemStack.a);

            for(int column = 0; column < shapeList.length; ++column) {
                String shape = shapeList[column];

                for(int row = 0; row < shape.length(); ++row) {
                    data.set(column * width + row, craftRecipe.toNMS((RecipeChoice)choiceMap.get(shape.charAt(row)), false));
                }
            }

            return new ShapedRecipes(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.getGroup(), width, shapeList.length, data, CraftItemStack.asNMSCopy(craftRecipe.getResult()));
        } else if (recipe instanceof ShapelessRecipe) {
            int width;
            CraftShapelessRecipe craftRecipe = CraftShapelessRecipe.fromBukkitRecipe((ShapelessRecipe)recipe);
            List<RecipeChoice> choiceList = craftRecipe.getChoiceList();
            NonNullList<RecipeItemStack> data = NonNullList.a(choiceList.size(), RecipeItemStack.a);

            for(width = 0; width < choiceList.size(); ++width) {
                data.set(width, craftRecipe.toNMS(choiceList.get(width), true));
            }

            return new ShapelessRecipes(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.getGroup(), CraftItemStack.asNMSCopy(craftRecipe.getResult()), data);
        } else if (recipe instanceof SmithingRecipe) {
            CraftSmithingRecipe craftRecipe = CraftSmithingRecipe.fromBukkitRecipe((SmithingRecipe)recipe);
            return new RecipeSmithing(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.toNMS(craftRecipe.getBase(), true), craftRecipe.toNMS(craftRecipe.getAddition(), true), CraftItemStack.asNMSCopy(craftRecipe.getResult()));
        } else if (recipe instanceof SmokingRecipe) {
            CraftSmokingRecipe craftRecipe = CraftSmokingRecipe.fromBukkitRecipe((SmokingRecipe)recipe);
            return new RecipeSmoking(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.getGroup(), craftRecipe.toNMS(craftRecipe.getInputChoice(), true), CraftItemStack.asNMSCopy(craftRecipe.getResult()), craftRecipe.getExperience(), craftRecipe.getCookingTime());
        } else if (recipe instanceof StonecuttingRecipe) {
            CraftStonecuttingRecipe craftRecipe = CraftStonecuttingRecipe.fromBukkitRecipe((StonecuttingRecipe)recipe);
            return new RecipeStonecutting(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.getGroup(), craftRecipe.toNMS(craftRecipe.getInputChoice(), true), CraftItemStack.asNMSCopy(craftRecipe.getResult()));
        } else {
            return null;
        }
    }
}
