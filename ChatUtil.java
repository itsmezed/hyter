package com.implementable.test.utilities;

import net.md_5.bungee.api.ChatColor;

/*
 * Developed by Implementable 2019/08/29
 */
public class ChatUtil {
    public static String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}