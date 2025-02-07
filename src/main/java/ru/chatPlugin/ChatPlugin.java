package ru.chatPlugin;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import ru.chatPlugin.Commands.Chatcolor;
import ru.chatPlugin.Commands.Chatplugin;

import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class ChatPlugin extends JavaPlugin implements Listener {

    public final static HashSet<String> onLoad = new HashSet<>();
    public final static HashSet<String> onSave = new HashSet<>();
    public final static HashSet<Player> players = new HashSet<>();
    public final static ConcurrentHashMap<Player, String> msgBeginGlobal = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, String> msgBeginLocal = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, Boolean> msgColorEnable = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, Boolean> msgColorIsRgb = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<String, String> colorCodes = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, String> color = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, ConcurrentHashMap<Character, Integer>> rgbColor1 = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, ConcurrentHashMap<Character, Integer>> rgbColor2 = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, String> colorName = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, String> rgbColorName1 = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, String> rgbColorName2 = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, String> font = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, Long> lastMsgTime = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, Long> saveTime = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, Collection<Player>> lastNearbyPlayers = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<String, ItemStack> preparedHeads = new ConcurrentHashMap<>();
    public final static ScheduledExecutorService exec = Executors.newScheduledThreadPool(6);
    public final static Random rand = new Random();
    public static JavaPlugin plugin;
    public static Chatcolor chatcolorExecutor;
    public static String configFolderPath = "";
    public static String playersDataFolder = "";
    public static boolean placeholderapisupport = false;
    public static boolean chatcolorcommandsupport = true;
    public static String globalmsgformat = "";
    public static String localmsgformat = "";
    public static int localchatradius = 0;
    public static boolean enablechatcooldown = false;
    public static int chatcooldown = 0;
    public static boolean allowedsymbolscheck = true;
    public static String allowedsymbols = "";
    public static String reloadmsg = "";
    public static String chatcolorusageconsolemsg = "";
    public static String chatcolorusageplayersmsg = "";
    public static String chatcoloroffmsg = "";
    public static String chatcoloroffothersmsg = "";
    public static String nopermsmsg = "";
    public static String playernotfoundmsg = "";
    public static String colornotfoundmsg = "";
    public static String helpmsg = "";
    public static String cooldownmsg = "";
    public static String colorsetothersmsg = "";
    public static String boldonothersmsg = "";
    public static String boldoffothersmsg = "";
    public static String italiconothersmsg = "";
    public static String italicoffothersmsg = "";
    public static String strikethroughonothersmsg = "";
    public static String strikethroughoffothersmsg = "";
    public static String underlineonothersmsg = "";
    public static String underlineoffothersmsg = "";
    public static String unallowedsymbolsmsg = "";

    @Override
    public void onEnable() {
        plugin = this;
        configFolderPath = plugin.getDataFolder().getAbsolutePath();
        playersDataFolder = configFolderPath + "/playerdata";
        Config.loadConfig();
        chatcolorExecutor = new Chatcolor();
        final PluginCommand chatcolorCommand = getCommand("chatcolor");
        final Chatplugin chatpluginExecutor = new Chatplugin();
        final PluginCommand chatpluginCommand = getCommand("chatplugin");
        chatcolorCommand.setExecutor(chatcolorExecutor);
        chatcolorCommand.setTabCompleter(chatcolorExecutor);
        chatpluginCommand.setExecutor(chatpluginExecutor);
        chatpluginCommand.setTabCompleter(chatpluginExecutor);
        getServer().getPluginManager().registerEvents(new ru.chatPlugin.Listener(), plugin);
    }

    @Override
    public void onDisable() {
        if (chatcolorcommandsupport) {
            try {
                for (Player player : players) {
                    final File playerData = new File(playersDataFolder + "/" + player.getName() + ".dat");
                    final ConcurrentHashMap<Character, Integer> rgb1 = rgbColor1.get(player);
                    final ConcurrentHashMap<Character, Integer> rgb2 = rgbColor2.get(player);
                    final String data = msgColorEnable.get(player) + "|" + msgColorIsRgb.get(player) + "|" + color.get(player) + "|" + rgb1.get('r') + "_" + rgb1.get('g') + "_" + rgb1.get('b') + "|" + rgb2.get('r') + "_" + rgb2.get('b') + "_" + rgb2.get('b') + "|" + font.get(player) + "|" + colorName.get(player) + "|" + rgbColorName1.get(player) + "|" + rgbColorName2.get(player) + "|";
                    final FileWriter writer = new FileWriter(playerData);
                    writer.write(data);
                    writer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}