package xuan.cat.packetwhitelistnbt.code.branch.v18;

import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_18_R1.util.CraftMagicNumbers;
import xuan.cat.packetwhitelistnbt.api.branch.BranchNBT;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTCompound;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTList;
import xuan.cat.packetwhitelistnbt.code.branch.v18.nbt.Branch_18_NBTCompound;
import xuan.cat.packetwhitelistnbt.code.branch.v18.nbt.Branch_18_NBTList;

public final class Branch_18_NBT implements BranchNBT {
    @Override
    public BranchNBTCompound createCompound() {
        return new Branch_18_NBTCompound();
    }

    @Override
    public BranchNBTList createList() {
        return new Branch_18_NBTList();
    }

    @Override
    public BranchNBTCompound fromItem(org.bukkit.inventory.ItemStack item) {
        if (!(item instanceof CraftItemStack))
            item = CraftItemStack.asCraftCopy(item);
        Branch_18_NBTCompound nbt = new Branch_18_NBTCompound();
        ((CraftItemStack) item).handle.b(nbt.getNMSTag());
        nbt.setInt("version", CraftMagicNumbers.INSTANCE.getDataVersion());
        return nbt;
    }

    @Override
    public org.bukkit.inventory.ItemStack toItem(BranchNBTCompound nbt) {
        ItemStack item = ItemStack.a(((Branch_18_NBTCompound) nbt).getNMSTag());
        item.convertStack(nbt.getInt("version"));
        return CraftItemStack.asBukkitCopy(item);
    }
}
