package ru.chatPlugin;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static ru.chatPlugin.ChatPlugin.*;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerLoginEvent event) {
        if (onsave.contains(event.getPlayer().getName())) event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        exec.schedule(new Runnable() {
            @Override
            public void run() {
                final Player player = event.getPlayer();
                boolean suffix = false;
                boolean prefix = false;
                String suffixs = "";
                String prefixs = "";
                int suffixpriority = 0;
                int prefixpriority = 0;
                for (final PermissionAttachmentInfo permatt : player.getEffectivePermissions()) {
                    final String perm = permatt.getPermission();
                    final String[] temp = perm.split("\\.");
                    if (perm.contains("chatsuffix.")) {
                        if (!suffix) {
                            suffix = true;
                            try {
                                suffixpriority = Integer.parseInt(temp[1]);
                            } catch (Exception e) {}
                            if (temp.length == 3) {
                                suffixs = temp[2];
                            } else {
                                for (int i = 2; i < temp.length; i++) {
                                    suffixs = suffixs + temp[i];
                                }
                            }
                        } else {
                            int currentpriority = 0;
                            try {
                                currentpriority = Integer.parseInt(temp[1]);
                            } catch (Exception e) {}
                            if (currentpriority > suffixpriority) {
                                suffixs = "";
                                suffixpriority = currentpriority;
                                if (temp.length == 3) {
                                    suffixs = temp[2];
                                } else {
                                    for (int i = 2; i < temp.length; i++) {
                                        suffixs = suffixs + temp[i];
                                    }
                                }
                            }
                        }
                    } else if (perm.contains("chatprefix.")) {
                        if (!prefix) {
                            prefix = true;
                            try {
                                prefixpriority = Integer.parseInt(temp[1]);
                            } catch (Exception e) {}
                            if (temp.length == 3) {
                                prefixs = temp[2];
                            } else {
                                for (int i = 2; i < temp.length; i++) {
                                    prefixs = prefixs + temp[i];
                                }
                            }
                        } else {
                            int currentpriority = 0;
                            try {
                                currentpriority = Integer.parseInt(temp[1]);
                            } catch (Exception e) {}
                            if (currentpriority > prefixpriority) {
                                prefixs = "";
                                prefixpriority = currentpriority;
                                if (temp.length == 3) {
                                    prefixs = temp[2];
                                } else {
                                    for (int i = 2; i < temp.length; i++) {
                                        prefixs = prefixs + temp[i];
                                    }
                                }
                            }
                        }
                    }
                }
                if (prefix) {
                    final char[] array = prefixs.toCharArray();
                    prefixs = "";
                    final HashSet<Integer> ignorechars = new HashSet<>();
                    final HashSet<Integer> uppercasechars = new HashSet<>();
                    int coincidenze = 0;
                    for (int i = 0; i < array.length; i++) {
                        switch (coincidenze) {
                            case 0:
                                if (array[i] == '@') {
                                    coincidenze = 1;
                                }
                                break;
                            case 1:
                                if (array[i] == '#') {
                                    coincidenze = 2;
                                }
                                break;
                            case 2:
                                if (array[i] == '@') {
                                    coincidenze = 0;
                                    ignorechars.add(i);
                                    ignorechars.add(i - 1);
                                    ignorechars.add(i - 2);
                                    uppercasechars.add(i + 1);
                                }
                                break;
                        }
                    }
                    for (int i = 0; i < array.length; i++) {
                        if (!ignorechars.contains(i)) {
                            if (Character.isLetter(array[i])) {
                                if (uppercasechars.contains(i)) {
                                    prefixs = prefixs + Character.toUpperCase(array[i]);
                                } else {
                                    prefixs = prefixs + array[i];
                                }
                            } else {
                                prefixs = prefixs + array[i];
                            }
                        }
                    }
                }
                if (suffix) {
                    final char[] array = suffixs.toCharArray();
                    suffixs = "";
                    final HashSet<Integer> ignorechars = new HashSet<>();
                    final HashSet<Integer> uppercasechars = new HashSet<>();
                    int coincidenze = 0;
                    for (int i = 0; i < array.length; i++) {
                        switch (coincidenze) {
                            case 0:
                                if (array[i] == '@') {
                                    coincidenze = 1;
                                }
                                break;
                            case 1:
                                if (array[i] == '#') {
                                    coincidenze = 2;
                                }
                                break;
                            case 2:
                                if (array[i] == '@') {
                                    coincidenze = 0;
                                    ignorechars.add(i);
                                    ignorechars.add(i - 1);
                                    ignorechars.add(i - 2);
                                    uppercasechars.add(i + 1);
                                }
                                break;
                        }
                    }
                    for (int i = 0; i < array.length; i++) {
                        if (!ignorechars.contains(i)) {
                            if (Character.isLetter(array[i])) {
                                if (uppercasechars.contains(i)) {
                                    suffixs = suffixs + Character.toUpperCase(array[i]);
                                } else {
                                    suffixs = suffixs + array[i];
                                }
                            } else {
                                suffixs = suffixs + array[i];
                            }
                        }
                    }
                }
                String msgbg = globalmsgformat;
                String msgbl = localmsgformat;
                final String nick = player.getName();
                msgbg = msgbg.replace("%prefix%", prefixs);
                msgbg = msgbg.replace("%nick%", nick);
                msgbg = msgbg.replace("%suffix%", suffixs);
                msgbl = msgbl.replace("%prefix%", prefixs);
                msgbl = msgbl.replace("%nick%", nick);
                msgbl = msgbl.replace("%suffix%", suffixs);
                if (placeholderapisupport) {
                    msgbg = PlaceholderAPI.setPlaceholders(player, msgbg);
                    msgbl = PlaceholderAPI.setPlaceholders(player, msgbl);
                    msgbg = msgbg.replaceAll("\\s+", " ");
                    msgbl = msgbl.replaceAll("\\s+", " ");
                }
                msgbg = msgbg.replace("&", "§");
                msgbl = msgbl.replace("&", "§");
                msgbeginglobal.put(player, msgbg);
                msgbeginlocal.put(player, msgbl);
                lastmsgtime.put(player, System.currentTimeMillis());
                if (chatcolorcommandsupport) {
                    final File playerdata = new File(playersdatafolder + "/" + nick + ".dat");
                    if (!playerdata.exists()) {
                        try {
                            playerdata.createNewFile();
                            final FileWriter writer = new FileWriter(playerdata);
                            writer.write("false|false||||||||");
                            writer.close();
                        } catch (Exception e) {}
                        msgcolorenable.put(player, false);
                        msgcolorisrgb.put(player, false);
                        color.put(player, "");
                        final ConcurrentHashMap<Character, Integer> rgb = Utils.getRGBByColorName("white");
                        rgbcolor1.put(player, rgb);
                        rgbcolor2.put(player, rgb);
                        font.put(player, "");
                        colorname.put(player, "§f§lБелый");
                        rgbcolorname1.put(player, "§f§lБелый");
                        rgbcolorname2.put(player, "§f§lБелый");
                    } else {
                        try {
                            final String[] data = new String(Files.readAllBytes(Paths.get(playerdata.getAbsolutePath()))).split("\\|");
                            msgcolorenable.put(player, Boolean.parseBoolean(data[0]));
                            msgcolorisrgb.put(player, Boolean.parseBoolean(data[1]));
                            color.put(player, data[2]);
                            final ConcurrentHashMap<Character, Integer> rgb1 = new ConcurrentHashMap<>();
                            final String[] rgbarray1 = data[3].split("_");
                            rgb1.put('r', Integer.parseInt(rgbarray1[0]));
                            rgb1.put('g', Integer.parseInt(rgbarray1[1]));
                            rgb1.put('b', Integer.parseInt(rgbarray1[2]));
                            rgbcolor1.put(player, rgb1);
                            final ConcurrentHashMap<Character, Integer> rgb2 = new ConcurrentHashMap<>();
                            final String[] rgbarray2 = data[4].split("_");
                            rgb2.put('r', Integer.parseInt(rgbarray2[0]));
                            rgb2.put('g', Integer.parseInt(rgbarray2[1]));
                            rgb2.put('b', Integer.parseInt(rgbarray2[2]));
                            rgbcolor2.put(player, rgb2);
                            font.put(player, data[5]);
                            colorname.put(player, data[6]);
                            rgbcolorname1.put(player, data[7]);
                            rgbcolorname2.put(player, data[8]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        final Collection<Player> nearbyplayers = player.getWorld().getNearbyPlayers(player.getLocation(), 100);
                        lastnearbyplayers.put(player, nearbyplayers);
                        savetime.put(player, System.currentTimeMillis());
                        players.add(player);
                    }
                }.runTask(plugin);
            }
        }, 0, TimeUnit.MILLISECONDS);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        players.remove(player);
        msgbeginglobal.remove(player);
        msgbeginlocal.remove(player);
        lastmsgtime.remove(player);
        savetime.remove(player);
        lastnearbyplayers.remove(player);
        if (chatcolorcommandsupport) {
            exec.schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        final String nick = player.getName();
                        if (onload.contains(nick)) {
                            while (true) {
                                Thread.sleep(2);
                                if (!onload.contains(nick)) {
                                    msgcolorenable.remove(player);
                                    msgcolorisrgb.remove(player);
                                    color.remove(player);
                                    rgbcolor1.remove(player);
                                    rgbcolor2.remove(player);
                                    font.remove(player);
                                    colorname.remove(player);
                                    rgbcolorname1.remove(player);
                                    rgbcolorname2.remove(player);
                                    onsave.remove(nick);
                                    break;
                                }
                            }
                            return;
                        }
                        final File playerdata = new File(playersdatafolder + "/" + nick + ".dat");
                        final ConcurrentHashMap<Character, Integer> rgb1 = rgbcolor1.get(player);
                        final ConcurrentHashMap<Character, Integer> rgb2 = rgbcolor2.get(player);
                        final String data = msgcolorenable.get(player) + "|" + msgcolorisrgb.get(player) + "|" + color.get(player) + "|" + rgb1.get('r') + "_" + rgb1.get('g') + "_" + rgb1.get('b') + "|" + rgb2.get('r') + "_" + rgb2.get('g') + "_" + rgb2.get('b') + "|" + font.get(player) + "|" + colorname.get(player) + "|" + rgbcolorname1.get(player) + "|" + rgbcolorname2.get(player) + "|";
                        final FileWriter writer = new FileWriter(playerdata);
                        writer.write(data);
                        writer.close();
                        msgcolorenable.remove(player);
                        msgcolorisrgb.remove(player);
                        color.remove(player);
                        rgbcolor1.remove(player);
                        rgbcolor2.remove(player);
                        font.remove(player);
                        colorname.remove(player);
                        rgbcolorname1.remove(player);
                        rgbcolorname2.remove(player);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 0, TimeUnit.MILLISECONDS);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(PlayerChatEvent event) {
        event.setCancelled(true);
        final Player player = event.getPlayer();
        if (enablechatcooldown & !player.hasPermission("chatplugin.cooldownbypass")) {
            if (System.currentTimeMillis() - lastmsgtime.get(player) < chatcooldown) {
                player.sendMessage(cooldownmsg);
                return;
            }
            lastmsgtime.put(player, System.currentTimeMillis());
        }
        String origmsg = event.getMessage();
        if (allowedsymbolscheck) {
            final char[] chars = origmsg.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (!allowedsymbols.contains(Character.toLowerCase(chars[i]) + "")) {
                    player.sendMessage(unallowedsymbolsmsg);
                    return;
                }
            }
        }
        if (player.hasPermission("chatplugin.manualcolor")) {
            origmsg = origmsg.replace("&", "§");
        }
        if (players.contains(player)) {
            if (origmsg.charAt(0) == '!') {
                if (origmsg.length() > 1) {
                    final String msg = msgbeginglobal.get(player) + Utils.colorizemsg(origmsg.substring(1), player);
                    for (final Player pl : players) {
                        pl.sendMessage(msg);
                    }
                }
            } else {
                final String msg = msgbeginlocal.get(player) + Utils.colorizemsg(origmsg, player);
                if (System.currentTimeMillis() - savetime.get(player) < 2000) {
                    for (final Player pl : lastnearbyplayers.get(player)) {
                        pl.sendMessage(msg);
                    }
                } else {
                    final Collection<Player> nearbyplayers = player.getWorld().getNearbyPlayers(player.getLocation(), localchatradius);
                    for (final Player pl : nearbyplayers) {
                        pl.sendMessage(msg);
                    }
                    lastnearbyplayers.put(player, nearbyplayers);
                    savetime.put(player, System.currentTimeMillis());
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInvClick(InventoryClickEvent event) {
        final Inventory inv = event.getClickedInventory();
        if (inv == null) return;
        final Integer maxstacksize = inv.getMaxStackSize();
        if (maxstacksize == null) return;
        if (maxstacksize < 88634) return;
        try {
            final Player player = (Player) inv.getHolder();
            final Player player2 = Bukkit.getPlayer(inv.getViewers().get(0).getName());
            final String item = event.getCurrentItem().getLore().get(0);
            switch (maxstacksize) {
                case 88634:
                    event.setCancelled(true);
                    if (player2 == null || !players.contains(player2)) {
                        player2.closeInventory();
                        player2.sendMessage(playernotfoundmsg);
                        return;
                    }
                    switch (item) {
                        case "§c§lВыключить кастомные сообщения":
                            Utils.disablemsgcolor(player2);
                            inv.setItem(3, null);
                            inv.setItem(4, Prepared.enablecustommessages);
                            inv.setItem(5, null);
                            break;
                        case "§a§lВключить кастомные сообщения":
                            msgcolorenable.put(player2, true);
                            inv.setItem(3, Prepared.disablecustommessages);
                            inv.setItem(4, Prepared.colorsettings);
                            inv.setItem(5, Prepared.fontsettings);
                            break;
                        case "§a§lНастройки цвета":
                            if (msgcolorisrgb.get(player2)) {
                                player.openInventory(Utils.createRGBMenu(player, player2));
                            } else {
                                final String title;
                                if (player == player2) {
                                    title = "§0§lНастройки сообщений";
                                } else {
                                    title = "§0§lНастройки сообщений игрока " + player2.getName();
                                }
                                final Inventory menu = Bukkit.createInventory(player2, 36, title);
                                menu.setItem(0, Prepared.setrgb);
                                Utils.fillInventoryColors(menu, 18);
                                final ItemStack currentcolor = preparedheads.get(colorname.get(player)).clone();
                                final ItemMeta meta = currentcolor.getItemMeta();
                                meta.setDisplayName("§f§lТекущий цвет:");
                                final List<String> lorelist = new ArrayList<>();
                                lorelist.add(meta.getLore().get(0));
                                meta.setLore(lorelist);
                                currentcolor.setItemMeta(meta);
                                menu.setItem(4, currentcolor);
                                menu.setItem(8, Prepared.back);
                                menu.setMaxStackSize(88636);
                                player.openInventory(menu);
                            }
                            break;
                        case "§a§lНастройки шрифта":
                            inv.setItem(4, null);
                            inv.setItem(5, null);
                            final String currentfont = font.get(player2);
                            if (currentfont.contains("§o")) {
                                inv.setItem(0, Prepared.offitalic);
                            } else {
                                inv.setItem(0, Prepared.onitalic);
                            }
                            if (currentfont.contains("§l")) {
                                inv.setItem(1, Prepared.offbold);
                            } else {
                                inv.setItem(1, Prepared.onbold);
                            }
                            if (currentfont.contains("§n")) {
                                inv.setItem(2, Prepared.offunderline);
                            } else {
                                inv.setItem(2, Prepared.onunderline);
                            }
                            if (currentfont.contains("§m")) {
                                inv.setItem(3, Prepared.offstrikethrough);
                            } else {
                                inv.setItem(3, Prepared.onstrikethrough);
                            }
                            inv.setItem(8, Prepared.back);
                            inv.setMaxStackSize(88635);
                            break;
                    }
                    break;
                case 88635:
                    event.setCancelled(true);
                    if (player2 == null || !players.contains(player2)) {
                        player2.closeInventory();
                        player2.sendMessage(playernotfoundmsg);
                        return;
                    }
                    final String currentfont = font.get(player2);
                    switch (item) {
                        case "§c§lВыключить наклонённый шрифт":
                            font.put(player2, currentfont.replace("§o", ""));
                            inv.setItem(0, Prepared.onitalic);
                            break;
                        case "§a§lВключить наклонённый шрифт":
                            font.put(player2, currentfont + "§o");
                            inv.setItem(0, Prepared.offitalic);
                            break;
                        case "§c§lВыключить жирный шрифт":
                            font.put(player2, currentfont.replace("§l", ""));
                            inv.setItem(1, Prepared.onbold);
                            break;
                        case "§a§lВключить жирный шрифт":
                            font.put(player2, currentfont + "§l");
                            inv.setItem(1, Prepared.offbold);
                            break;
                        case "§c§lВыключить подчёркнутый шрифт":
                            font.put(player2, currentfont.replace("§n", ""));
                            inv.setItem(2, Prepared.onunderline);
                            break;
                        case "§a§lВключить подчёркнутый шрифт":
                            font.put(player2, currentfont + "§n");
                            inv.setItem(2, Prepared.offunderline);
                            break;
                        case "§c§lВыключить зачёркнутый шрифт":
                            font.put(player2, currentfont.replace("§m", ""));
                            inv.setItem(3, Prepared.onstrikethrough);
                            break;
                        case "§a§lВключить зачёркнутый шрифт":
                            font.put(player2, currentfont + "§m");
                            inv.setItem(3, Prepared.offstrikethrough);
                            break;
                        case "§lНазад":
                            inv.setItem(0, null);
                            inv.setItem(1, null);
                            inv.setItem(2, null);
                            inv.setItem(8, null);
                            if (!msgcolorenable.get(player2)) {
                                inv.setItem(3, null);
                                inv.setItem(4, Prepared.enablecustommessages);
                            } else {
                                inv.setItem(3, Prepared.disablecustommessages);
                                inv.setItem(4, Prepared.colorsettings);
                                inv.setItem(5, Prepared.fontsettings);
                            }
                            inv.setMaxStackSize(88634);
                            break;
                    }
                    break;
                case 88636:
                    event.setCancelled(true);
                    if (player2 == null || !players.contains(player2)) {
                        player2.closeInventory();
                        player2.sendMessage(playernotfoundmsg);
                        return;
                    }
                    switch (item) {
                        case "§lНазад":
                            Utils.openSettings(player, player2);
                            break;
                        case "§lПоменять тип цвета на градиент":
                            msgcolorisrgb.put(player2, true);
                            player.openInventory(Utils.createRGBMenu(player, player2));
                            break;
                        default:
                            colorname.put(player2, item);
                            color.put(player2, item.substring(0, 2));
                            final ItemStack currentcolor = preparedheads.get(colorname.get(player)).clone();
                            final ItemMeta meta = currentcolor.getItemMeta();
                            meta.setDisplayName("§f§lТекущий цвет:");
                            final List<String> lorelist = new ArrayList<>();
                            lorelist.add(meta.getLore().get(0));
                            meta.setLore(lorelist);
                            currentcolor.setItemMeta(meta);
                            inv.setItem(4, currentcolor);
                            break;
                    }
                    break;
                case 88637:
                    event.setCancelled(true);
                    if (player2 == null || !players.contains(player2)) {
                        player2.closeInventory();
                        player2.sendMessage(playernotfoundmsg);
                        return;
                    }
                    switch (item) {
                        case "§lНазад":
                            Utils.openSettings(player, player2);
                            break;
                        case "§lПоменять тип цвета на обычный":
                            msgcolorisrgb.put(player2, false);
                            final String title;
                            if (player == player2) {
                                title = "§0§lНастройки сообщений";
                            } else {
                                title = "§0§lНастройки сообщений игрока " + player2.getName();
                            }
                            final Inventory menu = Bukkit.createInventory(player2, 36, title);
                            menu.setItem(0, Prepared.setrgb);
                            Utils.fillInventoryColors(menu, 18);
                            menu.setItem(8, Prepared.back);
                            final ItemStack currentcolor = preparedheads.get(colorname.get(player)).clone();
                            final ItemMeta meta = currentcolor.getItemMeta();
                            meta.setDisplayName("§f§lТекущий цвет:");
                            final List<String> lorelist = new ArrayList<>();
                            lorelist.add(meta.getLore().get(0));
                            meta.setLore(lorelist);
                            currentcolor.setItemMeta(meta);
                            menu.setItem(4, currentcolor);
                            menu.setMaxStackSize(88636);
                            player.openInventory(menu);
                            break;
                        case "§f§lПервый цвет":
                            final String color1title;
                            if (player == player2) {
                                color1title = "§0§lНастройки сообщений";
                            } else {
                                color1title = "§0§lНастройки сообщений игрока " + player2.getName();
                            }
                            final Inventory color1menu = Bukkit.createInventory(player2, 18, color1title);
                            color1menu.setMaxStackSize(88638);
                            Utils.fillInventoryColors(color1menu, 0);
                            player.openInventory(color1menu);
                            break;
                        case "§f§lВторой цвет":
                            final String color2title;
                            if (player == player2) {
                                color2title = "§0§lНастройки сообщений";
                            } else {
                                color2title = "§0§lНастройки сообщений игрока " + player2.getName();
                            }
                            final Inventory color2menu = Bukkit.createInventory(player2, 18, color2title);
                            color2menu.setMaxStackSize(88639);
                            Utils.fillInventoryColors(color2menu, 0);
                            player.openInventory(color2menu);
                            break;
                    }
                    break;
                case 88638:
                    event.setCancelled(true);
                    if (player2 == null || !players.contains(player2)) {
                        player2.closeInventory();
                        player2.sendMessage(playernotfoundmsg);
                        return;
                    }
                    rgbcolorname1.put(player2, item);
                    rgbcolor1.put(player2, Utils.getRGBByColorName(item));
                    player.openInventory(Utils.createRGBMenu(player, player2));
                    break;
                case 88639:
                    event.setCancelled(true);
                    if (player2 == null || !players.contains(player2)) {
                        player2.closeInventory();
                        player2.sendMessage(playernotfoundmsg);
                        return;
                    }
                    rgbcolorname2.put(player2, item);
                    rgbcolor2.put(player2, Utils.getRGBByColorName(item));
                    player.openInventory(Utils.createRGBMenu(player, player2));
                    break;
            }
        } catch (Exception e) {}
    }
}
