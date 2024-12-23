package com.github.lunatrius.schematica.client.gui.config;

import com.github.lunatrius.core.client.gui.GuiScreenBase;
import net.minecraft.src.GuiScreen;

public class GuiConfig extends GuiScreenBase {

    public GuiConfig(GuiScreen parent) {
        super(parent);

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return parentScreen.doesGuiPauseGame();
    }
}
