package xuan.cat.packetwhitelistnbt.api.branch;

import org.bukkit.inventory.ItemStack;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTCompound;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTList;

public interface BranchNBT {
    BranchNBTCompound createCompound();

    BranchNBTList createList();

    BranchNBTCompound fromItem(ItemStack item);

    ItemStack toItem(BranchNBTCompound nbt);
}
