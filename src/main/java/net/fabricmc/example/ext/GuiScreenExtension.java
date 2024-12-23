package net.fabricmc.example.ext;

import net.minecraft.src.ItemStack;

import java.util.List;

public interface GuiScreenExtension {
    default void renderToolTip(ItemStack par1ItemStack, int mouseX, int mouseY) {
        throw new RuntimeException("uh oh. mixin borked!");
    }

    default void renderTooltipList(List<String> tooltip, int mouseX, int mouseY) {
        throw new RuntimeException("uh oh. mixin borked!");
    }
}
