package xuan.cat.packetwhitelistnbt.code.data;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import xuan.cat.packetwhitelistnbt.api.branch.BranchNBT;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTCompound;
import xuan.cat.packetwhitelistnbt.api.branch.nbt.BranchNBTList;

import java.util.*;

/**
 * 配置文件
 */
public final class ConfigData {
    private FileConfiguration fileConfiguration;
    private final JavaPlugin plugin;
    private final BranchNBT branchNBT;
    private TagComparator itemAllowedTagComparator;
    private TagComparator itemDisallowTagComparator;
    public String permissionsNode_ignoreItemTagLimit;
    public long permissionsPeriodicMillisecondCheck;


    public ConfigData(JavaPlugin plugin, FileConfiguration fileConfiguration, BranchNBT branchNBT) {
        this.plugin = plugin;
        this.fileConfiguration = fileConfiguration;
        this.branchNBT = branchNBT;
        load();
    }


    public void reload() {
        plugin.reloadConfig();
        fileConfiguration = plugin.getConfig();
        load();
    }


    private void load() {
        ConfigurationSection itemConfiguration = fileConfiguration.getConfigurationSection("item");
        if (itemConfiguration == null)
            throw new NullPointerException("config.yml->item");
        List<String> itemAllowedTagList = itemConfiguration.getStringList("allowed-tag");
        List<String> itemDisallowTagList = itemConfiguration.getStringList("disallow-tag");
        TagComparator itemAllowedTagComparator = applyTagComparator(itemAllowedTagList);
        TagComparator itemDisallowTagComparator = applyTagComparator(itemDisallowTagList);

        ConfigurationSection permissionsConfiguration = fileConfiguration.getConfigurationSection("permissions");
        if (permissionsConfiguration == null)
            throw new NullPointerException("config.yml->permissions");
        ConfigurationSection permissionsNodeConfiguration = permissionsConfiguration.getConfigurationSection("node");
        if (permissionsNodeConfiguration == null)
            throw new NullPointerException("config.yml->permissions->node");
        String permissionsNode_ignoreItemTagLimit = permissionsNodeConfiguration.getString("ignore-item-tag-limit", "packetwhitelist.ignore_item_allowed_tag");
        long permissionsPeriodicMillisecondCheck = permissionsConfiguration.getLong("periodic-millisecond-check", 60000L);

        // 正式替換
        this.itemAllowedTagComparator = itemAllowedTagComparator;
        this.itemDisallowTagComparator = itemDisallowTagComparator;
        this.permissionsNode_ignoreItemTagLimit = permissionsNode_ignoreItemTagLimit;
        this.permissionsPeriodicMillisecondCheck = permissionsPeriodicMillisecondCheck;
    }


    public static TagComparator applyTagComparator(List<String> tagList) {
        TagComparator comparatorRoot = new TagComparator();
        for (String itemAllowedTag : tagList) {
            String[] tagSplit = itemAllowedTag.split("\\.");
            TagComparator comparator = comparatorRoot;
            for (int layer = 0, length = tagSplit.length - 1 ; layer <= length ; layer++) {
                String tag = tagSplit[layer];
                if (layer == length) {
                    // 最後一個
                    if (tag.equals("*")) {
                        comparator.setHitAll(true);
                    } else {
                        comparator.setHit(tag);
                    }
                } else {
                    // 中圖路徑
                    comparator.setHit(tag);
                    comparator = comparator.getLayerNotNull(tag);
                }
            }
        }
        return comparatorRoot;
    }


    /** 標籤比較器 */
    private static class TagComparator {
        private final Set<String> hitSet = new HashSet<>();
        private final Map<String, TagComparator> layerMap = new HashMap<>();
        private boolean hitAll = false;

        public TagComparator() {
        }

        public boolean isHit(String name) {
            return hitAll || hitSet.contains(name);
        }
        public void setHit(String name) {
            hitSet.add(name);
        }

        public void setHitAll(boolean allowedAll) {
            this.hitAll = allowedAll;
        }
        public boolean isHitAll() {
            return hitAll;
        }

        public TagComparator getLayer(String name) {
            return layerMap.get(name);
        }
        public TagComparator getLayerNotNull(String name) {
            TagComparator child = layerMap.computeIfAbsent(name, key -> new TagComparator());
            child.hitAll |= hitAll;
            return child;
        }

        @Override
        public String toString() {
            return "{TagComparator " + "hitSet=" + hitSet + ", layerMap=" + layerMap + ", hitAll=" + hitAll + '}';
        }
    }


    public ItemStack filtrationItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR)
            return null;
        BranchNBTCompound sourceItem = branchNBT.fromItem(item);
        BranchNBTCompound closeItem = branchNBT.createCompound();
        closeItem.setByte("Count", sourceItem.getByte("Count"));
        closeItem.setString("id", sourceItem.getString("id"));
        closeItem.setInt("version", sourceItem.getInt("version"));
        BranchNBTCompound sourceTag = sourceItem.getCompound("tag");
        BranchNBTCompound closeTag = filtrationTagComparatorMap(branchNBT, sourceTag, itemAllowedTagComparator, itemDisallowTagComparator);
        if (closeTag != null) {
            closeItem.setCompound("tag", closeTag);
        }
        return branchNBT.toItem(closeItem);
    }
    private static BranchNBTCompound filtrationTagComparatorMap(BranchNBT branchNBT, BranchNBTCompound sourceMap, TagComparator allowedComparator, TagComparator disallowComparator) {
        return filtrationTagComparatorMap(branchNBT, sourceMap, allowedComparator, disallowComparator, allowedComparator.hitAll);
    }
    private static BranchNBTCompound filtrationTagComparatorMap(BranchNBT branchNBT,BranchNBTCompound sourceMap, TagComparator allowedComparator, TagComparator disallowComparator, boolean allowedAll) {
        if (!allowedAll && allowedComparator == null) {
            return null;
        }
        if (allowedComparator != null) {
            allowedAll |= allowedComparator.hitAll;
        }
        if (disallowComparator != null && disallowComparator.hitAll) {
            return null;
        }
        BranchNBTCompound closeMap = branchNBT.createCompound();
        for (String tag : sourceMap.getKeys()) {
            if ((disallowComparator == null || !disallowComparator.isHit(tag)) && (allowedAll || allowedComparator.isHit(tag))) {
                switch (sourceMap.getType(tag)) {
                    case NBT_COMPOUND:
                        BranchNBTCompound layerMap = filtrationTagComparatorMap(branchNBT, sourceMap.getCompound(tag), allowedComparator != null ? allowedComparator.getLayer(tag) : null, disallowComparator != null ? disallowComparator.getLayer(tag) : null, allowedAll);
                        if (layerMap != null) {
                            closeMap.setCompound(tag, layerMap);
                        }
                        break;
                    case NBT_LIST:
                        BranchNBTList cloneList = filtrationTagComparatorList(branchNBT, sourceMap.getList(tag), allowedComparator != null ? allowedComparator.getLayer(tag) : null, disallowComparator != null ? disallowComparator.getLayer(tag) : null, allowedAll);
                        if (cloneList != null) {
                            closeMap.setList(tag, cloneList);
                        }
                        break;
                    default:
                        closeMap.set(tag, sourceMap.get(tag));
                        break;
                }
            }
        }
        return closeMap.size() == 0 ? null : closeMap;
    }
    private static BranchNBTList filtrationTagComparatorList(BranchNBT branchNBT,BranchNBTList sourceList, TagComparator allowedComparator, TagComparator disallowComparator, boolean allowedAll) {
        if (!allowedAll && allowedComparator == null) {
            return null;
        }
        if (allowedComparator != null) {
            allowedAll |= allowedComparator.hitAll;
        }
        if (disallowComparator != null && disallowComparator.hitAll) {
            return null;
        }
        BranchNBTList closeList = branchNBT.createList();
        if ((disallowComparator == null || !disallowComparator.isHit("[]")) && (allowedAll || allowedComparator.isHit("[]"))) {
            switch (sourceList.getOwnType()) {
                case NBT_COMPOUND:
                    for (Object entry : sourceList) {
                        BranchNBTCompound layerMap = filtrationTagComparatorMap(branchNBT, (BranchNBTCompound) entry, allowedComparator != null ? allowedComparator.getLayer("[]") : null, disallowComparator != null ? disallowComparator.getLayer("[]") : null, allowedAll);
                        if (layerMap != null) {
                            closeList.add(layerMap);
                        }
                    }
                    break;
                case NBT_LIST:
                    for (Object entry : sourceList) {
                        BranchNBTList layerList = filtrationTagComparatorList(branchNBT, (BranchNBTList) entry, allowedComparator != null ? allowedComparator.getLayer("[]") : null, disallowComparator != null ? disallowComparator.getLayer("[]") : null, allowedAll);
                        if (layerList != null) {
                            closeList.add(layerList);
                        }
                    }
                    break;
                default:
                    for (Object entry : sourceList) {
                        closeList.add(entry);
                    }
                    break;
            }
        }
        return closeList.size() == 0 ? null : closeList;
    }

}