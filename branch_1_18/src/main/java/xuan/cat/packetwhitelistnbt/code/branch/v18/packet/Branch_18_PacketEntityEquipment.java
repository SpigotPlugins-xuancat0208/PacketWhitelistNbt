package xuan.cat.packetwhitelistnbt.code.branch.v18.packet;

import com.mojang.datafixers.util.Pair;
import net.minecraft.network.protocol.game.PacketPlayOutEntityEquipment;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_18_R1.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.inventory.EquipmentSlot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Branch_18_PacketEntityEquipment {
    private final PacketPlayOutEntityEquipment packet;

    public Branch_18_PacketEntityEquipment(PacketPlayOutEntityEquipment packet) {
        this.packet = packet;
    }

    private static Field field_slots;
    static {
        try {
            field_slots = PacketPlayOutEntityEquipment.class.getDeclaredField("c"); // TODO 映射 slots
            field_slots.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public Map<EquipmentSlot, org.bukkit.inventory.ItemStack> getEquipmentItemMap() {
        Map<EquipmentSlot, org.bukkit.inventory.ItemStack> map = new HashMap<>();
        packet.c().forEach(pair -> map.put(CraftEquipmentSlot.getSlot(pair.getFirst()), CraftItemStack.asBukkitCopy(pair.getSecond())));
        return map;
    }
    public void setEquipmentItemMap(Map<EquipmentSlot, org.bukkit.inventory.ItemStack> equipmentItemMap) {
        List<Pair<EnumItemSlot, ItemStack>> list = new ArrayList<>(equipmentItemMap.size());
        equipmentItemMap.forEach((slot, item) -> list.add(Pair.of(CraftEquipmentSlot.getNMS(slot), CraftItemStack.asNMSCopy(item))));
        try {
            field_slots.set(packet,list);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
