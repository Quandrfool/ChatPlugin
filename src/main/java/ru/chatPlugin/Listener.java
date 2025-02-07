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
        if (onSave.contains(event.getPlayer().getName())) event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final String nick = player.getName();
        onLoad.add(nick);
        exec.schedule(new Runnable() {
            @Override
            public void run() {
                boolean suffix = false;
                boolean prefix = false;
                String suffixs = "";
                String prefixs = "";
                int suffixPriority = 0;
                int prefixPriority = 0;
                try {
                    for (PermissionAttachmentInfo permAtt : player.getEffectivePermissions()) {
                        final String perm = permAtt.getPermission();
                        final String[] temp = perm.split("\\.");
                        if (perm.contains("chatsuffix.")) {
                            if (!suffix) {
                                suffix = true;
                                suffixPriority = Integer.parseInt(temp[1]);
                                if (temp.length == 3) {
                                    suffixs = temp[2];
                                } else {
                                    for (int i = 2; i < temp.length; i++) {
                                        suffixs += temp[i];
                                    }
                                }
                            } else {
                                int currentPriority = 0;
                                currentPriority = Integer.parseInt(temp[1]);
                                if (currentPriority > suffixPriority) {
                                    suffixs = "";
                                    suffixPriority = currentPriority;
                                    if (temp.length == 3) {
                                        suffixs = temp[2];
                                    } else {
                                        for (int i = 2; i < temp.length; i++) {
                                            suffixs += temp[i];
                                        }
                                    }
                                }
                            }
                        } else if (perm.contains("chatprefix.")) {
                            if (!prefix) {
                                prefix = true;
                                prefixPriority = Integer.parseInt(temp[1]);
                                if (temp.length == 3) {
                                    prefixs = temp[2];
                                } else {
                                    for (int i = 2; i < temp.length; i++) {
                                        prefixs += temp[i];
                                    }
                                }
                            } else {
                                int currentPriority = 0;
                                currentPriority = Integer.parseInt(temp[1]);
                                if (currentPriority > prefixPriority) {
                                    prefixs = "";
                                    prefixPriority = currentPriority;
                                    if (temp.length == 3) {
                                        prefixs = temp[2];
                                    } else {
                                        for (int i = 2; i < temp.length; i++) {
                                            prefixs += temp[i];
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (prefix) {
                        final char[] array = prefixs.toCharArray();
                        prefixs = "";
                        final HashSet<Integer> ignoreChars = new HashSet<>();
                        final HashSet<Integer> upperCaseChars = new HashSet<>();
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
                                        ignoreChars.add(i);
                                        ignoreChars.add(i - 1);
                                        ignoreChars.add(i - 2);
                                        upperCaseChars.add(i + 1);
                                    }
                                    break;
                            }
                        }
                        for (int i = 0; i < array.length; i++) {
                            if (!ignoreChars.contains(i)) {
                                if (Character.isLetter(array[i])) {
                                    if (upperCaseChars.contains(i)) {
                                        prefixs += Character.toUpperCase(array[i]);
                                    } else {
                                        prefixs += array[i];
                                    }
                                } else {
                                    prefixs += array[i];
                                }
                            }
                        }
                    }
                    if (suffix) {
                        final char[] array = suffixs.toCharArray();
                        suffixs = "";
                        final HashSet<Integer> ignoreChars = new HashSet<>();
                        final HashSet<Integer> upperCaseChars = new HashSet<>();
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
                                        ignoreChars.add(i);
                                        ignoreChars.add(i - 1);
                                        ignoreChars.add(i - 2);
                                        upperCaseChars.add(i + 1);
                                    }
                                    break;
                            }
                        }
                        for (int i = 0; i < array.length; i++) {
                            if (!ignoreChars.contains(i)) {
                                if (Character.isLetter(array[i])) {
                                    if (upperCaseChars.contains(i)) {
                                        suffixs += Character.toUpperCase(array[i]);
                                    } else {
                                        suffixs += array[i];
                                    }
                                } else {
                                    suffixs += array[i];
                                }
                            }
                        }
                    }
                    String msgBg = globalmsgformat;
                    String msgBl = localmsgformat;
                    msgBg = msgBg.replace("%prefix%", prefixs);
                    msgBg = msgBg.replace("%nick%", nick);
                    msgBg = msgBg.replace("%suffix%", suffixs);
                    msgBl = msgBl.replace("%prefix%", prefixs);
                    msgBl = msgBl.replace("%nick%", nick);
                    msgBl = msgBl.replace("%suffix%", suffixs);
                    if (placeholderapisupport) {
                        msgBg = PlaceholderAPI.setPlaceholders(player, msgBg);
                        msgBl = PlaceholderAPI.setPlaceholders(player, msgBl);
                        msgBg = msgBg.replaceAll("\\s+", " ");
                        msgBl = msgBl.replaceAll("\\s+", " ");
                    }
                    msgBg = msgBg.replace("&", "§");
                    msgBl = msgBl.replace("&", "§");
                    msgBeginGlobal.put(player, msgBg);
                    msgBeginLocal.put(player, msgBl);
                    lastMsgTime.put(player, System.currentTimeMillis());
                    if (chatcolorcommandsupport) {
                        final File playerData = new File(playersDataFolder + "/" + nick + ".dat");
                        if (!playerData.exists()) {
                            playerData.createNewFile();
                            final FileWriter writer = new FileWriter(playerData);
                            writer.write("false|false||||||||");
                            writer.close();
                            msgColorEnable.put(player, false);
                            msgColorIsRgb.put(player, false);
                            color.put(player, "");
                            final ConcurrentHashMap<Character, Integer> rgb = Utils.getRgbByColorName("white");
                            rgbColor1.put(player, rgb);
                            rgbColor2.put(player, rgb);
                            font.put(player, "");
                            colorName.put(player, "§f§lБелый");
                            rgbColorName1.put(player, "§f§lБелый");
                            rgbColorName2.put(player, "§f§lБелый");
                        } else {
                            final String[] data = new String(Files.readAllBytes(Paths.get(playerData.getAbsolutePath()))).split("\\|");
                            msgColorEnable.put(player, Boolean.parseBoolean(data[0]));
                            msgColorIsRgb.put(player, Boolean.parseBoolean(data[1]));
                            color.put(player, data[2]);
                            final ConcurrentHashMap<Character, Integer> rgb1 = new ConcurrentHashMap<>();
                            final String[] rgbArray1 = data[3].split("_");
                            rgb1.put('r', Integer.parseInt(rgbArray1[0]));
                            rgb1.put('g', Integer.parseInt(rgbArray1[1]));
                            rgb1.put('b', Integer.parseInt(rgbArray1[2]));
                            rgbColor1.put(player, rgb1);
                            final ConcurrentHashMap<Character, Integer> rgb2 = new ConcurrentHashMap<>();
                            final String[] rgbArray2 = data[4].split("_");
                            rgb2.put('r', Integer.parseInt(rgbArray2[0]));
                            rgb2.put('g', Integer.parseInt(rgbArray2[1]));
                            rgb2.put('b', Integer.parseInt(rgbArray2[2]));
                            rgbColor2.put(player, rgb2);
                            font.put(player, data[5]);
                            colorName.put(player, data[6]);
                            rgbColorName1.put(player, data[7]);
                            rgbColorName2.put(player, data[8]);
                        }
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            lastNearbyPlayers.put(player, player.getWorld().getNearbyPlayers(player.getLocation(), 100));
                            saveTime.put(player, System.currentTimeMillis());
                            players.add(player);
                        }
                    }.runTask(plugin);
                    onLoad.remove(nick);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 0, TimeUnit.MILLISECONDS);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final String nick = player.getName();
        players.remove(player);
        msgBeginGlobal.remove(player);
        msgBeginLocal.remove(player);
        lastMsgTime.remove(player);
        saveTime.remove(player);
        lastNearbyPlayers.remove(player);
        if (chatcolorcommandsupport) {
            onSave.add(nick);
            exec.schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (onLoad.contains(nick)) {
                            while (true) {
                                Thread.sleep(2);
                                if (!onLoad.contains(nick)) {
                                    msgColorEnable.remove(player);
                                    msgColorIsRgb.remove(player);
                                    color.remove(player);
                                    rgbColor1.remove(player);
                                    rgbColor2.remove(player);
                                    font.remove(player);
                                    colorName.remove(player);
                                    rgbColorName1.remove(player);
                                    rgbColorName2.remove(player);
                                    onSave.remove(nick);
                                    break;
                                }
                            }
                            return;
                        }
                        final ConcurrentHashMap<Character, Integer> rgb1 = rgbColor1.get(player);
                        final ConcurrentHashMap<Character, Integer> rgb2 = rgbColor2.get(player);
                        final String data = msgColorEnable.get(player) + "|" + msgColorIsRgb.get(player) + "|" + color.get(player) + "|" + rgb1.get('r') + "_" + rgb1.get('g') + "_" + rgb1.get('b') + "|" + rgb2.get('r') + "_" + rgb2.get('g') + "_" + rgb2.get('b') + "|" + font.get(player) + "|" + colorName.get(player) + "|" + rgbColorName1.get(player) + "|" + rgbColorName2.get(player) + "|";
                        final FileWriter writer = new FileWriter(playersDataFolder + "/" + nick + ".dat");
                        writer.write(data);
                        writer.close();
                        msgColorEnable.remove(player);
                        msgColorIsRgb.remove(player);
                        color.remove(player);
                        rgbColor1.remove(player);
                        rgbColor2.remove(player);
                        font.remove(player);
                        colorName.remove(player);
                        rgbColorName1.remove(player);
                        rgbColorName2.remove(player);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    onSave.remove(nick);
                }
            }, 0, TimeUnit.MILLISECONDS);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(PlayerChatEvent event) {
        event.setCancelled(true);
        final Player player = event.getPlayer();
        if (enablechatcooldown & !player.hasPermission("chatplugin.cooldownbypass")) {
            if (System.currentTimeMillis() - lastMsgTime.get(player) < chatcooldown) {
                player.sendMessage(cooldownmsg);
                return;
            }
            lastMsgTime.put(player, System.currentTimeMillis());
        }
        String origMsg = event.getMessage();
        if (allowedsymbolscheck) {
            final char[] chars = origMsg.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (!allowedsymbols.contains(Character.toLowerCase(chars[i]) + "")) {
                    player.sendMessage(unallowedsymbolsmsg);
                    return;
                }
            }
        }
        if (player.hasPermission("chatplugin.manualcolor")) {
            origMsg = origMsg.replace("&", "§");
        }
        if (players.contains(player)) {
            if (origMsg.charAt(0) == '!') {
                if (origMsg.length() > 1) {
                    final String msg = msgBeginGlobal.get(player) + Utils.colorizeMsg(origMsg.substring(1), player);
                    for (Player pl : players) {
                        pl.sendMessage(msg);
                    }
                }
            } else {
                final String msg = msgBeginLocal.get(player) + Utils.colorizeMsg(origMsg, player);
                if (System.currentTimeMillis() - saveTime.get(player) < 2000) {
                    for (Player pl : lastNearbyPlayers.get(player)) {
                        pl.sendMessage(msg);
                    }
                } else {
                    final Collection<Player> nearbyPlayers = player.getWorld().getNearbyPlayers(player.getLocation(), localchatradius);
                    for (Player pl : nearbyPlayers) {
                        pl.sendMessage(msg);
                    }
                    lastNearbyPlayers.put(player, nearbyPlayers);
                    saveTime.put(player, System.currentTimeMillis());
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInvClick(InventoryClickEvent event) {
        final Inventory inv = event.getClickedInventory();
        if (inv == null) return;
        final Integer maxStackSize = inv.getMaxStackSize();
        if (maxStackSize == null) return;
        if (maxStackSize < 88634) return;
        try {
            final Player player = (Player) inv.getHolder();
            final Player player2 = Bukkit.getPlayer(inv.getViewers().get(0).getName());
            final String item = event.getCurrentItem().getLore().get(0);
            switch (maxStackSize) {
                case 88634:
                    event.setCancelled(true);
                    if (player2 == null || !players.contains(player2)) {
                        player2.closeInventory();
                        player2.sendMessage(playernotfoundmsg);
                        return;
                    }
                    switch (item) {
                        case "§c§lВыключить кастомные сообщения":
                            Utils.disableMsgColor(player2);
                            inv.setItem(3, null);
                            inv.setItem(4, Prepared.enableCustomMessages);
                            inv.setItem(5, null);
                            break;
                        case "§a§lВключить кастомные сообщения":
                            msgColorEnable.put(player2, true);
                            inv.setItem(3, Prepared.disableCustomMessages);
                            inv.setItem(4, Prepared.colorSettings);
                            inv.setItem(5, Prepared.fontSettings);
                            break;
                        case "§a§lНастройки цвета":
                            if (msgColorIsRgb.get(player2)) {
                                player.openInventory(Utils.createRgbMenu(player, player2));
                            } else {
                                final String title;
                                if (player == player2) {
                                    title = "§0§lНастройки сообщений";
                                } else {
                                    title = "§0§lНастройки сообщений игрока " + player2.getName();
                                }
                                final Inventory menu = Bukkit.createInventory(player2, 36, title);
                                menu.setItem(0, Prepared.setRgb);
                                Utils.fillInventoryColors(menu, 18);
                                final ItemStack currentColor = preparedHeads.get(colorName.get(player)).clone();
                                final ItemMeta meta = currentColor.getItemMeta();
                                meta.setDisplayName("§f§lТекущий цвет:");
                                final List<String> loreList = new ArrayList<>();
                                loreList.add(meta.getLore().get(0));
                                meta.setLore(loreList);
                                currentColor.setItemMeta(meta);
                                menu.setItem(4, currentColor);
                                menu.setItem(8, Prepared.back);
                                menu.setMaxStackSize(88636);
                                player.openInventory(menu);
                            }
                            break;
                        case "§a§lНастройки шрифта":
                            inv.setItem(4, null);
                            inv.setItem(5, null);
                            final String currentFont = font.get(player2);
                            if (currentFont.contains("§o")) {
                                inv.setItem(0, Prepared.offItalic);
                            } else {
                                inv.setItem(0, Prepared.onItalic);
                            }
                            if (currentFont.contains("§l")) {
                                inv.setItem(1, Prepared.offBold);
                            } else {
                                inv.setItem(1, Prepared.onBold);
                            }
                            if (currentFont.contains("§n")) {
                                inv.setItem(2, Prepared.offUnderline);
                            } else {
                                inv.setItem(2, Prepared.onUnderline);
                            }
                            if (currentFont.contains("§m")) {
                                inv.setItem(3, Prepared.offStrikethrough);
                            } else {
                                inv.setItem(3, Prepared.onStrikethrough);
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
                    final String currentFont = font.get(player2);
                    switch (item) {
                        case "§c§lВыключить наклонённый шрифт":
                            font.put(player2, currentFont.replace("§o", ""));
                            inv.setItem(0, Prepared.onItalic);
                            break;
                        case "§a§lВключить наклонённый шрифт":
                            font.put(player2, currentFont + "§o");
                            inv.setItem(0, Prepared.offItalic);
                            break;
                        case "§c§lВыключить жирный шрифт":
                            font.put(player2, currentFont.replace("§l", ""));
                            inv.setItem(1, Prepared.onBold);
                            break;
                        case "§a§lВключить жирный шрифт":
                            font.put(player2, currentFont + "§l");
                            inv.setItem(1, Prepared.offBold);
                            break;
                        case "§c§lВыключить подчёркнутый шрифт":
                            font.put(player2, currentFont.replace("§n", ""));
                            inv.setItem(2, Prepared.onUnderline);
                            break;
                        case "§a§lВключить подчёркнутый шрифт":
                            font.put(player2, currentFont + "§n");
                            inv.setItem(2, Prepared.offUnderline);
                            break;
                        case "§c§lВыключить зачёркнутый шрифт":
                            font.put(player2, currentFont.replace("§m", ""));
                            inv.setItem(3, Prepared.onStrikethrough);
                            break;
                        case "§a§lВключить зачёркнутый шрифт":
                            font.put(player2, currentFont + "§m");
                            inv.setItem(3, Prepared.offStrikethrough);
                            break;
                        case "§lНазад":
                            inv.setItem(0, null);
                            inv.setItem(1, null);
                            inv.setItem(2, null);
                            inv.setItem(8, null);
                            if (!msgColorEnable.get(player2)) {
                                inv.setItem(3, null);
                                inv.setItem(4, Prepared.enableCustomMessages);
                            } else {
                                inv.setItem(3, Prepared.disableCustomMessages);
                                inv.setItem(4, Prepared.colorSettings);
                                inv.setItem(5, Prepared.fontSettings);
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
                            msgColorIsRgb.put(player2, true);
                            player.openInventory(Utils.createRgbMenu(player, player2));
                            break;
                        default:
                            colorName.put(player2, item);
                            color.put(player2, item.substring(0, 2));
                            final ItemStack currentColor = preparedHeads.get(colorName.get(player)).clone();
                            final ItemMeta meta = currentColor.getItemMeta();
                            meta.setDisplayName("§f§lТекущий цвет:");
                            final List<String> loreList = new ArrayList<>();
                            loreList.add(meta.getLore().get(0));
                            meta.setLore(loreList);
                            currentColor.setItemMeta(meta);
                            inv.setItem(4, currentColor);
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
                            msgColorIsRgb.put(player2, false);
                            final String title;
                            if (player == player2) {
                                title = "§0§lНастройки сообщений";
                            } else {
                                title = "§0§lНастройки сообщений игрока " + player2.getName();
                            }
                            final Inventory menu = Bukkit.createInventory(player2, 36, title);
                            menu.setItem(0, Prepared.setRgb);
                            Utils.fillInventoryColors(menu, 18);
                            menu.setItem(8, Prepared.back);
                            final ItemStack currentColor = preparedHeads.get(colorName.get(player)).clone();
                            final ItemMeta meta = currentColor.getItemMeta();
                            meta.setDisplayName("§f§lТекущий цвет:");
                            final List<String> loreList = new ArrayList<>();
                            loreList.add(meta.getLore().get(0));
                            meta.setLore(loreList);
                            currentColor.setItemMeta(meta);
                            menu.setItem(4, currentColor);
                            menu.setMaxStackSize(88636);
                            player.openInventory(menu);
                            break;
                        case "§f§lПервый цвет":
                            final String color1Title;
                            if (player == player2) {
                                color1Title = "§0§lНастройки сообщений";
                            } else {
                                color1Title = "§0§lНастройки сообщений игрока " + player2.getName();
                            }
                            final Inventory color1Menu = Bukkit.createInventory(player2, 18, color1Title);
                            color1Menu.setMaxStackSize(88638);
                            Utils.fillInventoryColors(color1Menu, 0);
                            player.openInventory(color1Menu);
                            break;
                        case "§f§lВторой цвет":
                            final String color2Title;
                            if (player == player2) {
                                color2Title = "§0§lНастройки сообщений";
                            } else {
                                color2Title = "§0§lНастройки сообщений игрока " + player2.getName();
                            }
                            final Inventory color2Menu = Bukkit.createInventory(player2, 18, color2Title);
                            color2Menu.setMaxStackSize(88639);
                            Utils.fillInventoryColors(color2Menu, 0);
                            player.openInventory(color2Menu);
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
                    rgbColorName1.put(player2, item);
                    rgbColor1.put(player2, Utils.getRgbByColorName(item));
                    player.openInventory(Utils.createRgbMenu(player, player2));
                    break;
                case 88639:
                    event.setCancelled(true);
                    if (player2 == null || !players.contains(player2)) {
                        player2.closeInventory();
                        player2.sendMessage(playernotfoundmsg);
                        return;
                    }
                    rgbColorName2.put(player2, item);
                    rgbColor2.put(player2, Utils.getRgbByColorName(item));
                    player.openInventory(Utils.createRgbMenu(player, player2));
                    break;
            }
        } catch (Exception e) {}
    }
}
