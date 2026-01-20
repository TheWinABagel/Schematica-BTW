package com.github.lunatrius.schematica.util;

import net.fabricmc.example.mixin.GuiNewChatAccessor;
import net.minecraft.src.ChatLine;
import net.minecraft.src.Minecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ChatUtils {
    public static void printToChatWhileRemovingLast(String string, String lastMessageRegex) {
        List<ChatLine> lines = ((GuiNewChatAccessor)Minecraft.getMinecraft().ingameGUI.getChatGUI()).getChatLines();
//        var str = lines.get(0).getChatLineString();
        int lines1 = getMatchingIndex(lastMessageRegex.replace("\\", "\\\\"), lines);

        List<Integer> removed = new ArrayList<>();
        var test = string.split("\n");
//        int[] removed = new int[string.split("\n").length];
        if (lines1 != -1) {
            for (int i = lines1; i < string.split("\n").length && i < lines.size(); i++) {
                removed.add(lines.get(i).getChatLineID());
            }
            for (int remove: removed) {
                Minecraft.getMinecraft().ingameGUI.getChatGUI().deleteChatLine(remove);
            }
        }
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(string);
    }

    private static int getMatchingIndex(String lastMessage, List<ChatLine> lines) {
        try {
            Pattern pattern = Pattern.compile(lastMessage);
            for (int i = 0; i < lines.size(); i++) {
                if(pattern.matcher(lines.get(i).getChatLineString()).find())
                    return i;
            }
        }
        catch (PatternSyntaxException e) {
            System.err.println("Invalid last message pattern: " + lastMessage);
            return -1;
        }

        return -1;

    }

}
