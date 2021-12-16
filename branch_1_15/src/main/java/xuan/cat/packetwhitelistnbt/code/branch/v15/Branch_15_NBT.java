package xuan.cat.packetwhitelistnbt.code.branch.v15;

import net.minecraft.server.v1_15_R1.ItemStack;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_15_R1.util.CraftMagicNumbers;
import xuan.cat.packetwhitelistnbt.api.branch.BranchNBT;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTCompound;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTList;
import xuan.cat.packetwhitelistnbt.code.branch.v15.nbt.Branch_15_NBTCompound;
import xuan.cat.packetwhitelistnbt.code.branch.v15.nbt.Branch_15_NBTList;

import java.lang.reflect.Field;

public final class Branch_15_NBT implements BranchNBT {
    @Override
    public BranchNBTCompound createCompound() {
        return new Branch_15_NBTCompound();
    }

    @Override
    public BranchNBTList createList() {
        return new Branch_15_NBTList();
    }


    private static Field field_CraftItemStack_handle;
    static {
        try {
            field_CraftItemStack_handle = CraftItemStack.class.getDeclaredField("handle");
            field_CraftItemStack_handle.setAccessible(true);
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public BranchNBTCompound fromItem(org.bukkit.inventory.ItemStack item) {
        if (!(item instanceof CraftItemStack))
            item = CraftItemStack.asCraftCopy(item);
        Branch_15_NBTCompound nbt = new Branch_15_NBTCompound();
        try {
            ((ItemStack) field_CraftItemStack_handle.get(item)).save(nbt.getNMSTag());
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        nbt.setInt("version", CraftMagicNumbers.INSTANCE.getDataVersion());
        return nbt;
    }

    @Override
    public org.bukkit.inventory.ItemStack toItem(BranchNBTCompound nbt) {
        ItemStack item = ItemStack.a(((Branch_15_NBTCompound) nbt).getNMSTag());
        item.convertStack(nbt.getInt("version"));
        return CraftItemStack.asBukkitCopy(item);
    }
}
