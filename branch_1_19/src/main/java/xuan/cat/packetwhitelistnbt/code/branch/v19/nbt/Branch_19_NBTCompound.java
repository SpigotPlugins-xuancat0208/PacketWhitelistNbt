package xuan.cat.packetwhitelistnbt.code.branch.v19.nbt;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTCompound;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTList;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTType;

import java.util.Set;

public final class Branch_19_NBTCompound implements BranchNBTCompound {
    protected CompoundTag tag;

    public Branch_19_NBTCompound() {
        this.tag = new CompoundTag();
    }

    public Branch_19_NBTCompound(CompoundTag tag) {
        this.tag = tag;
    }


    public CompoundTag getNMSTag() {
        return this.tag;
    }

    public BranchNBTType getType(String key) {
        return Branch_19_NBTType.fromNMS(tag.getTagType(key));
    }
    
    public int size() {
        return tag.size();
    }

    public Object get(String key) {
        Tag base = tag.get(key);
        if (base instanceof CompoundTag) {
            return new Branch_19_NBTCompound((CompoundTag) base);
        } else if (base instanceof ListTag) {
            return new Branch_19_NBTList((ListTag) base);
        } else {
            return base;
        }
    }

    public Set<String> getKeys() {
        return tag.getAllKeys();
    }

    public byte getByte(String key) {
        return tag.getByte(key);
    }

    public BranchNBTCompound getCompound(String key) {
        CompoundTag nbtTagCompound = tag.getCompound(key);
        if (nbtTagCompound.size() == 0) {
            nbtTagCompound = new CompoundTag();
            tag.put(key, nbtTagCompound);
        }

        return new Branch_19_NBTCompound(nbtTagCompound);
    }
    
    public int getInt(String key) {
        return tag.getInt(key);
    }
    
    public String getString(String key) {
        return tag.getString(key);
    }

    public BranchNBTList getList(String key) {
        ListTag ListTag = (ListTag) tag.get(key);
        if (ListTag == null || ListTag.size() == 0) {
            ListTag = new ListTag();
            tag.put(key, ListTag);
        }

        return new Branch_19_NBTList(ListTag);
    }

    public void set(String key, Object value) {
        if (value instanceof Branch_19_NBTCompound) {
            tag.put(key, ((Branch_19_NBTCompound) value).getNMSTag());
        } else if (value instanceof Branch_19_NBTList) {
            tag.put(key, ((Branch_19_NBTList) value).getNMSTag());
        } else {
            tag.put(key, (Tag) value);
        }
    }

    public void setByte(String key, byte value) {
        tag.putByte(key, value);
    }

    public void setCompound(String key, BranchNBTCompound value) {
        tag.put(key, ((Branch_19_NBTCompound) value).getNMSTag());
    }

    public void setInt(String key, int value) {
        tag.putInt(key, value);
    }

    public void setList(String key, BranchNBTList value) {
        tag.put(key, ((Branch_19_NBTList) value).getNMSTag());
    }

    public void setString(String key, String value) {
        tag.putString(key, value);
    }
}
