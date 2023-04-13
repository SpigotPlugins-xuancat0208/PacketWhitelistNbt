package xuan.cat.packetwhitelistnbt.code.branch.v15.packet;

import net.minecraft.server.v1_15_R1.MerchantRecipeList;
import net.minecraft.server.v1_15_R1.PacketPlayOutOpenWindowMerchant;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftMerchantRecipe;
import org.bukkit.inventory.MerchantRecipe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class Branch_15_PacketOpenWindowMerchant {
    private final PacketPlayOutOpenWindowMerchant packet;

    public Branch_15_PacketOpenWindowMerchant(PacketPlayOutOpenWindowMerchant packet) {
        this.packet = packet;
    }

    private static Field field_offers;
    static {
        try {
            field_offers = PacketPlayOutOpenWindowMerchant.class.getDeclaredField("b"); // TODO 映射 offers
            field_offers.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public List<MerchantRecipe> getRecipeList() {
        List<MerchantRecipe> recipeList = new ArrayList<>();
        try {
            ((MerchantRecipeList) field_offers.get(packet)).forEach(recipe -> recipeList.add(recipe.asBukkit()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return recipeList;
    }
    public void setRecipeList(List<MerchantRecipe> recipeList) {
        MerchantRecipeList list = new MerchantRecipeList();
        recipeList.forEach(recipe -> list.add(CraftMerchantRecipe.fromBukkit(recipe).toMinecraft()));
        try {
            field_offers.set(packet, list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
