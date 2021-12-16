package xuan.cat.packetwhitelistnbt.code.branch.v14.nbt;

import net.minecraft.server.v1_14_R1.NBTBase;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagList;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTList;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTType;

public final class Branch_14_NBTList extends BranchNBTList {
    protected NBTTagList tag;

    public Branch_14_NBTList() {
        this.tag = new NBTTagList();
    }

    public Branch_14_NBTList(NBTTagList tag) {
        this.tag = tag;
    }


    public NBTTagList getNMSTag() {
        return this.tag;
    }


    public BranchNBTType getOwnType() {
        return Branch_14_NBTType.fromNMS(tag.a_());
    }

    public boolean add(Object value) {
        add(size(), value);
        return true;
    }
    public void add(int index, Object value) {
        if (value instanceof Branch_14_NBTCompound) {
            tag.add(index, ((Branch_14_NBTCompound) value).getNMSTag());
        } else if (value instanceof Branch_14_NBTList) {
            tag.add(index, ((Branch_14_NBTList) value).getNMSTag());
        } else {
            tag.add(index, (NBTBase) value);
        }
    }

    public Object get(int index) {
        NBTBase base = tag.get(index);
        if (base instanceof NBTTagCompound) {
            return new Branch_14_NBTCompound((NBTTagCompound) base);
        } else if (base instanceof NBTTagList) {
            return new Branch_14_NBTList((NBTTagList) base);
        } else {
            return base;
        }
    }

    public int size() {
        return tag.size();
    }
}
