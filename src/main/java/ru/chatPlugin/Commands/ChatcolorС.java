package ru.chatPlugin.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.chatPlugin.Utils;

import java.util.ArrayList;
import java.util.List;

import static ru.chatPlugin.ChatPlugin.*;

public class ChatcolorС implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("chatplugin.chatcolor")) {
            if (sender instanceof Player) {
                switch (args.length) {
                    case 0:
                        Utils.openSettings((Player) sender, (Player) sender);
                        break;
                    case 1:
                        if (sender.hasPermission("chatplugin.chatcolor.others")) {
                            final Player target = Bukkit.getPlayer(args[0]);
                            if (target != null && players.contains(target)) {
                                Utils.openSettings((Player) sender, target);
                            } else {
                                sender.sendMessage(playernotfoundmsg);
                            }
                        } else {
                            sender.sendMessage(nopermsmsg);
                        }
                        break;
                    case 2:
                        args2(sender, args);
                        break;
                    default:
                        sender.sendMessage(chatcolorusageplayersmsg);
                        break;
                }
            } else {
                if (args.length == 2) {
                    args2(sender, args);
                } else {
                    sender.sendMessage(chatcolorusageconsolemsg);
                }
            }
        } else {
            sender.sendMessage(nopermsmsg);
        }
        return false;
    }

    public void args2(CommandSender sender, String[] args) {
        if (sender.hasPermission("chatplugin.chatcolor.others")) {
            final Player target = Bukkit.getPlayer(args[0]);
            if (target != null && players.contains(target)) {
                if (!colorCodes.containsKey(args[1])) {
                    if (!Utils.setFont(args[1], sender, target)) {
                        if (!Utils.setRgb(args[1], sender, target)) {
                            if (args[1].equals("off")) {
                                Utils.disableMsgColor(target);
                                sender.sendMessage(chatcoloroffothersmsg.replace("%nick%", target.getName()));
                            } else {
                                sender.sendMessage(colornotfoundmsg);
                            }
                        } else {
                            msgColorEnable.put(target, true);
                            msgColorIsRgb.put(target, true);
                        }
                    } else {
                        msgColorEnable.put(target, true);
                    }
                } else {
                    color.put(target, colorCodes.get(args[1]));
                    sender.sendMessage(colorsetothersmsg.replace("%nick%", target.getName()));
                    msgColorIsRgb.put(target, false);
                    msgColorEnable.put(target, true);
                }
            } else {
                sender.sendMessage(playernotfoundmsg);
            }
        } else {
            sender.sendMessage(nopermsmsg);
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        final List<String> completions = new ArrayList<>();
        switch (args.length) {
            case 1:
                if (sender.hasPermission("chatplugin.chatcolor.others")) {
                    for (Player player : players) {
                        completions.add(player.getName());
                    }
                }
                break;
            case 2:
                if (sender.hasPermission("chatplugin.chatcolor.others")) {
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
