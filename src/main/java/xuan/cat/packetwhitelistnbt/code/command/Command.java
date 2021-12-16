package xuan.cat.packetwhitelistnbt.code.command;

import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xuan.cat.packetwhitelistnbt.code.ReduceServer;
import xuan.cat.packetwhitelistnbt.code.data.ConfigData;

public final class Command implements CommandExecutor {
    private final ReduceServer reduceServer;
    private final ConfigData configData;

    public Command(ReduceServer reduceServer, ConfigData configData) {
        this.reduceServer   = reduceServer;
        this.configData     = configData;
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String message, String[] parameters) {
        if (!sender.hasPermission("command.packetwhitelistnbt")) {
            // 無權限
            sender.sendMessage(new TranslatableComponent("server.command.no_permission"));
        } else {
            if (parameters.length != 1 || parameters[0] == null) {
                // 缺少參數
                sender.sendMessage(new TranslatableComponent("server.command.missing_parameters"));
            } else {
                switch (parameters[0]) {
                    case "reload":
                        try {
                            configData.reload();
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                player.updateInventory();
                            }
                            // 重讀配置完成
                            sender.sendMessage(new TranslatableComponent("server.command.reread_configuration_successfully"));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            // 重讀配置錯誤
                            sender.sendMessage(new TranslatableComponent("server.command.reread_configuration_error"));
                        }
                        break;
                    default:
                        // 未知的參數類型
                        sender.sendMessage(new TranslatableComponent("server.command.unknown_parameter_type"));
                        break;
                }
            }
        }
        return true;
    }
}
