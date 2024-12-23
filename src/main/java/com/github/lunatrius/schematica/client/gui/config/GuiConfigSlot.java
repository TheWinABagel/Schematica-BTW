package com.github.lunatrius.schematica.client.gui.config;

import net.minecraft.src.GuiSlot;
import net.minecraft.src.Minecraft;
import net.minecraft.src.Tessellator;

public class GuiConfigSlot extends GuiSlot {

    private int selectedIndex;

    public GuiConfigSlot(GuiConfig configGui) {
        super(Minecraft.getMinecraft(), configGui.width, configGui.height, 16, configGui.height - 34, 25);
    }

    @Override
    protected int getSize() {
//        return this.guiSchematicMaterials.blockList.size();
    return 1;
    }

    @Override
    protected void elementClicked(int index, boolean bl) {
        this.selectedIndex = index;
    }

    @Override
    protected boolean isSelected(int index) {
        return index == this.selectedIndex;
    }

    @Override
    protected void drawBackground() {
    }

    @Override
    protected void drawSlot(int index, int x, int y, int par4, Tessellator tessellator) {

    }
}
