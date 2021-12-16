package xuan.cat.packetwhitelistnbt.code.branch.v18.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTCompound;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTList;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTType;

import java.util.Set;

public final class Branch_18_NBTCompound implements BranchNBTCompound {
    protected NBTTagCompound tag;

    public Branch_18_NBTCompound() {
        this.tag = new NBTTagCompound();
    }

    public Branch_18_NBTCompound(NBTTagCompound tag) {
        this.tag = tag;
    }


    public NBTTagCompound getNMSTag() {
        return this.tag;
    }


    public BranchNBTType getType(String key) {
        return Branch_18_NBTType.fromNMS(tag.d(key));
    }
    
    public int size() {
        return tag.e();
    }

    public Object get(String key) {
        return tag.c(key);
    }

    public Set<String> getKeys() {
        return tag.d();
    }

    public byte getByte(String key) {
        return tag.f(key);
    }

    public BranchNBTCompound getCompound(String key) {
        NBTTagCompound nbtTagCompound = tag.p(key);
        if (nbtTagCompound.e() == 0) {
            nbtTagCompound = new NBTTagCompound();
            tag.a(key, nbtTagCompound);
        }

        return new Branch_18_NBTCompound(nbtTagCompound);
    }
    
    public int getInt(String key) {
        return tag.h(key);
    }
    
    public String getString(String key) {
        return tag.l(key);
    }

    public BranchNBTList getList(String key) {
        NBTTagList nbtTagList = (NBTTagList) tag.c(key);
        if (nbtTagList == null || nbtTagList.size() == 0) {
            nbtTagList = new NBTTagList();
            tag.a(key, nbtTagList);
        }

        return new Branch_18_NBTList(nbtTagList);
    }

    public void set(String key, Object value) {
        tag.a(key, (NBTBase) value);
    }

    public void setByte(String key, byte value) {
        tag.a(key, value);
    }

    public void setCompound(String key, BranchNBTCompound value) {
        tag.a(key, ((Branch_18_NBTCompound) value).getNMSTag());
    }

    public void setInt(String key, int value) {
        tag.a(key, value);
    }

    public void setList(String key, BranchNBTList value) {
        tag.a(key, ((Branch_18_NBTList) value).getNMSTag());
    }

    public void setString(String key, String value) {
        tag.a(key, value);
    }
}
