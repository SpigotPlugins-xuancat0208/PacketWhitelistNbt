package xuan.cat.packetwhitelistnbt.code.branch.v18.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTAbstract;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTList;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTType;

import java.util.AbstractList;

public final class Branch_18_NBTList extends BranchNBTList {
    protected NBTTagList tag;

    public Branch_18_NBTList() {
        this.tag = new NBTTagList();
    }

    public Branch_18_NBTList(NBTTagList tag) {
        this.tag = tag;
    }


    public NBTTagList getNMSTag() {
        return this.tag;
    }


    public BranchNBTType getOwnType() {
        return Branch_18_NBTType.fromNMS(tag.e());
    }

    public boolean add(Object value) {
        return tag.add((NBTBase) value);
    }

    public Object get(int index) {
        return tag.get(index);
    }

    public int size() {
        return tag.size();
    }
}
