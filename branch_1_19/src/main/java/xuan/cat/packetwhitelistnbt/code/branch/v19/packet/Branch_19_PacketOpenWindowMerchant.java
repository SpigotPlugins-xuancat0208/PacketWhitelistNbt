package xuan.cat.packetwhitelistnbt.code.branch.v19.packet;

import net.minecraft.network.protocol.game.ClientboundMerchantOffersPacket;
import net.minecraft.world.item.trading.MerchantOffers;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftMerchantRecipe;
import org.bukkit.inventory.MerchantRecipe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class Branch_19_PacketOpenWindowMerchant {
    private final ClientboundMerchantOffersPacket packet;

    public Branch_19_PacketOpenWindowMerchant(ClientboundMerchantOffersPacket packet) {
        this.packet = packet;
    }

    private static Field field_offers;
    static {
        try {
            field_offers = ClientboundMerchantOffersPacket.class.getDeclaredField("b"); // TODO 映射 offers
            field_offers.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public List<MerchantRecipe> getRecipeList() {
        List<MerchantRecipe> recipeList = new ArrayList<>();
        packet.getOffers().forEach(recipe -> recipeList.add(recipe.asBukkit()));
        return recipeList;
    }
    public void setRecipeList(List<MerchantRecipe> recipeList) {
        MerchantOffers list = new MerchantOffers();
        recipeList.forEach(recipe -> list.add(CraftMerchantRecipe.fromBukkit(recipe).toMinecraft()));
        try {
            field_offers.set(packet, list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
