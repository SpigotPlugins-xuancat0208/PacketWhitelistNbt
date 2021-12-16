package xuan.cat.packetwhitelistnbt.code.branch.v17.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTCompound;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTList;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTType;

import java.util.Set;

public final class Branch_17_NBTCompound implements BranchNBTCompound {
    protected NBTTagCompound tag;

    public Branch_17_NBTCompound() {
        this.tag = new NBTTagCompound();
    }

    public Branch_17_NBTCompound(NBTTagCompound tag) {
        this.tag = tag;
    }


    public NBTTagCompound getNMSTag() {
        return this.tag;
    }


    public BranchNBTType getType(String key) {
        return Branch_17_NBTType.fromNMS(tag.d(key));
    }
    
    public int size() {
        return tag.e();
    }

    public Object get(String key) {
        NBTBase base = tag.get(key);
        if (base instanceof NBTTagCompound) {
            return new Branch_17_NBTCompound((NBTTagCompound) base);
        } else if (base instanceof NBTTagList) {
            return new Branch_17_NBTList((NBTTagList) base);
        } else {
            return base;
        }
    }

    public Set<String> getKeys() {
        return tag.getKeys();
    }

    public byte getByte(String key) {
        return tag.getByte(key);
    }

    public BranchNBTCompound getCompound(String key) {
        NBTTagCompound nbtTagCompound = tag.getCompound(key);
        if (nbtTagCompound.e() == 0) {
            nbtTagCompound = new NBTTagCompound();
            tag.set(key, nbtTagCompound);
        }

        return new Branch_17_NBTCompound(nbtTagCompound);
    }
    
    public int getInt(String key) {
        return tag.getInt(key);
    }
    
    public String getString(String key) {
        return tag.getString(key);
    }

    public BranchNBTList getList(String key) {
        NBTTagList nbtTagList = (NBTTagList) tag.get(key);
        if (nbtTagList == null || nbtTagList.size() == 0) {
            nbtTagList = new NBTTagList();
            tag.set(key, nbtTagList);
        }

        return new Branch_17_NBTList(nbtTagList);
    }

    public void set(String key, Object value) {
        if (value instanceof Branch_17_NBTCompound) {
            tag.set(key, ((Branch_17_NBTCompound) value).getNMSTag());
        } else if (value instanceof Branch_17_NBTList) {
            tag.set(key, ((Branch_17_NBTList) value).getNMSTag());
        } else {
            tag.set(key, (NBTBase) value);
        }
    }

    public void setByte(String key, byte value) {
        tag.setByte(key, value);
    }

    public void setCompound(String key, BranchNBTCompound value) {
        tag.set(key, ((Branch_17_NBTCompound) value).getNMSTag());
    }

    public void setInt(String key, int value) {
        tag.setInt(key, value);
    }

    public void setList(String key, BranchNBTList value) {
        tag.set(key, ((Branch_17_NBTList) value).getNMSTag());
    }

    public void setString(String key, String value) {
        tag.setString(key, value);
    }
}
