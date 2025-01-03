package ru.chatPlugin;

import com.destroystokyo.paper.utils.PaperPluginLogger;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static ru.chatPlugin.ChatPlugin.*;

public class Config {

    public static void loadConfig() {
        final File cfgdir = new File(configfolderpath);
        if (!cfgdir.exists()) {
            cfgdir.mkdir();
        }
        final File cfg = new File(configfolderpath + "/config.yml");
        try {
            if (!cfg.exists()) {
                cfg.createNewFile();
                final FileWriter writer = new FileWriter(cfg);
                writer.write(defcfg);
                writer.close();
            }
            processOptions(new String(Files.readAllBytes(Paths.get(cfg.getAbsolutePath()))).split("\n"));
        } catch (Exception e) {
            try {
                final String newname = "config-" + rand.nextInt(999999999) + ".yml";
                final File oldcfg = new File(configfolderpath + "/" + newname);
                oldcfg.createNewFile();
                FileWriter writer = new FileWriter(oldcfg);
                writer.write(new String(Files.readAllBytes(Paths.get(cfg.getAbsolutePath()))));
                writer.close();
                cfg.delete();
                cfg.createNewFile();
                writer = new FileWriter(cfg);
                writer.write(defcfg);
                writer.close();
                processOptions(new String(Files.readAllBytes(Paths.get(cfg.getAbsolutePath()))).split("\n"));
                Bukkit.getLogger().info("§cВо время загрузки конфигурации произошла ошибка, поэтому была загружена дефолтная конфигурация, ваша старая была сохранена в файл " + newname);
                Bukkit.getLogger().info("StackTrace:");
                e.printStackTrace();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        Utils.prepare();
    }

    public static void processOptions(String[] options) {
        for (String option : options) {
            final int index = option.indexOf(": ");
            if (index == -1) continue;
            final String parameter = option.substring(0, index);
            String value = option.substring(index + 2);
            value = value.replace("\"", "");
            value = value.replace("\\n", "\n");
            value = value.replace("&", "§");
            switch (parameter) {
                case "placeholderapi-support":
                    placeholderapisupport = Boolean.parseBoolean(value);
                    break;
                case "chatcolor-command-support":
                    chatcolorcommandsupport = Boolean.parseBoolean(value);
                    break;
                case "global-msg-format":
                    globalmsgformat = value;
                    break;
                case "local-msg-format":
                    localmsgformat = value;
                    break;
                case "enable-chat-cooldown":
                    enablechatcooldown = Boolean.parseBoolean(value);
                    break;
                case "chat-cooldown":
                    chatcooldown = Integer.parseInt(value);
                    break;
                case "reload-msg":
                    reloadmsg = value;
                    break;
                case "chatcolor-usage-console-msg":
                    chatcolorusageconsolemsg = value;
                    break;
                case "chatcolor-usage-players-msg":
                    chatcolorusageplayersmsg = value;
                    break;
                case "chatcoloroff-msg":
                    chatcoloroffmsg = value;
                    break;
                case "chatcoloroffothers-msg":
                    chatcoloroffothersmsg = value;
                    break;
                case "noperms-msg":
                    nopermsmsg = value;
                    break;
                case "playernotfound-msg":
                    playernotfoundmsg = value;
                    break;
                case "colornotfound-msg":
                    colornotfoundmsg = value;
                    break;
                case "chatplugin-help-msg":
                    helpmsg = value;
                    break;
                case "cooldown-msg":
                    cooldownmsg = value;
                    break;
                case "colorsetothers-msg":
                    colorsetothersmsg = value;
                    break;
                case "boldonothers-msg":
                    boldonothersmsg = value;
                    break;
                case "boldoffothers-msg":
                    boldoffothersmsg = value;
                    break;
                case "italiconothers-msg":
                    italiconothersmsg = value;
                    break;
                case "italicoffothers-msg":
                    italicoffothersmsg = value;
                    break;
                case "strikethroughonothers-msg":
                    strikethroughonothersmsg = value;
                    break;
                case "strikethroughoffothers-msg":
                    strikethroughoffothersmsg = value;
                    break;
                case "underlineonothers-msg":
                    underlineonothersmsg = value;
                    break;
                case "underlineoffothers-msg":
                    underlineoffothersmsg = value;
                    break;
                case "allowed-symbols-check":
                    allowedsymbolscheck = Boolean.parseBoolean(value);
                case "allowed-symbols":
                    final String optionss = option.substring(index + 2);
                    allowedsymbols = optionss.substring(1, optionss.length() - 1);
                case "unallowedsymbols-msg":
                    unallowedsymbolsmsg = value;
            }
        }
    }
}
