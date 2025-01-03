package ru.chatPlugin;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static ru.chatPlugin.ChatPlugin.*;

public class Utils {

    public static boolean setFont(String fontstr, CommandSender sender, Player target) {
        final String currentfont = font.get(target);
        switch (fontstr) {
            case "italic":
                if (currentfont.contains("§o")) {
                    font.put(target, currentfont.replace("§o", ""));
                    sender.sendMessage(italicoffothersmsg.replace("%nick%", target.getName()));
                } else {
                    font.put(target, currentfont + "§o");
                    sender.sendMessage(italiconothersmsg.replace("%nick%", target.getName()));
                }
                return true;
            case "bold":
                if (currentfont.contains("§l")) {
                    font.put(target, currentfont.replace("§l", ""));
                    sender.sendMessage(boldoffothersmsg.replace("%nick%", target.getName()));
                } else {
                    font.put(target, currentfont + "§l");
                    sender.sendMessage(boldonothersmsg.replace("%nick%", target.getName()));
                }
                return true;
            case "underline":
                if (currentfont.contains("§n")) {
                    font.put(target, currentfont.replace("§n", ""));
                    sender.sendMessage(underlineoffothersmsg.replace("%nick%", target.getName()));
                } else {
                    font.put(target, currentfont + "§n");
                    sender.sendMessage(underlineonothersmsg.replace("%nick%", target.getName()));
                }
                return true;
            case "strikethrough":
                if (currentfont.contains("§m")) {
                    font.put(target, currentfont.replace("§m", ""));
                    sender.sendMessage(strikethroughoffothersmsg.replace("%nick%", target.getName()));
                } else {
                    font.put(target, currentfont + "§m");
                    sender.sendMessage(strikethroughonothersmsg.replace("%nick%", target.getName()));
                }
                return true;
            default:
                return false;
        }
    }

    public static boolean setRGB(String colorstr, CommandSender sender, Player target) {
        try {
            final String[] colors = colorstr.split("_");
            final String color1 = colors[0];
            final String color2 = colors[1];
            if (colorcodes.containsKey(color1) & colorcodes.containsKey(color2)) {
                rgbcolor1.put(target, getRGBByColorName(color1));
                rgbcolor2.put(target, getRGBByColorName(color2));
                sender.sendMessage(colorsetothersmsg.replace("%nick%", target.getName()));
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static void disablemsgcolor(Player player) {
        msgcolorenable.put(player, false);
        msgcolorisrgb.put(player, false);
        color.put(player, "");
        final ConcurrentHashMap<Character, Integer> rgb = getRGBByColorName("white");
        rgbcolor1.put(player, rgb);
        rgbcolor2.put(player, rgb);
        font.put(player, "");
        colorname.put(player, "§f§lБелый");
        rgbcolorname1.put(player, "§f§lБелый");
        rgbcolorname2.put(player, "§f§lБелый");
    }

    public static ConcurrentHashMap<Character, Integer> getRGBByColorName(String colorstr) {
        final ConcurrentHashMap<Character, Integer> rgb = new ConcurrentHashMap<>();
        switch (colorstr) {
            case "red", "§c§lКрасный":
                rgb.put('r', 255);
                rgb.put('g', 85);
                rgb.put('b', 85);
                return rgb;
            case "dark-red", "§4§lТёмно-красный":
                rgb.put('r', 170);
                rgb.put('g', 0);
                rgb.put('b', 0);
                return rgb;
            case "yellow", "§e§lЖёлтый":
                rgb.put('r', 255);
                rgb.put('g', 255);
                rgb.put('b', 85);
                return rgb;
            case "gold", "§6§lЗолотой":
                rgb.put('r', 255);
                rgb.put('g', 170);
                rgb.put('b', 0);
                return rgb;
            case "green", "§a§lЗелёный":
                rgb.put('r', 85);
                rgb.put('g', 255);
                rgb.put('b', 85);
                return rgb;
            case "dark-green", "§2§lТёмно-зелёный":
                rgb.put('r', 0);
                rgb.put('g', 170);
                rgb.put('b', 0);
                return rgb;
            case "blue", "§9§lГолубой":
                rgb.put('r', 85);
                rgb.put('g', 85);
                rgb.put('b', 255);
                return rgb;
            case "dark-blue", "§1§lСиний":
                rgb.put('r', 0);
                rgb.put('g', 0);
                rgb.put('b', 170);
                return rgb;
            case "light-purple", "§d§lСветло-пурпурный":
                rgb.put('r', 255);
                rgb.put('g', 85);
                rgb.put('b', 255);
                return rgb;
            case "dark-purple", "§5§lТёмно-пурпурный":
                rgb.put('r', 170);
                rgb.put('g', 0);
                rgb.put('b', 170);
                return rgb;
            case "black", "§0§lЧёрный":
                rgb.put('r', 0);
                rgb.put('g', 0);
                rgb.put('b', 0);
                return rgb;
            case "gray", "§7§lСерый":
                rgb.put('r', 170);
                rgb.put('g', 170);
                rgb.put('b', 170);
                return rgb;
            case "dark-gray", "§8§lТёмно-серый":
                rgb.put('r', 85);
                rgb.put('g', 85);
                rgb.put('b', 85);
                return rgb;
            case "aqua", "§b§lАквамариновый":
                rgb.put('r', 85);
                rgb.put('g', 255);
                rgb.put('b', 255);
                return rgb;
            case "dark-aqua", "§3§lТёмно-аквамариновый":
                rgb.put('r', 0);
                rgb.put('g', 170);
                rgb.put('b', 170);
                return rgb;
            case "white", "§f§lБелый":
                rgb.put('r', 255);
                rgb.put('g', 255);
                rgb.put('b', 255);
                return rgb;
            default:
                return null;
        }
    }

    private static String getColorByRGB(int r, int g, int b) {
        return ChatColor.of(String.format("#%02x%02x%02x", r, g, b)) + "";
    }

    public static String colorizemsg(String msg, Player player) {
        if (!chatcolorcommandsupport) return msg;
        if (!msgcolorenable.get(player)) return msg;
        if (msgcolorisrgb.get(player)) {
            final ConcurrentHashMap<Character, Integer> startrgb = rgbcolor1.get(player);
            final ConcurrentHashMap<Character, Integer> endrgb = rgbcolor2.get(player);
            final int startr = startrgb.get('r');
            final int startg = startrgb.get('g');
            final int startb = startrgb.get('b');
            final int endr = endrgb.get('r');
            final int endg = endrgb.get('g');
            final int endb = endrgb.get('b');
            final int divider = msg.length() - 1;
            if (divider < 1) {
                return getColorByRGB(startr, startg, startb) + font.get(player) + msg;
            }
            final int[] r = new int[msg.length()];
            r[0] = startr;
            if (startr == endr) {
                for (int i = 1; i < msg.length(); i++) {
                    r[i] = startr;
                }
            } else if (startr < endr) {
                final int step = (endr - startr) / divider;
                for (int i = 1; i < msg.length(); i++) {
                    r[i] = r[i-1] + step;
                }
            } else {
                final int step = (startr - endr) / divider;
                for (int i = 1; i < msg.length(); i++) {
                    r[i] = r[i-1] - step;
                }
            }
            final int[] g = new int[msg.length()];
            g[0] = startg;
            if (startg == endg) {
                for (int i = 1; i < msg.length(); i++) {
                    g[i] = startg;
                }
            } else if (startg < endg) {
                final int step = (endg - startg) / divider;
                for (int i = 1; i < msg.length(); i++) {
                    g[i] = g[i-1] + step;
                }
            } else {
                final int step = (startg - endg) / divider;
                for (int i = 1; i < msg.length(); i++) {
                    g[i] = g[i-1] - step;
                }
            }
            final int[] b = new int[msg.length()];
            b[0] = startb;
            if (startb == endb) {
                for (int i = 1; i < msg.length(); i++) {
                    b[i] = startb;
                }
            } else if (startb < endb) {
                final int step = (endb - startb) / divider;
                for (int i = 1; i < msg.length(); i++) {
                    b[i] = b[i-1] + step;
                }
            } else {
                final int step = (startb - endb) / divider;
                for (int i = 1; i < msg.length(); i++) {
                    b[i] = b[i-1] - step;
                }
            }
            final String pfont = font.get(player);
            final char[] array = msg.toCharArray();
            final StringBuilder newmsg = new StringBuilder();
            for (int i = 0; i < msg.length(); i++) {
                newmsg.append(getColorByRGB(r[i], g[i], b[i]) + pfont + array[i]);
            }
            return newmsg.toString();
        } else {
            return color.get(player) + font.get(player) + msg;
        }
    }

    public static void openSettings(Player player1, Player player2) {
        final String title;
        if (player1 == player2) {
            title = "§0§lНастройки сообщений";
        } else {
            title = "§0§lНастройки сообщений игрока " + player2.getName();
        }
        final Inventory menu = Bukkit.createInventory(player2, 9, title);
        menu.setMaxStackSize(88634);
        if (!msgcolorenable.get(player2)) {
            menu.setItem(4, Prepared.enablecustommessages);
        } else {
            menu.setItem(3, Prepared.disablecustommessages);
            menu.setItem(4, Prepared.colorsettings);
            menu.setItem(5, Prepared.fontsettings);
        }
        player1.openInventory(menu);
    }

    public static ItemStack createItem(Material material, String lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        final List<String> lorelist = new ArrayList<>();
        for (String str : lore.split("\n")) {
            lorelist.add(str);
        }
        lorelist.add(" ");
        meta.setLore(lorelist);
        item.setItemMeta(meta);
        return item;
    }

    public static Inventory createRGBMenu(Player player1, Player player2) {
        final String title;
        if (player1 == player2) {
            title = "§0§lНастройки сообщений";
        } else {
            title = "§0§lНастройки сообщений игрока " + player2.getName();
        }
        final Inventory menu = Bukkit.createInventory(player2, 9, title);
        menu.setItem(0, Prepared.setnormal);
        menu.setItem(8, Prepared.back);
        final ItemStack color1 = preparedheads.get(rgbcolorname1.get(player2)).clone();
        final ItemMeta meta1 = color1.getItemMeta();
        final List<String> lorelist1 = new ArrayList<>();
        lorelist1.add("§f§lПервый цвет");
        lorelist1.add(" ");
        meta1.setLore(lorelist1);
        color1.setItemMeta(meta1);
        final ItemStack color2 = preparedheads.get(rgbcolorname2.get(player2)).clone();
        final ItemMeta meta2 = color2.getItemMeta();
        final List<String> lorelist2 = new ArrayList<>();
        lorelist2.add("§f§lВторой цвет");
        lorelist2.add(" ");
        meta2.setLore(lorelist2);
        color2.setItemMeta(meta2);
        menu.setItem(3, color1);
        menu.setItem(5, color2);
        menu.setMaxStackSize(88637);
        return menu;
    }

    public static void fillInventoryColors(Inventory inv, int startindex) {
        inv.setItem(startindex, Prepared.redhead);
        startindex++;
        inv.setItem(startindex, Prepared.darkredhead);
        startindex++;
        inv.setItem(startindex, Prepared.goldhead);
        startindex++;
        inv.setItem(startindex, Prepared.yellowhead);
        startindex++;
        inv.setItem(startindex, Prepared.greenhead);
        startindex++;
        inv.setItem(startindex, Prepared.darkgreenhead);
        startindex++;
        inv.setItem(startindex, Prepared.bluehead);
        startindex++;
        inv.setItem(startindex, Prepared.darkbluehead);
        startindex++;
        inv.setItem(startindex, Prepared.lightpurplehead);
        startindex = startindex + 2;
        inv.setItem(startindex, Prepared.darkpurplehead);
        startindex++;
        inv.setItem(startindex, Prepared.blackhead);
        startindex++;
        inv.setItem(startindex, Prepared.grayhead);
        startindex++;
        inv.setItem(startindex, Prepared.darkgrayhead);
        startindex++;
        inv.setItem(startindex, Prepared.aquahead);
        startindex++;
        inv.setItem(startindex, Prepared.darkaquahead);
        startindex++;
        inv.setItem(startindex, Prepared.whitehead);
    }

    public static ItemStack prepareHead(String colorstr, String base64) {
        final ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        final SkullMeta meta = (SkullMeta) item.getItemMeta();
        final PlayerProfile profile = Bukkit.createProfile("");
        profile.setProperty(new ProfileProperty("textures", base64));
        meta.setPlayerProfile(profile);
        meta.setDisplayName(" ");
        final List<String> lorelist = new ArrayList<>();
        lorelist.add(colorstr);
        lorelist.add(" ");
        meta.setLore(lorelist);
        item.setItemMeta(meta);
        preparedheads.put(colorstr, item);
        return item;
    }

    public static void prepare() {
        if (chatcolorcommandsupport) {
            final File playerdatafolderf = new File(playersdatafolder);
            if (!playerdatafolderf.exists()) playerdatafolderf.mkdir();
            Prepared.redhead = prepareHead("§c§lКрасный", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWMxNDYwMGFjZTUwNjk1YzdjOWJjZjA5ZTQyYWZkOWY1M2M5ZTIwZGFhMTUyNGM5NWRiNDE5N2RkMzExNjQxMiJ9fX0=");
            Prepared.darkredhead = prepareHead("§4§lТёмно-красный", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjhkNDA5MzUyNzk3NzFhZGM2MzkzNmVkOWM4NDYzYWJkZjVjNWJhNzhkMmU4NmNiMWVjMTBiNGQxZDIyNWZiIn19fQ==");
            Prepared.goldhead = prepareHead("§6§lЗолотой", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjA5MGQwOWUxNzNlZTM0MTM4YzNiMDFiNDhlZTBiZTUzNGJiYjFhY2UwZGRmNWZmOThlNjZmN2IwMjExMzk5NSJ9fX0=");
            Prepared.yellowhead = prepareHead("§e§lЖёлтый", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTg5YjkzZmQ2MTZlZDM2NzBjY2Y2NDdhMGY5MzgwMzk4YzBkNDYxNTYzNGYyZGVmZjQ2YzZlZGJkYzcxMjg4NSJ9fX0=");
            Prepared.greenhead = prepareHead("§a§lЗелёный", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTI1YjhlZWQ1YzU2NWJkNDQwZWM0N2M3OWMyMGQ1Y2YzNzAxNjJiMWQ5YjVkZDMxMDBlZDYyODNmZTAxZDZlIn19fQ==");
            Prepared.darkgreenhead = prepareHead("§2§lТёмно-зелёный", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWUzOGZhMzEzMWUyZGEwNGE4ZjhkNTA4NzJhODIzMmQ3ZDlkZWEzNDBkMDZjOTA5N2ZmYTNjYzQ4MjA4ZGYxZCJ9fX0=");
            Prepared.bluehead = prepareHead("§9§lГолубой", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjEwZTM3NGNkYzJiYTk1YmI3MmYxYTAzNmM3N2RhMzUwOTkzNWExYWJkMjRiNjhjNmIzNTkxNjkwYjEwM2ZlZCJ9fX0=");
            Prepared.darkbluehead = prepareHead("§1§lСиний", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTExOTRmZTllZGY1ODNjMGViZTdkYzFkMzQzMDliZWVmYjIyOWJiMTViNmE4YzNjN2IwYzc2ZGUyN2M4YjdiZiJ9fX0=");
            Prepared.lightpurplehead = prepareHead("§d§lСветло-пурпурный", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGNmMjgzNTE4MGNiZmVjM2IzMTdkNmE0NzQ5MWE3NGFlNzE0MzViYTE2OWE1NzkyNWI5MDk2ZWEyZjljNjFiNiJ9fX0=");
            Prepared.darkpurplehead = prepareHead("§5§lТёмно-пурпурный", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWI4MmU3MmI4ZTQ4MzJlNWExMTRhYjBmYzEyN2M4YWNiODNmMzFmZDRkMjY2ZDA4YjJjYWNjNWI2NDAxYTQwMCJ9fX0=");
            Prepared.blackhead = prepareHead("§0§lЧёрный", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmE1MmQ1NzlhZmUyZmRmN2I4ZWNmYTc0NmNkMDE2MTUwZDk2YmViNzUwMDliYjI3MzNhZGUxNWQ0ODdjNDJhMSJ9fX0=");
            Prepared.grayhead = prepareHead("§7§lСерый", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjFhMWUyNDk0MjdmNjA5YWZiZjI0ZTJlODI2Y2EwYjQxMGE1NmNjM2MwOWI1NmVkNjkwYTk5OWE5MDQ2MDI0YyJ9fX0=");
            Prepared.darkgrayhead = prepareHead("§8§lТёмно-серый", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzNmNTU1ODdlNjlhNDlkZGY5ZDdkNGEzZDFmY2NlMzg4ZWE3NTFkZTdhMDE5NjM5NzdhZjU1NWQ5YjNiMTJkZCJ9fX0=");
            Prepared.aquahead = prepareHead("§b§lАквамариновый", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmQ2YThiNDdkYTkyM2I3ZDEwMTQyNDQ3ZmRiZGNmZDFlOGU4MmViNDg0OTY0MjUyYmIzNmRkYjVmNzNiNTFjMiJ9fX0=");
            Prepared.darkaquahead = prepareHead("§3§lТёмно-аквамариновый", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzFmNTcwNTExMzBlODUwODQ4ZThlMzdlNzIxMTBhMTZmMDlkYmRhYjdkOWQ2ZTMzYTlmZWNmZDM0OGQ1YTExMCJ9fX0=");
            Prepared.whitehead = prepareHead("§f§lБелый", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWE3NzRkYWRiNGJhNDQyZTBlMjhmNWZmYmQ2MzQxZTZjNzFhNzdkMTU3NDU1OTM0NzE4MWRiNjE0MzA4YWUyNSJ9fX0=");
            Prepared.back = createItem(Material.ARROW, "§lНазад");
            Prepared.enablecustommessages = createItem(Material.RED_WOOL, "§a§lВключить кастомные сообщения");
            Prepared.disablecustommessages = createItem(Material.LIME_WOOL, "§c§lВыключить кастомные сообщения");
            Prepared.colorsettings = createItem(Material.ORANGE_BANNER, "§a§lНастройки цвета");
            Prepared.fontsettings = createItem(Material.OAK_SIGN, "§a§lНастройки шрифта");
            Prepared.setrgb = createItem(Material.TRIPWIRE_HOOK, "§lПоменять тип цвета на градиент");
            Prepared.setnormal = createItem(Material.TRIPWIRE_HOOK, "§lПоменять тип цвета обычный");
            Prepared.offbold = createItem(Material.LIME_WOOL, "§c§lВыключить жирный шрифт");
            Prepared.onbold = createItem(Material.RED_WOOL, "§a§lВключить жирный шрифт");
            Prepared.offitalic = createItem(Material.LIME_WOOL, "§c§lВыключить наклонённый шрифт");
            Prepared.onitalic = createItem(Material.RED_WOOL, "§a§lВключить наклонённый шрифт");
            Prepared.offunderline = createItem(Material.LIME_WOOL, "§c§lВыключить подчёркнутый шрифт");
            Prepared.onunderline = createItem(Material.RED_WOOL, "§a§lВключить подчёркнутый шрифт");
            Prepared.offstrikethrough = createItem(Material.LIME_WOOL, "§c§lВыключить зачёркнутый шрифт");
            Prepared.onstrikethrough = createItem(Material.RED_WOOL, "§a§lВключить зачёркнутый шрифт");
            colorcodes.put("red", "§c");
            colorcodes.put("dark-red", "§4");
            colorcodes.put("gold", "§6");
            colorcodes.put("yellow", "§e");
            colorcodes.put("green", "§a");
            colorcodes.put("dark-green", "§2");
            colorcodes.put("blue", "§9");
            colorcodes.put("dark-blue", "§1");
            colorcodes.put("light-purple", "§d");
            colorcodes.put("dark-purple", "§5");
            colorcodes.put("black", "§0");
            colorcodes.put("gray", "§7");
            colorcodes.put("dark-gray", "§8");
            colorcodes.put("aqua", "§b");
            colorcodes.put("dark-aqua", "§3");
            colorcodes.put("white", "§f");
        } else {
            Prepared.clear();
        }
    }
}