package ru.chatPlugin;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import ru.chatPlugin.Commands.chatcolor;
import ru.chatPlugin.Commands.chatplugin;

import java.io.File;
import java.io.FileWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class ChatPlugin extends JavaPlugin implements Listener {

    public final static HashSet<Player> players = new HashSet<>();
    public final static ConcurrentHashMap<Player, String> msgbeginglobal = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, String> msgbeginlocal = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, Boolean> msgcolorenable = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, Boolean> msgcolorisrgb = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<String, String> colorcodes = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, String> color = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, ConcurrentHashMap<Character, Integer>> rgbcolor1 = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, ConcurrentHashMap<Character, Integer>> rgbcolor2 = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, String> colorname = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, String> rgbcolorname1 = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, String> rgbcolorname2 = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, String> font = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, Long> lastmsgtime = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, Long> savetime = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<Player, Collection<Player>> lastnearbyplayers = new ConcurrentHashMap<>();
    public final static ConcurrentHashMap<String, ItemStack> preparedheads = new ConcurrentHashMap<>();
    public final static ScheduledExecutorService exec = Executors.newScheduledThreadPool(3);
    public final static Random rand = new Random();
    public static JavaPlugin plugin;
    public static chatcolor chatcolorexecutor;
    public static String configfolderpath = "";
    public static String playersdatafolder = "";
    public static boolean placeholderapisupport = false;
    public static boolean chatcolorcommandsupport = true;
    public static String globalmsgformat = "";
    public static String localmsgformat = "";
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
        configfolderpath = plugin.getDataFolder().getAbsolutePath();
        playersdatafolder = configfolderpath + "/playerdata";
        Config.loadConfig();
        chatcolorexecutor = new chatcolor();
        final PluginCommand chatcolorcommand = getCommand("chatcolor");
        final chatplugin chatpluginexecutor = new chatplugin();
        final PluginCommand chatplugincommand = getCommand("chatplugin");
        chatcolorcommand.setExecutor(chatcolorexecutor);
        chatcolorcommand.setTabCompleter(chatcolorexecutor);
        chatplugincommand.setExecutor(chatpluginexecutor);
        chatplugincommand.setTabCompleter(chatpluginexecutor);
        getServer().getPluginManager().registerEvents(new ru.chatPlugin.Listener(), plugin);
    }

    @Override
    public void onDisable() {
        if (chatcolorcommandsupport) {
            for (Player player : players) {
                final File playerdata = new File(playersdatafolder + "/" + player.getName() + ".dat");
                try {
                    final ConcurrentHashMap<Character, Integer> rgb1 = rgbcolor1.get(player);
                    final ConcurrentHashMap<Character, Integer> rgb2 = rgbcolor2.get(player);
                    final String data = msgcolorenable.get(player) + "|" + msgcolorisrgb.get(player) + "|" + color.get(player) + "|" + rgb1.get('r') + "_" + rgb1.get('g') + "_" + rgb1.get('b') + "|" + rgb2.get('r') + "_" + rgb2.get('b') + "_" + rgb2.get('b') + "|" + font.get(player) + "|" + colorname.get(player) + "|" + rgbcolorname1.get(player) + "|" + rgbcolorname2.get(player) + "|";
                    final FileWriter writer = new FileWriter(playerdata);
                    writer.write(data);
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}