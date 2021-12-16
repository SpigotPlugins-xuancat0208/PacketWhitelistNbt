package xuan.cat.packetwhitelistnbt.code.branch.v18;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.IRecipe;
import net.minecraft.world.item.crafting.IRecipeComplex;
import net.minecraft.world.item.crafting.RecipeBlasting;
import net.minecraft.world.item.crafting.RecipeCampfire;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.item.crafting.RecipeSmithing;
import net.minecraft.world.item.crafting.RecipeSmoking;
import net.minecraft.world.item.crafting.RecipeStonecutting;
import net.minecraft.world.item.crafting.ShapedRecipes;
import net.minecraft.world.item.crafting.ShapelessRecipes;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftBlastingRecipe;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftCampfireRecipe;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftComplexRecipe;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftFurnaceRecipe;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftShapedRecipe;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftShapelessRecipe;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftSmithingRecipe;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftSmokingRecipe;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftStonecuttingRecipe;
import org.bukkit.craftbukkit.v1_18_R1.util.CraftNamespacedKey;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.ComplexRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.inventory.SmokingRecipe;
import org.bukkit.inventory.StonecuttingRecipe;

public class Branch_18_RecipeSerializer {
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
        } else {
            if (recipe instanceof ComplexRecipe) {
                try {
                    return (IRecipeComplex)field_CraftComplexRecipe_recipe.get(recipe);
                } catch (IllegalAccessException var9) {
                    var9.printStackTrace();
                }
            } else {
                if (recipe instanceof FurnaceRecipe) {
                    CraftFurnaceRecipe craftRecipe = CraftFurnaceRecipe.fromBukkitRecipe((FurnaceRecipe)recipe);
                    return new net.minecraft.world.item.crafting.FurnaceRecipe(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.getGroup(), craftRecipe.toNMS(craftRecipe.getInputChoice(), true), CraftItemStack.asNMSCopy(craftRecipe.getResult()), craftRecipe.getExperience(), craftRecipe.getCookingTime());
                }

                int width;
                if (recipe instanceof ShapedRecipe) {
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
                }

                if (recipe instanceof ShapelessRecipe) {
                    CraftShapelessRecipe craftRecipe = CraftShapelessRecipe.fromBukkitRecipe((ShapelessRecipe)recipe);
                    List<RecipeChoice> choiceList = craftRecipe.getChoiceList();
                    NonNullList<RecipeItemStack> data = NonNullList.a(choiceList.size(), RecipeItemStack.a);

                    for(width = 0; width < choiceList.size(); ++width) {
                        data.set(width, craftRecipe.toNMS((RecipeChoice)choiceList.get(width), true));
                    }

                    return new ShapelessRecipes(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.getGroup(), CraftItemStack.asNMSCopy(craftRecipe.getResult()), data);
                }

                if (recipe instanceof SmithingRecipe) {
                    CraftSmithingRecipe craftRecipe = CraftSmithingRecipe.fromBukkitRecipe((SmithingRecipe)recipe);
                    return new RecipeSmithing(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.toNMS(craftRecipe.getBase(), true), craftRecipe.toNMS(craftRecipe.getAddition(), true), CraftItemStack.asNMSCopy(craftRecipe.getResult()), craftRecipe.willCopyNbt());
                }

                if (recipe instanceof SmokingRecipe) {
                    CraftSmokingRecipe craftRecipe = CraftSmokingRecipe.fromBukkitRecipe((SmokingRecipe)recipe);
                    return new RecipeSmoking(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.getGroup(), craftRecipe.toNMS(craftRecipe.getInputChoice(), true), CraftItemStack.asNMSCopy(craftRecipe.getResult()), craftRecipe.getExperience(), craftRecipe.getCookingTime());
                }

                if (recipe instanceof StonecuttingRecipe) {
                    CraftStonecuttingRecipe craftRecipe = CraftStonecuttingRecipe.fromBukkitRecipe((StonecuttingRecipe)recipe);
                    return new RecipeStonecutting(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.getGroup(), craftRecipe.toNMS(craftRecipe.getInputChoice(), true), CraftItemStack.asNMSCopy(craftRecipe.getResult()));
                }
            }

            return null;
        }
    }
}
