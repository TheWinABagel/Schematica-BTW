package com.github.lunatrius.schematica.client.gui.config;

import com.github.lunatrius.schematica.client.gui.shim.GuiButtonExt;
import net.minecraft.src.Minecraft;

public class GuiButtonConfig extends GuiButtonExt {
    public static int ID = 17;
    public GuiButtonConfig(int id, int xPos, int yPos) {
        super(id, xPos, yPos, 20, 20, "C");
    }

    @Override
    public boolean mousePressed(Minecraft mc, int par2, int par3) {
        if (mc.thePlayer != null) {
            mc.thePlayer.sendChatMessage("hi");
        }
        return super.mousePressed(mc, par2, par3);
    }
}
