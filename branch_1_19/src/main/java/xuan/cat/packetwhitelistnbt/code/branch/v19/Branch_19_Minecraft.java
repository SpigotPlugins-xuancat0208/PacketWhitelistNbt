package xuan.cat.packetwhitelistnbt.code.branch.v19;

import io.netty.channel.*;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import xuan.cat.packetwhitelistnbt.api.branch.BranchMinecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class Branch_19_Minecraft implements BranchMinecraft {
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
            MerchantRecipe clone = new MerchantRecipe(convert.apply(source.getResult()), source.getUses(), source.getMaxUses(), source.hasExperienceReward(), source.getVillagerExperience(), source.getPriceMultiplier(), source.getDemand(), source.getSpecialPrice(), source.shouldIgnoreDiscounts());
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

    public void injectPlayer(Player player) {
        ServerPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        ServerGamePacketListenerImpl connection = entityPlayer.connection;
        Channel channel = connection.connection.channel;
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addAfter("packet_handler", "packet_whitelist_nbt_write", new ChannelDuplexHandler() {
            @Override
            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                if (msg instanceof Packet) {
                    if (!Branch_19_ProxyPlayerConnection.write(player, (Packet<?>) msg))
                        return;
                }
                super.write(ctx, msg, promise);
            }
        });
        pipeline.addAfter("encoder", "packet_whitelist_nbt_read", new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                if (msg instanceof Packet) {
                    if (!Branch_19_ProxyPlayerConnection.read(player, (Packet<?>) msg))
                        return;
                }
                super.channelRead(ctx, msg);
            }
        });
    }
}
