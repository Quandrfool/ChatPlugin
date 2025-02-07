package ru.chatPlugin;

import org.bukkit.inventory.ItemStack;

import static ru.chatPlugin.ChatPlugin.*;

public class Prepared {

    public static ItemStack redHead;
    public static ItemStack darkRedHead;
    public static ItemStack goldHead;
    public static ItemStack yellowHead;
    public static ItemStack greenHead;
    public static ItemStack darkGreenHead;
    public static ItemStack blueHead;
    public static ItemStack darkBlueHead;
    public static ItemStack lightPurpleHead;
    public static ItemStack darkPurpleHead;
    public static ItemStack blackHead;
    public static ItemStack grayHead;
    public static ItemStack darkGrayHead;
    public static ItemStack aquaHead;
    public static ItemStack darkAquaHead;
    public static ItemStack whiteHead;

    public static ItemStack back;
    public static ItemStack enableCustomMessages;
    public static ItemStack disableCustomMessages;
    public static ItemStack colorSettings;
    public static ItemStack fontSettings;
    public static ItemStack setRgb;
    public static ItemStack setNormal;
    public static ItemStack offBold;
    public static ItemStack onBold;
    public static ItemStack offItalic;
    public static ItemStack onItalic;
    public static ItemStack offUnderline;
    public static ItemStack onUnderline;
    public static ItemStack offStrikethrough;
    public static ItemStack onStrikethrough;

    public static void clear() {
        redHead = null;
        darkRedHead = null;
        goldHead = null;
        yellowHead = null;
        greenHead = null;
        darkGreenHead = null;
        blueHead = null;
        darkBlueHead = null;
        lightPurpleHead = null;
        darkPurpleHead = null;
        blackHead = null;
        grayHead = null;
        darkGrayHead = null;
        aquaHead = null;
        darkAquaHead = null;
        whiteHead = null;
        back = null;
        enableCustomMessages = null;
        disableCustomMessages = null;
        colorSettings = null;
        fontSettings = null;
        setRgb = null;
        setNormal = null;
        offBold = null;
        onBold = null;
        offItalic = null;
        onItalic = null;
        offUnderline = null;
        onUnderline = null;
        offStrikethrough = null;
        onStrikethrough = null;
        preparedHeads.clear();
        colorCodes.clear();
    }
}
