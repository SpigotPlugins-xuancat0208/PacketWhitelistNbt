package xuan.cat.packetwhitelistnbt.api.branch.nbt;

import java.util.AbstractList;

public abstract class BranchNBTList extends AbstractList<Object> implements BranchNBTAbstract {
    public abstract BranchNBTType getOwnType();

    public abstract boolean add(Object value);
}
