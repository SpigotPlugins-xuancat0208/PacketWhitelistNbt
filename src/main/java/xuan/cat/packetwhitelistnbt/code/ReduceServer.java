package xuan.cat.packetwhitelistnbt.code;

import org.bukkit.plugin.Plugin;
import xuan.cat.packetwhitelistnbt.code.data.ConfigData;

public final class ReduceServer {
    private final ConfigData configData;
    private final Plugin plugin;


    public ReduceServer(ConfigData configData, Plugin plugin) {
        this.configData = configData;
        this.plugin = plugin;
    }


}
