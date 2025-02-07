package ru.chatPlugin;

import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static ru.chatPlugin.ChatPlugin.*;

public class Config {

    public static void loadConfig() {
        final String defCfg = "placeholderapi-support: false\n" +
                "chatcolor-command-support: true\n" +
                "\n" +
                "global-msg-format: \"§8§l[§6§lG§8§l]§f %prefix%%nick%%suffix%§f: \"\n" +
                "local-msg-format: \"§8§l[§b§lL§8§l]§f %prefix%%nick%%suffix%§f: \"\n" +
                "\n" +
                "localchat-radius: 100\n" +
                "\n" +
                "enable-chat-cooldown: false\n" +
                "chat-cooldown: 1000\n" +
                "\n" +
                "allowed-symbols-check: false\n" +
                "allowed-symbols: \"abcdefghijklmnopqrstuvwxyzйцукенгшщзхъфывапролджэячсмитьбю1234567890~!@#№$;%^:&?*(){}[]'\\.\",/| ~ІіїЇЄє+=-_<> \"\n" +
                "\n" +
                "reload-msg: \"§a§lКонфигурация плагина перезагружена\"\n" +
                "chatcolor-usage-console-msg: \"§c§lИспользуйте /chatcolor <игрок> <цвет(reg/green/blue-yellow и тд)>\"\n" +
                "chatcolor-usage-players-msg: \"§c§lИспользуйте /chatcolor <игрок>\"\n" +
                "chatcoloroff-msg: \"§a§lВы выключили кастомный цвет сообщений\"\n" +
                "chatcoloroffothers-msg: \"§a§lВы выключили кастомный цвет сообщений для игрока %nick%\"\n" +
                "noperms-msg: \"§c§lУ вас нет прав не выполнение этой команды\"\n" +
                "playernotfound-msg: \"§c§lИгрок не найден\"\n" +
                "colornotfound-msg: \"§c§lТакой цвет не найден\"\n" +
                "chatplugin-help-msg: \"§b§lДопустимые команды:\\n§d§l/chatplugin reload §b§l- §e§lперезагрузить конфигурацию плагина\\n§d§l/chatplugin color <игрок> <цвет(reg/green/blue-yellow и тд)> §b§l- §e§lустановить цвет сообщений\"\n" +
                "cooldown-msg: \"§c§lТак блять, куда разогнался нахуй? Ты шо ебать, молния маквин епта?\"\n" +
                "colorsetothers-msg: \"§a§lВы успешно установили цвет сообщений для игрока %nick%\"\n" +
                "boldonothers-msg: \"§a§lВы успешно включили жирный шрифт для игрока %nick%\"\n" +
                "boldoffothers-msg: \"§a§lВы успешно выключили жирный шрифт для игрока %nick%\"\n" +
                "italiconothers-msg: \"§a§lВы успешно включили наклонный шрифт для игрока %nick%\"\n" +
                "italicoffothers-msg: \"§a§lВы успешно выключили наклонный шрифт для игрока %nick%\"\n" +
                "strikethroughonothers-msg: \"§a§lВы успешно включили зачёркнутый шрифт для игрока %nick%\"\n" +
                "strikethroughoffothers-msg: \"§a§lВы успешно выключили зачёркнутый шрифт для игрока %nick%\"\n" +
                "underlineonothers-msg: \"§a§lВы успешно включили подчёркнутый шрифт для игрока %nick%\"\n" +
                "underlineoffothers-msg: \"§a§lВы успешно выключили подчёркнутый шрифт для игрока %nick%\"\n" +
                "unallowedsymbols-msg: \"§c§lВ вашем сообщении есть неразрешённые символы\"";
        final File cfgDir = new File(configFolderPath);
        if (!cfgDir.exists()) {
            cfgDir.mkdir();
        }
        final File cfg = new File(configFolderPath + "/config.yml");
        try {
            if (!cfg.exists()) {
                cfg.createNewFile();
                final FileWriter writer = new FileWriter(cfg);
                writer.write(defCfg);
                writer.close();
            }
            processOptions(new String(Files.readAllBytes(Paths.get(cfg.getAbsolutePath()))).split("\n"));
        } catch (Exception e) {
            try {
                final String newName = "config-" + rand.nextInt(999999999) + ".yml";
                final File oldCfg = new File(configFolderPath + "/" + newName);
                oldCfg.createNewFile();
                FileWriter writer = new FileWriter(oldCfg);
                writer.write(new String(Files.readAllBytes(Paths.get(cfg.getAbsolutePath()))));
                writer.close();
                cfg.delete();
                cfg.createNewFile();
                writer = new FileWriter(cfg);
                writer.write(defCfg);
                writer.close();
                processOptions(new String(Files.readAllBytes(Paths.get(cfg.getAbsolutePath()))).split("\n"));
                Bukkit.getLogger().info("§cВо время загрузки конфигурации произошла ошибка, поэтому была загружена дефолтная конфигурация, ваша старая была сохранена в файл " + newName);
                Bukkit.getLogger().info("StackTrace:");
                e.printStackTrace();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        Utils.prepare();
    }

    static void processOptions(String[] options) {
        for (String option : options) {
            final int index = option.indexOf(": ");
            if (index == -1) continue;
            final String parameter = option.substring(0, index);
            final String value = option.substring(index + 2).replace("\"", "").replace("\\n", "\n").replace("&", "§");
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
                case "localchat-radius":
                    localchatradius = Integer.parseInt(value);
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
