package xuan.cat.packetwhitelistnbt.code.branch.v16.nbt;

import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTType;

public interface Branch_16_NBTType {
    static BranchNBTType fromNMS(int type) {
        switch(type) {
            case 9:
                return BranchNBTType.NBT_LIST;
            case 10:
                return BranchNBTType.NBT_COMPOUND;
            default:
                return BranchNBTType.OTHER;
        }
    }
}
