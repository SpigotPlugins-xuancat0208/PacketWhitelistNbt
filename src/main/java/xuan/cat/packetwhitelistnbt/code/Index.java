package xuan.cat.packetwhitelistnbt.code;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import xuan.cat.packetwhitelistnbt.api.branch.BranchMinecraft;
import xuan.cat.packetwhitelistnbt.api.branch.BranchNBT;
import xuan.cat.packetwhitelistnbt.api.branch.BranchPacket;
import xuan.cat.packetwhitelistnbt.code.branch.v18.Branch_18_Minecraft;
import xuan.cat.packetwhitelistnbt.code.branch.v18.Branch_18_NBT;
import xuan.cat.packetwhitelistnbt.code.branch.v18.Branch_18_Packet;
import xuan.cat.packetwhitelistnbt.code.command.Command;
import xuan.cat.packetwhitelistnbt.code.command.CommandSuggest;
import xuan.cat.packetwhitelistnbt.code.data.ConfigData;

public final class Index extends JavaPlugin {
    private static ProtocolManager protocolManager;
    private static Plugin plugin;
    private static ReduceServer reduceServer;
    private static ConfigData configData;
    private static BranchNBT branchNBT;
    private static BranchPacket branchPacket;
    private static BranchMinecraft branchMinecraft;

    public void onEnable() {
        plugin = this;
        protocolManager = ProtocolLibrary.getProtocolManager();

        saveDefaultConfig();

        // 檢測版本
        String bukkitVersion = Bukkit.getBukkitVersion();
        /*if (bukkitVersion.matches("1\\.14.*\\-R0\\.1.*")) {
            // 1.14
            branchPacket    = new Branch_14_R1_Packet();
            branchMinecraft = new Branch_14_R1_Minecraft();
            chunkServer     = new ChunkServer(configData, this, ViewShape.SQUARE, branchMinecraft, branchPacket);
        } else if (bukkitVersion.matches("1\\.15.*\\-R0\\.1.*")) {
            // 1.15
            branchPacket    = new Branch_15_R1_Packet();
            branchMinecraft = new Branch_15_R1_Minecraft();
            chunkServer     = new ChunkServer(configData, this, ViewShape.SQUARE, branchMinecraft, branchPacket);
        } else if (bukkitVersion.matches("1\\.16.*\\-R0\\.1.*")) {
            // 1.16
            branchPacket    = new Branch_16_R3_Packet();
            branchMinecraft = new Branch_16_R3_Minecraft();
            chunkServer     = new ChunkServer(configData, this, ViewShape.SQUARE, branchMinecraft, branchPacket);
        } else if (bukkitVersion.matches("1\\.17.*\\-R0\\.1.*")) {
            // 1.17
            branchPacket    = new Branch_17_Packet();
            branchMinecraft = new Branch_17_Minecraft();
            chunkServer     = new ChunkServer(configData, this, ViewShape.SQUARE, branchMinecraft, branchPacket);
        } else */if (bukkitVersion.matches("1\\.18.*\\-R0\\.1.*")) {
            // 1.18
            branchPacket    = new Branch_18_Packet();
            branchNBT       = new Branch_18_NBT();
            branchMinecraft = new Branch_18_Minecraft();
        } else {
            throw new NullPointerException("Unsupported MC version");
        }

        configData = new ConfigData(this, getConfig(), branchNBT);
        reduceServer = new ReduceServer(configData, this);

        Bukkit.getPluginManager().registerEvents(new ReduceEvent(configData, reduceServer), this);
        protocolManager.addPacketListener(new ReducePacketEvent(plugin, configData, branchPacket, branchMinecraft));

        // 指令
        PluginCommand command = getCommand("whitelistnbt");
        if (command != null) {
            command.setExecutor(new Command(reduceServer, configData));
            command.setTabCompleter(new CommandSuggest());
        }
    }

    public static ReduceServer getReduceServer() {
        return reduceServer;
    }

    public static ConfigData getConfigData() {
        return configData;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public void onDisable() {
    }
}
