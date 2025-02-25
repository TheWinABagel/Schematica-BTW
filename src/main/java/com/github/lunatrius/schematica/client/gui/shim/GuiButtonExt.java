package com.github.lunatrius.schematica.client.gui.shim;

import com.github.lunatrius.schematica.reference.Reference;
import net.minecraft.src.GuiButton;
import net.minecraft.src.Minecraft;

public class GuiButtonExt extends GuiButton {
    private OnClick onClick = (but, a, b) -> true;

    public GuiButtonExt(int id, int xPos, int yPos, int width, int height, String displayString) {
        super(id, xPos, yPos, width, height, displayString);
    }

    /**
     * Draws this button to the screen.
     */
    /**
     * Draws this button to the screen.
     */
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.drawButton) {
            this.field_82253_i = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int k = this.getHoverState(this.field_82253_i);
            GuiUtils.drawContinuousTexturedBox(buttonTextures, this.xPosition, this.yPosition, 0, 46 + k * 20, this.width, this.height, 200, 20, 2, 3, 2, 2, this.zLevel);
            this.mouseDragged(mc, mouseX, mouseY);
            int color = 14737632;

            if (!this.enabled) {
                color = 10526880;
            } else if (this.field_82253_i) {
                color = 16777120;
            }

            String buttonText = this.displayString;
            int strWidth = mc.fontRenderer.getStringWidth(buttonText);
            int ellipsisWidth = mc.fontRenderer.getStringWidth("...");

            if (strWidth > width - 6 && strWidth > ellipsisWidth)
                buttonText = mc.fontRenderer.trimStringToWidth(buttonText, width - 6 - ellipsisWidth).trim() + "...";

            this.drawCenteredString(mc.fontRenderer, buttonText, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, color);
        }
    }

    public GuiButtonExt onClick(OnClick onClick) {
        this.onClick = onClick;
        return this;
    }

    public boolean isHovering() {
        return func_82252_a();
    }

    @Override
    public boolean mousePressed(Minecraft par1Minecraft, int mouseX, int mouseY) {
        boolean spr = this.enabled && this.drawButton && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        Reference.logger.info("Mouse pressed id {}, super says {}, current x,y ({},{}) current btn pos ({}, {})", this.id, spr, mouseX, mouseY, xPosition,yPosition);
        return spr && onClick.onClick(this, mouseX, mouseY);
    }

    public interface OnClick {
        /**
         * @return true if it should pass handling to actionPerformed
         * */
        boolean onClick(GuiButtonExt button, int mouseX, int mouseY);
    }
}