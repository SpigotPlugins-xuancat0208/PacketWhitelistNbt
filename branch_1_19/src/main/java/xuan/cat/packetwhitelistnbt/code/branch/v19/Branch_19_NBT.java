package xuan.cat.packetwhitelistnbt.code.branch.v19;

import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import xuan.cat.packetwhitelistnbt.api.branch.BranchNBT;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTCompound;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTList;
import xuan.cat.packetwhitelistnbt.code.branch.v19.nbt.Branch_19_NBTCompound;
import xuan.cat.packetwhitelistnbt.code.branch.v19.nbt.Branch_19_NBTList;

import java.lang.reflect.Field;

public final class Branch_19_NBT implements BranchNBT {
    @Override
    public BranchNBTCompound createCompound() {
        return new Branch_19_NBTCompound();
    }

    @Override
    public BranchNBTList createList() {
        return new Branch_19_NBTList();
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
        Branch_19_NBTCompound nbt = new Branch_19_NBTCompound();
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
        ItemStack item = ItemStack.of(((Branch_19_NBTCompound) nbt).getNMSTag());
        item.convertStack(nbt.getInt("version"));
        return CraftItemStack.asBukkitCopy(item);
    }
}
