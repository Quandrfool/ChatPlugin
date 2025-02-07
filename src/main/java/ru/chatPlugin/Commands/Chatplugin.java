package ru.chatPlugin.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.chatPlugin.Config;

import java.util.ArrayList;
import java.util.List;

import static ru.chatPlugin.ChatPlugin.*;

public class Chatplugin implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0 && args[0].equals("color")) {
            final String[] newArgs = new String[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                newArgs[i - 1] = args[i];
            }
            chatcolorExecutor.onCommand(sender, command, label, newArgs);
            return false;
        }
        switch (args.length) {
            case 1:
                switch (args[0]) {
                    case "reload":
                        if (sender.hasPermission("chatplugin.reload")) {
                            Config.loadConfig();
                            sender.sendMessage(reloadmsg);
                        } else {
                            sender.sendMessage(nopermsmsg);
                        }
                        break;
                    default:
                        if (sender.hasPermission("chatplugin.help")) {
                            sender.sendMessage(helpmsg);
                        } else {
                            sender.sendMessage(nopermsmsg);
                        }
                        break;
                }
                break;
            default:
                if (sender.hasPermission("chatplugin.help")) {
                    sender.sendMessage(helpmsg);
                } else {
                    sender.sendMessage(nopermsmsg);
                }
                break;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        final List<String> completions = new ArrayList<>();
        switch (args.length) {
            case 1:
                completions.add("reload");
                if (sender.hasPermission("chatplugin.chatcolor.others")) completions.add("color");
                break;
            case 2:
                if (args[0].equals("color")) {
                    if (sender.hasPermission("chatplugin.chatcolor.others")) {
                        for (Player player : players) {
                            completions.add(player.getName());
                        }
                    }
                }
                break;
            case 3:
                if (sender.hasPermission("chatplugin.chatcolor.others") & args[0].equals("color")) {
                    completions.add("red");
                    completions.add("dark-red");
                    completions.add("yellow");
                    completions.add("orange");
                    completions.add("green");
                    completions.add("dark-green");
                    completions.add("blue");
                    completions.add("dark-blue");
                    completions.add("purple");
                    completions.add("dark-purple");
                    completions.add("black");
                    completions.add("gray");
                    completions.add("dark-gray");
                    completions.add("aqua");
                    completions.add("dark-aqua");
                    completions.add("off");
                    completions.add("italic");
                    completions.add("bold");
                    completions.add("underline");
                    completions.add("strikethrough");
                }
        }
        return completions;
    }
}
