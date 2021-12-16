package xuan.cat.packetwhitelistnbt.api.branch.nbt;

import java.util.Set;

public interface BranchNBTCompound extends BranchNBTAbstract {
    BranchNBTType getType(String key);

    Object get(String key);

    byte getByte(String key);

    BranchNBTCompound getCompound(String key);

    int getInt(String key);

    Set<String> getKeys();

    BranchNBTList getList(String key);

    String getString(String key);

    void set(String key, Object value);

    void setByte(String key, byte value);

    void setCompound(String key, BranchNBTCompound value);

    void setInt(String key, int value);

    void setList(String key, BranchNBTList value);

    void setString(String key, String value);
}
