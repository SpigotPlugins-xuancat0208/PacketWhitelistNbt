package xuan.cat.packetwhitelistnbt.code.branch.v15.nbt;

import net.minecraft.server.v1_15_R1.NBTBase;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.NBTTagList;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTList;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTType;

public final class Branch_15_NBTList extends BranchNBTList {
    protected NBTTagList tag;

    public Branch_15_NBTList() {
        this.tag = new NBTTagList();
    }

    public Branch_15_NBTList(NBTTagList tag) {
        this.tag = tag;
    }


    public NBTTagList getNMSTag() {
        return this.tag;
    }


    public BranchNBTType getOwnType() {
        return Branch_15_NBTType.fromNMS(tag.a_());
    }

    public boolean add(Object value) {
        add(size(), value);
        return true;
    }
    public void add(int index, Object value) {
        if (value instanceof Branch_15_NBTCompound) {
            tag.add(index, ((Branch_15_NBTCompound) value).getNMSTag());
        } else if (value instanceof Branch_15_NBTList) {
            tag.add(index, ((Branch_15_NBTList) value).getNMSTag());
        } else {
            tag.add(index, (NBTBase) value);
        }
    }

    public Object get(int index) {
        NBTBase base = tag.get(index);
        if (base instanceof NBTTagCompound) {
            return new Branch_15_NBTCompound((NBTTagCompound) base);
        } else if (base instanceof NBTTagList) {
            return new Branch_15_NBTList((NBTTagList) base);
        } else {
            return base;
        }
    }

    public int size() {
        return tag.size();
    }
}
