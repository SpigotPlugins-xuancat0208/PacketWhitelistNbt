package xuan.cat.packetwhitelistnbt.code;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import xuan.cat.packetwhitelistnbt.api.branch.BranchNBT;
import xuan.cat.packetwhitelistnbt.api.branch.BranchPacket;
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

    public void onEnable() {
        plugin = this;
        protocolManager = ProtocolLibrary.getProtocolManager();

        saveDefaultConfig();

        configData = new ConfigData(this, getConfig(), branchNBT);
        reduceServer = new ReduceServer(configData, this);

        Bukkit.getPluginManager().registerEvents(new ReduceEvent(configData, reduceServer), this);
        protocolManager.addPacketListener(new ReducePacketEvent(plugin, configData, branchPacket));

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
