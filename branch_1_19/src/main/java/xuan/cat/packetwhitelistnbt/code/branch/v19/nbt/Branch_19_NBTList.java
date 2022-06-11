package xuan.cat.packetwhitelistnbt.code.branch.v19.nbt;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTList;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTType;

public final class Branch_19_NBTList extends BranchNBTList {
    protected ListTag tag;

    public Branch_19_NBTList() {
        this.tag = new ListTag();
    }

    public Branch_19_NBTList(ListTag tag) {
        this.tag = tag;
    }


    public ListTag getNMSTag() {
        return this.tag;
    }


    public BranchNBTType getOwnType() {
        return Branch_19_NBTType.fromNMS(tag.getElementType());
    }

    public boolean add(Object value) {
        add(size(), value);
        return true;
    }
    public void add(int index, Object value) {
        if (value instanceof Branch_19_NBTCompound) {
            tag.add(index, ((Branch_19_NBTCompound) value).getNMSTag());
        } else if (value instanceof Branch_19_NBTList) {
            tag.add(index, ((Branch_19_NBTList) value).getNMSTag());
        } else {
            tag.add(index, (Tag) value);
        }
    }

    public Object get(int index) {
        Tag base = tag.get(index);
        if (base instanceof CompoundTag) {
            return new Branch_19_NBTCompound((CompoundTag) base);
        } else if (base instanceof ListTag) {
            return new Branch_19_NBTList((ListTag) base);
        } else {
            return base;
        }
    }

    public int size() {
        return tag.size();
    }
}
