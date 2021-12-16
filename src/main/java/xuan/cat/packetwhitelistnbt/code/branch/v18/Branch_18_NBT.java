package xuan.cat.packetwhitelistnbt.code.branch.v18;

import org.bukkit.inventory.ItemStack;
import xuan.cat.packetwhitelistnbt.api.branch.BranchNBT;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTCompound;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTList;

public final class Branch_18_NBT implements BranchNBT {
    @Override
    public BranchNBTCompound createCompound() {
        throw new UnsupportedOperationException();
    }

    @Override
    public BranchNBTList createList() {
        throw new UnsupportedOperationException();
    }

    @Override
    public BranchNBTCompound fromItem(ItemStack item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ItemStack toItem(BranchNBTCompound nbt) {
        throw new UnsupportedOperationException();
    }
}
