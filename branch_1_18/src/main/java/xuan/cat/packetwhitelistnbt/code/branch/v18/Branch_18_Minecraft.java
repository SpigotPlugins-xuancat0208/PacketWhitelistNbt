package xuan.cat.packetwhitelistnbt.code.branch.v18;

import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import xuan.cat.packetwhitelistnbt.api.branch.BranchMinecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class Branch_18_Minecraft implements BranchMinecraft {
    public Recipe filtrationRecipe(Recipe recipe, Function<ItemStack, ItemStack> convert) {
        if (recipe instanceof BlastingRecipe) {
            BlastingRecipe source = (BlastingRecipe) recipe;
            return new BlastingRecipe(source.getKey(), convert.apply(source.getResult()), source.getInputChoice(), source.getExperience(), source.getCookingTime());

        } else if (recipe instanceof CampfireRecipe) {
            CampfireRecipe source = (CampfireRecipe) recipe;
            return new CampfireRecipe(source.getKey(), convert.apply(source.getResult()), source.getInputChoice(), source.getExperience(), source.getCookingTime());

        } else if (recipe instanceof FurnaceRecipe) {
            FurnaceRecipe source = (FurnaceRecipe) recipe;
            return new FurnaceRecipe(source.getKey(), convert.apply(source.getResult()), source.getInputChoice(), source.getExperience(), source.getCookingTime());

        } else if (recipe instanceof MerchantRecipe) {
            MerchantRecipe source = (MerchantRecipe) recipe;
            MerchantRecipe clone = new MerchantRecipe(convert.apply(source.getResult()), source.getUses(), source.getMaxUses(), source.hasExperienceReward(), source.getVillagerExperience(), source.getPriceMultiplier(), source.shouldIgnoreDiscounts());
            List<ItemStack> ingredientList = new ArrayList<>();
            source.getIngredients().forEach(ingredient -> ingredientList.add(convert.apply(ingredient)));
            clone.setIngredients(ingredientList);
            return clone;

        } else if (recipe instanceof ShapedRecipe) {
            ShapedRecipe source = (ShapedRecipe) recipe;
            ShapedRecipe clone = new ShapedRecipe(source.getKey(), convert.apply(source.getResult()));
            clone.shape(source.getShape());
            clone.setGroup(source.getGroup());
            source.getChoiceMap().forEach((character, recipeChoice) -> clone.setIngredient(character, filtrationRecipeChoice(recipeChoice, convert)));
            return clone;

        } else if (recipe instanceof ShapelessRecipe) {
            ShapelessRecipe source = (ShapelessRecipe) recipe;
            ShapelessRecipe clone = new ShapelessRecipe(source.getKey(), convert.apply(source.getResult()));
            clone.setGroup(source.getGroup());
            source.getChoiceList().forEach((recipeChoice) -> clone.addIngredient(filtrationRecipeChoice(recipeChoice, convert)));
            return clone;

        } else if (recipe instanceof SmithingRecipe) {
            SmithingRecipe source = (SmithingRecipe) recipe;
            try {
                // 適用於 paper
                return new SmithingRecipe(source.getKey(), convert.apply(source.getResult()), filtrationRecipeChoice(source.getBase(), convert), filtrationRecipeChoice(source.getAddition(), convert), source.willCopyNbt());
            } catch (NoSuchMethodError noSuchMethodError) {
                // 適用於 spigot (不推薦)
                return new SmithingRecipe(source.getKey(), convert.apply(source.getResult()), filtrationRecipeChoice(source.getBase(), convert), filtrationRecipeChoice(source.getAddition(), convert));
            }
        } else if (recipe instanceof SmokingRecipe) {
            SmokingRecipe source = (SmokingRecipe) recipe;
            return new SmokingRecipe(source.getKey(), convert.apply(source.getResult()), source.getInputChoice(), source.getExperience(), source.getCookingTime());

        } else if (recipe instanceof StonecuttingRecipe) {
            StonecuttingRecipe source = (StonecuttingRecipe) recipe;
            StonecuttingRecipe clone = new StonecuttingRecipe(source.getKey(), convert.apply(source.getResult()), filtrationRecipeChoice(source.getInputChoice(), convert));
            clone.setGroup(source.getGroup());
            return clone;

        }
        return recipe;
    }
    private RecipeChoice filtrationRecipeChoice(RecipeChoice recipeChoice, Function<ItemStack, ItemStack> convert) {
        if (recipeChoice instanceof RecipeChoice.ExactChoice) {
            RecipeChoice.ExactChoice exactChoice = (RecipeChoice.ExactChoice) recipeChoice;
            List<ItemStack> choices = new ArrayList<>();
            exactChoice.getChoices().forEach(item -> choices.add(convert.apply(item)));
            return new RecipeChoice.ExactChoice(choices);
        } else  {
            return recipeChoice;
        }
    }

    /**
     * 參考 XuanCatAPI.ExtendPlayer#replacePlayerCode
     */
    public void injectPlayer(Player player) {
        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        PlayerConnection connection = entityPlayer.b;
        entityPlayer.b = new Branch_18_ProxyPlayerConnection(connection, entityPlayer);
    }
}
