package com.github.lunatrius.schematica.client.gui.shim;

import net.minecraft.src.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiUnicodeGlyphButton extends GuiButtonExt {
    public String glyph;
    public float glyphScale;

    public GuiUnicodeGlyphButton(int id, int xPos, int yPos, int width, int height, String displayString, String glyph, float glyphScale) {
        super(id, xPos, yPos, width, height, displayString);
        this.glyph = glyph;
        this.glyphScale = glyphScale;
    }

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
            int glyphWidth = (int) (mc.fontRenderer.getStringWidth(glyph) * glyphScale);
            int strWidth = mc.fontRenderer.getStringWidth(buttonText);
            int elipsisWidth = mc.fontRenderer.getStringWidth("...");
            int totalWidth = strWidth + glyphWidth;

            if (totalWidth > width - 6 && totalWidth > elipsisWidth)
                buttonText = mc.fontRenderer.trimStringToWidth(buttonText, width - 6 - elipsisWidth).trim() + "...";

            strWidth = mc.fontRenderer.getStringWidth(buttonText);
            totalWidth = glyphWidth + strWidth;

            GL11.glPushMatrix();
            GL11.glScalef(glyphScale, glyphScale, 1.0F);
            this.drawCenteredString(mc.fontRenderer, glyph,
                    (int) (((this.xPosition + (this.width / 2) - (strWidth / 2)) / glyphScale) - (glyphWidth / (2 * glyphScale)) + 2),
                    (int) (((this.yPosition + ((this.height - 8) / glyphScale) / 2) - 1) / glyphScale), color);
            GL11.glPopMatrix();

            this.drawCenteredString(mc.fontRenderer, buttonText, (int) (this.xPosition + (this.width / 2) + (glyphWidth / glyphScale)),
                    this.yPosition + (this.height - 8) / 2, color);
        }
    }
}