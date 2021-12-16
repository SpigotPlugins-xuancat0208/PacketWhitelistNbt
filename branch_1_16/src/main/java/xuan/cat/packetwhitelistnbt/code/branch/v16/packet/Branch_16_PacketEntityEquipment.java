package xuan.cat.packetwhitelistnbt.code.branch.v16.packet;

import com.mojang.datafixers.util.Pair;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.ItemStack;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEquipment;
import org.bukkit.craftbukkit.v1_16_R3.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.EquipmentSlot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Branch_16_PacketEntityEquipment {
    private final PacketPlayOutEntityEquipment packet;

    public Branch_16_PacketEntityEquipment(PacketPlayOutEntityEquipment packet) {
        this.packet = packet;
    }

    private static Field field_slots;
    static {
        try {
            field_slots = PacketPlayOutEntityEquipment.class.getDeclaredField("b"); // TODO 映射 slots
            field_slots.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public Map<EquipmentSlot, org.bukkit.inventory.ItemStack> getEquipmentItemMap() {
        Map<EquipmentSlot, org.bukkit.inventory.ItemStack> map = new HashMap<>();
        try {
            ((List<Pair<EnumItemSlot, ItemStack>>) field_slots.get(packet)).forEach(pair -> map.put(CraftEquipmentSlot.getSlot(pair.getFirst()), CraftItemStack.asBukkitCopy(pair.getSecond())));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
