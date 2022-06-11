package xuan.cat.packetwhitelistnbt.code.branch.v19.nbt;

import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTType;

public interface Branch_19_NBTType {
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
