package xuan.cat.packetwhitelistnbt.code.branch.v19;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.*;
import org.bukkit.craftbukkit.v1_19_R3.inventory.*;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public final class Branch_19_RecipeSerializer {
    private static Field field_CraftComplexRecipe_recipe;
    static {
        try {
            field_CraftComplexRecipe_recipe = CraftComplexRecipe.class.getDeclaredField("recipe");
            field_CraftComplexRecipe_recipe.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static net.minecraft.world.item.crafting.Recipe fromBukkit(Recipe recipe) {
        if (recipe instanceof org.bukkit.inventory.BlastingRecipe) {
            CraftBlastingRecipe craftRecipe = CraftBlastingRecipe.fromBukkitRecipe((org.bukkit.inventory.BlastingRecipe) recipe);
            return new net.minecraft.world.item.crafting.BlastingRecipe(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.getGroup(), CraftRecipe.getCategory(craftRecipe.getCategory()), craftRecipe.toNMS(craftRecipe.getInputChoice(), true), CraftItemStack.asNMSCopy(craftRecipe.getResult()), craftRecipe.getExperience(), craftRecipe.getCookingTime());

        } else if (recipe instanceof org.bukkit.inventory.CampfireRecipe) {
            CraftCampfireRecipe craftRecipe = CraftCampfireRecipe.fromBukkitRecipe((org.bukkit.inventory.CampfireRecipe) recipe);
            return new CampfireCookingRecipe(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.getGroup(), CraftRecipe.getCategory(craftRecipe.getCategory()), craftRecipe.toNMS(craftRecipe.getInputChoice(), true), CraftItemStack.asNMSCopy(craftRecipe.getResult()), craftRecipe.getExperience(), craftRecipe.getCookingTime());

        } else if (recipe instanceof org.bukkit.inventory.FurnaceRecipe) {
            CraftFurnaceRecipe craftRecipe = CraftFurnaceRecipe.fromBukkitRecipe((org.bukkit.inventory.FurnaceRecipe) recipe);
            return new SmeltingRecipe(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.getGroup(), CraftRecipe.getCategory(craftRecipe.getCategory()), craftRecipe.toNMS(craftRecipe.getInputChoice(), true), CraftItemStack.asNMSCopy(craftRecipe.getResult()), craftRecipe.getExperience(), craftRecipe.getCookingTime());

        } else if (recipe instanceof org.bukkit.inventory.ShapedRecipe) {
            CraftShapedRecipe craftRecipe = CraftShapedRecipe.fromBukkitRecipe((org.bukkit.inventory.ShapedRecipe) recipe);
            String[] shapeList = craftRecipe.getShape();
            Map<Character, RecipeChoice> choiceMap = craftRecipe.getChoiceMap();
            int width = shapeList[0].length();
            NonNullList<Ingredient> data = NonNullList.withSize(shapeList.length * width, Ingredient.EMPTY);
            for(int column = 0; column < shapeList.length; ++column) {
                String shape = shapeList[column];
                for(int row = 0; row < shape.length(); ++row) {
                    data.set(column * width + row, craftRecipe.toNMS(choiceMap.get(shape.charAt(row)), false));
                }
            }
            return new ShapedRecipe(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.getGroup(), CraftRecipe.getCategory(craftRecipe.getCategory()), width, shapeList.length, data, CraftItemStack.asNMSCopy(craftRecipe.getResult()));

        } else if (recipe instanceof org.bukkit.inventory.ShapelessRecipe) {
            CraftShapelessRecipe craftRecipe = CraftShapelessRecipe.fromBukkitRecipe((org.bukkit.inventory.ShapelessRecipe) recipe);
            List<RecipeChoice> choiceList = craftRecipe.getChoiceList();
            NonNullList<Ingredient> data = NonNullList.withSize(choiceList.size(), Ingredient.EMPTY);
            for(int i = 0; i < choiceList.size(); ++i) {
                data.set(i, craftRecipe.toNMS(choiceList.get(i), true));
            }
            return new ShapelessRecipe(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.getGroup(), CraftRecipe.getCategory(craftRecipe.getCategory()), CraftItemStack.asNMSCopy(craftRecipe.getResult()), data);

        } else if (recipe instanceof org.bukkit.inventory.SmithingTrimRecipe) {
            CraftSmithingTrimRecipe craftRecipe = CraftSmithingTrimRecipe.fromBukkitRecipe((org.bukkit.inventory.SmithingTrimRecipe) recipe);
            return new SmithingTrimRecipe(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.toNMS(craftRecipe.getTemplate(), true), craftRecipe.toNMS(craftRecipe.getBase(), true), craftRecipe.toNMS(craftRecipe.getAddition(), true));

        } else if (recipe instanceof org.bukkit.inventory.SmithingTransformRecipe) {
            CraftSmithingTransformRecipe craftRecipe = CraftSmithingTransformRecipe.fromBukkitRecipe((org.bukkit.inventory.SmithingTransformRecipe) recipe);
            return new SmithingTransformRecipe(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.toNMS(craftRecipe.getTemplate(), true), craftRecipe.toNMS(craftRecipe.getBase(), true), craftRecipe.toNMS(craftRecipe.getAddition(), true), CraftItemStack.asNMSCopy(craftRecipe.getResult()));

        } else if (recipe instanceof org.bukkit.inventory.SmithingRecipe) {
            CraftSmithingRecipe craftRecipe = CraftSmithingRecipe.fromBukkitRecipe((org.bukkit.inventory.SmithingRecipe) recipe);
            try {
                // 適用於 paper
                return new LegacyUpgradeRecipe(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.toNMS(craftRecipe.getBase(), true), craftRecipe.toNMS(craftRecipe.getAddition(), true), CraftItemStack.asNMSCopy(craftRecipe.getResult()), craftRecipe.willCopyNbt());
            } catch (NoSuchMethodError noSuchMethodError) {
                // 適用於 spigot (不推薦)
                return new LegacyUpgradeRecipe(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.toNMS(craftRecipe.getBase(), true), craftRecipe.toNMS(craftRecipe.getAddition(), true), CraftItemStack.asNMSCopy(craftRecipe.getResult()));
            }

        } else if (recipe instanceof org.bukkit.inventory.SmokingRecipe) {
            CraftSmokingRecipe craftRecipe = CraftSmokingRecipe.fromBukkitRecipe((org.bukkit.inventory.SmokingRecipe) recipe);
            return new SmokingRecipe(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.getGroup(), CraftRecipe.getCategory(craftRecipe.getCategory()), craftRecipe.toNMS(craftRecipe.getInputChoice(), true), CraftItemStack.asNMSCopy(craftRecipe.getResult()), craftRecipe.getExperience(), craftRecipe.getCookingTime());

        } else if (recipe instanceof org.bukkit.inventory.StonecuttingRecipe) {
            CraftStonecuttingRecipe craftRecipe = CraftStonecuttingRecipe.fromBukkitRecipe((org.bukkit.inventory.StonecuttingRecipe) recipe);
            return new StonecutterRecipe(CraftNamespacedKey.toMinecraft(craftRecipe.getKey()), craftRecipe.getGroup(), craftRecipe.toNMS(craftRecipe.getInputChoice(), true), CraftItemStack.asNMSCopy(craftRecipe.getResult()));

        } else if (recipe instanceof org.bukkit.inventory.ComplexRecipe) {
            try {
                return (CustomRecipe) field_CraftComplexRecipe_recipe.get(recipe);
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
        try {
            throw new IllegalArgumentException("Recipe miss: " + recipe.getClass());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
