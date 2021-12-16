package xuan.cat.packetwhitelistnbt.code.branch.v18;

import com.comphenix.protocol.events.PacketContainer;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEquipment;
import net.minecraft.network.protocol.game.PacketPlayOutRecipeUpdate;
import net.minecraft.network.protocol.game.PacketPlayOutSetSlot;
import net.minecraft.network.protocol.game.PacketPlayOutWindowItems;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import xuan.cat.packetwhitelistnbt.api.branch.BranchPacket;
import xuan.cat.packetwhitelistnbt.code.branch.v18.packet.Branch_18_PacketEntityEquipment;
import xuan.cat.packetwhitelistnbt.code.branch.v18.packet.Branch_18_PacketRecipeUpdate;
import xuan.cat.packetwhitelistnbt.code.branch.v18.packet.Branch_18_PacketSetSlot;
import xuan.cat.packetwhitelistnbt.code.branch.v18.packet.Branch_18_PacketWindowItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Branch_18_Packet implements BranchPacket {
    @Override
    public void convertSetSlot(PacketContainer container, Function<ItemStack, ItemStack> convert) {
        Branch_18_PacketSetSlot packet = new Branch_18_PacketSetSlot((PacketPlayOutSetSlot) container.getHandle());
        packet.setItem(convert.apply(packet.getItem()));
    }

    @Override
    public void convertWindowItems(PacketContainer container, Function<ItemStack, ItemStack> convert) {
        Branch_18_PacketWindowItems packet = new Branch_18_PacketWindowItems((PacketPlayOutWindowItems) container.getHandle());
        List<ItemStack> list = new ArrayList<>();
        packet.getItemList().forEach((item) -> list.add(convert.apply(item)));
        packet.setItemList(list);
    }

    @Override
    public void convertEntityEquipment(PacketContainer container, Function<ItemStack, ItemStack> convert) {
        Branch_18_PacketEntityEquipment packet = new Branch_18_PacketEntityEquipment((PacketPlayOutEntityEquipment) container.getHandle());
        Map<EquipmentSlot, ItemStack> map = new HashMap<>();
        packet.getEquipmentItemMap().forEach((equipmentSlot, item) -> map.put(equipmentSlot, convert.apply(item)));
        packet.setEquipmentItemMap(map);
    }

    @Override
    public void convertRecipeUpdate(PacketContainer container, Function<Recipe, Recipe> convert) {
        Branch_18_PacketRecipeUpdate packet = new Branch_18_PacketRecipeUpdate((PacketPlayOutRecipeUpdate) container.getHandle());
        List<Recipe> list = new ArrayList<>();
        packet.getRecipeList().forEach((item) -> list.add(convert.apply(item)));
        packet.setRecipeList(list);
    }
}
