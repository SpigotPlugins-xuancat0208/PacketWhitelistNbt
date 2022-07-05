package xuan.cat.packetwhitelistnbt.code.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public final class CommandSuggest implements TabCompleter {
    public CommandSuggest() {
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] parameters) {
        if (!sender.hasPermission("command.whitelistnbt")) {
            return new ArrayList<>();
        }

        List<String> list = new ArrayList<>();

        if (parameters.length == 1) {
            list.add("reload");
            list.add("permissionCheck");
        } else if (parameters.length == 2) {
            switch (parameters[0]) {
                case "permissionCheck":
                    Bukkit.getOnlinePlayers().forEach(player -> list.add(player.getName()));
                    break;
            }
        }

        return list;
    }
}
