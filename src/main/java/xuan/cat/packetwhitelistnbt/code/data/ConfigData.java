package xuan.cat.packetwhitelistnbt.code.data;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.*;
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
    private TagComparator itemTagComparator;


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
        TagComparator itemTagComparator = applyTagComparator(itemAllowedTagList);

        // 正式替換
        this.itemTagComparator = itemTagComparator;
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
                        comparator.setAllowedAll(true);
                    } else {
                        comparator.setAllowed(tag);
                    }
                } else {
                    // 中圖路徑
                    comparator.setAllowed(tag);
                    comparator = comparator.getLayerNotNull(tag);
                }
            }
        }
        return comparatorRoot;
    }


    /** 標籤比較器 */
    private static class TagComparator {
        private final Set<String> allowedSet = new HashSet<>();
        private final Map<String, TagComparator> layerMap = new HashMap<>();
        private boolean allowedAll = false;

        public TagComparator() {
        }

        public boolean isAllowed(String name) {
            return allowedAll || allowedSet.contains(name);
        }
        public void setAllowed(String name) {
            allowedSet.add(name);
        }

        public void setAllowedAll(boolean allowedAll) {
            this.allowedAll = allowedAll;
        }
        public boolean isAllowedAll() {
            return allowedAll;
        }

        public TagComparator getLayer(String name) {
            return layerMap.get(name);
        }
        public TagComparator getLayerNotNull(String name) {
            TagComparator child = layerMap.computeIfAbsent(name, key -> new TagComparator());
            child.allowedAll |= allowedAll;
            return child;
        }

        @Override
        public String toString() {
            return "{" + "allowedSet=" + allowedSet + ", layerMap=" + layerMap + ", allowedAll=" + allowedAll + '}';
        }
    }


    public Recipe filtrationRecipe(Recipe recipe) {
        if (recipe instanceof BlastingRecipe) {
            BlastingRecipe source = (BlastingRecipe) recipe;
            return new BlastingRecipe(source.getKey(), filtrationItem(source.getResult()), source.getInputChoice(), source.getExperience(), source.getCookingTime());

        } else if (recipe instanceof CampfireRecipe) {
            CampfireRecipe source = (CampfireRecipe) recipe;
            return new CampfireRecipe(source.getKey(), filtrationItem(source.getResult()), source.getInputChoice(), source.getExperience(), source.getCookingTime());

        } else if (recipe instanceof FurnaceRecipe) {
            FurnaceRecipe source = (FurnaceRecipe) recipe;
            return new FurnaceRecipe(source.getKey(), filtrationItem(source.getResult()), source.getInputChoice(), source.getExperience(), source.getCookingTime());

        } else if (recipe instanceof MerchantRecipe) {
            MerchantRecipe source = (MerchantRecipe) recipe;
            MerchantRecipe clone = new MerchantRecipe(filtrationItem(source.getResult()), source.getUses(), source.getMaxUses(), source.hasExperienceReward(), source.getVillagerExperience(), source.getPriceMultiplier(), source.shouldIgnoreDiscounts());
            List<ItemStack> ingredientList = new ArrayList<>();
            source.getIngredients().forEach(ingredient -> ingredientList.add(filtrationItem(ingredient)));
            clone.setIngredients(ingredientList);
            return clone;

        } else if (recipe instanceof ShapedRecipe) {
            ShapedRecipe source = (ShapedRecipe) recipe;
            ShapedRecipe clone = new ShapedRecipe(source.getKey(), filtrationItem(source.getResult()));
            clone.shape(source.getShape());
            clone.setGroup(source.getGroup());
            source.getChoiceMap().forEach((character, recipeChoice) -> clone.setIngredient(character, filtrationRecipeChoice(recipeChoice)));
            return clone;

        } else if (recipe instanceof ShapelessRecipe) {
            ShapelessRecipe source = (ShapelessRecipe) recipe;
            ShapelessRecipe clone = new ShapelessRecipe(source.getKey(), filtrationItem(source.getResult()));
            clone.setGroup(source.getGroup());
            source.getChoiceList().forEach((recipeChoice) -> clone.addIngredient(filtrationRecipeChoice(recipeChoice)));
            return clone;

        } else if (recipe instanceof SmithingRecipe) {
            SmithingRecipe source = (SmithingRecipe) recipe;
            try {
                // 適用於 paper
                return new SmithingRecipe(source.getKey(), filtrationItem(source.getResult()), filtrationRecipeChoice(source.getBase()), filtrationRecipeChoice(source.getAddition()), source.willCopyNbt());
            } catch (NoSuchMethodError noSuchMethodError) {
                // 適用於 spigot (不推薦)
                return new SmithingRecipe(source.getKey(), filtrationItem(source.getResult()), filtrationRecipeChoice(source.getBase()), filtrationRecipeChoice(source.getAddition()));
            }
        } else if (recipe instanceof SmokingRecipe) {
            SmokingRecipe source = (SmokingRecipe) recipe;
            return new SmokingRecipe(source.getKey(), filtrationItem(source.getResult()), source.getInputChoice(), source.getExperience(), source.getCookingTime());

        } else if (recipe instanceof StonecuttingRecipe) {
            StonecuttingRecipe source = (StonecuttingRecipe) recipe;
            StonecuttingRecipe clone = new StonecuttingRecipe(source.getKey(), filtrationItem(source.getResult()), filtrationRecipeChoice(source.getInputChoice()));
            clone.setGroup(source.getGroup());
            return clone;

        }
        return recipe;
    }
    private RecipeChoice filtrationRecipeChoice(RecipeChoice recipeChoice) {
        if (recipeChoice instanceof RecipeChoice.ExactChoice) {
            RecipeChoice.ExactChoice exactChoice = (RecipeChoice.ExactChoice) recipeChoice;
            List<ItemStack> choices = new ArrayList<>();
            exactChoice.getChoices().forEach(item -> choices.add(filtrationItem(item)));
            return new RecipeChoice.ExactChoice(choices);
        } else  {
            return recipeChoice;
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
        BranchNBTCompound closeTag = filtrationTagComparatorMap(branchNBT, sourceTag, itemTagComparator);
        if (closeTag != null) {
            closeItem.setCompound("tag", closeTag);
        }
        return branchNBT.toItem(closeItem);
    }
    private static BranchNBTCompound filtrationTagComparatorMap(BranchNBT branchNBT, BranchNBTCompound sourceMap, TagComparator comparator) {
        return filtrationTagComparatorMap(branchNBT, sourceMap, comparator, comparator.allowedAll);
    }
    private static BranchNBTCompound filtrationTagComparatorMap(BranchNBT branchNBT,BranchNBTCompound sourceMap, TagComparator comparator, boolean allowedAll) {
        if (!allowedAll && comparator == null) {
            return null;
        }
        if (comparator != null) {
            allowedAll |= comparator.allowedAll;
        }
        BranchNBTCompound closeMap = branchNBT.createCompound();
        for (String tag : sourceMap.getKeys()) {
            if (allowedAll || comparator.isAllowed(tag)) {
                switch (sourceMap.getType(tag)) {
                    case NBT_COMPOUND:
                        BranchNBTCompound layerMap = filtrationTagComparatorMap(branchNBT, sourceMap.getCompound(tag), comparator != null ? comparator.getLayer(tag) : null, allowedAll);
                        if (layerMap != null) {
                            closeMap.setCompound(tag, layerMap);
                        }
                        break;
                    case NBT_LIST:
                        BranchNBTList cloneList = filtrationTagComparatorList(branchNBT, sourceMap.getList(tag), comparator != null ? comparator.getLayer(tag) : null, allowedAll);
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
    private static BranchNBTList filtrationTagComparatorList(BranchNBT branchNBT,BranchNBTList sourceList, TagComparator comparator, boolean allowedAll) {
        if (!allowedAll && comparator == null) {
            return null;
        }
        if (comparator != null) {
            allowedAll |= comparator.allowedAll;
        }
        BranchNBTList closeList = branchNBT.createList();
        if (allowedAll || comparator.isAllowed("[]")) {
            switch (sourceList.getOwnType()) {
                case NBT_COMPOUND:
                    for (Object entry : sourceList) {
                        BranchNBTCompound layerMap = filtrationTagComparatorMap(branchNBT, (BranchNBTCompound) entry, comparator != null ? comparator.getLayer("[]") : null, allowedAll);
                        if (layerMap != null) {
                            closeList.add(layerMap);
                        }
                    }
                    break;
                case NBT_LIST:
                    for (Object entry : sourceList) {
                        BranchNBTList layerList = filtrationTagComparatorList(branchNBT, (BranchNBTList) entry, comparator != null ? comparator.getLayer("[]") : null, allowedAll);
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