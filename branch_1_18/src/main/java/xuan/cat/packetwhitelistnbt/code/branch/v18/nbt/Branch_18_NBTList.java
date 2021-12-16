package xuan.cat.packetwhitelistnbt.code.branch.v18.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTList;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTType;

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
        add(size(), value);
        return true;
    }
    public void add(int index, Object value) {
        if (value instanceof Branch_18_NBTCompound) {
            tag.c(index, ((Branch_18_NBTCompound) value).getNMSTag());
        } else if (value instanceof Branch_18_NBTList) {
            tag.c(index, ((Branch_18_NBTList) value).getNMSTag());
        } else {
            tag.c(index, (NBTBase) value);
        }
    }

    public Object get(int index) {
        NBTBase base = tag.k(index);
        if (base instanceof NBTTagCompound) {
            return new Branch_18_NBTCompound((NBTTagCompound) base);
        } else if (base instanceof NBTTagList) {
            return new Branch_18_NBTList((NBTTagList) base);
        } else {
            return base;
        }
    }

    public int size() {
        return tag.size();
    }
}
