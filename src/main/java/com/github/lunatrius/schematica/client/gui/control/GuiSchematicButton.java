package com.github.lunatrius.schematica.client.gui.control;

import com.github.lunatrius.schematica.handler.ConfigurationHandler;
import com.github.lunatrius.schematica.reference.Reference;
import com.github.lunatrius.schematica.util.ItemStackSortType;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.Minecraft;
import org.lwjgl.opengl.GL11;

public class GuiSchematicButton extends Gui {
    private OnClick onClick = (button, mouseX, mouseY) -> true;
    private final int xPosition, yPosition, width, height;
    private boolean hovering = false;
    private final String text;
    protected ItemStackSortType sortType;

    private static int IDS = 0;
    private final int id;

    private String glyph = "";
    private final float glyphScale;

    public GuiSchematicButton(Builder builder) {
        this(builder.xPos, builder.yPos, builder.width, builder.height, builder.text, builder.id, builder.glyphScale, builder.sortType);
                this.onClick = builder.onClick;
    }

    public GuiSchematicButton(int xPosition, int yPosition, int width, int height, String text) {
        this(xPosition, yPosition, width, height, text, 1.0F, ItemStackSortType.NONE);
    }

    protected GuiSchematicButton(int xPosition, int yPosition, int width, int height, String text, int id, float glyphScale, ItemStackSortType sortType) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.text = text;
        this.id = id;
        this.glyphScale = glyphScale;
        ItemStackSortType config  = ItemStackSortType.configValue();
        if (config.label.equals(sortType.label)) {
            this.sortType = config;
        }
        else
            this.sortType = sortType;
    }

    public GuiSchematicButton(int xPosition, int yPosition, int width, int height, String text, float glyphScale, ItemStackSortType type) {
        this(xPosition, yPosition, width, height, text, getNextId(), glyphScale, type);
    }

    private static int getNextId() {
        if (IDS > 100) IDS = 0;
        return IDS++;
    }

    public ItemStackSortType cycle() {
        this.sortType = this.sortType.cycle();
        ConfigurationHandler.propSortType.set(String.valueOf(this.sortType));
        ConfigurationHandler.loadConfiguration();
        return this.sortType;
    }

    public void resetToNone() {
        Reference.logger.debug("Resetting button with id {} and text {}", this.id, text);
        this.sortType = this.sortType.reset();
    }

    public void render(int mouseX, int mouseY) {
        Minecraft mc = Minecraft.getMinecraft();
        this.hovering = isHovering(mouseX, mouseY);
//        drawRectangle(xPosition, yPosition, width, height);
//        drawRect(xPosition, yPosition, xPosition + width, yPosition + height, 0xC418DC);
        drawGradientRect(xPosition, yPosition, xPosition + width, yPosition + height, 0x4444444f, 0xeeeeeeff);
        int color = 14737632;

        String buttonText = this.text;
        int glyphWidth = (int) (mc.fontRenderer.getStringWidth(ItemStackSortType.defaultGlyph()) * glyphScale);
        int strWidth = mc.fontRenderer.getStringWidth(buttonText);
        int elipsisWidth = mc.fontRenderer.getStringWidth("...");
        int totalWidth = strWidth + glyphWidth;

        if (totalWidth > width - 6 && totalWidth > elipsisWidth)
            buttonText = mc.fontRenderer.trimStringToWidth(buttonText, width - 6 - elipsisWidth).trim() + "...";

        GL11.glPushMatrix();
        GL11.glScalef(glyphScale, glyphScale, 1.0F);

        //glyph text, 2 is offset or smth idfk
        this.drawString(mc.fontRenderer, this.sortType.glyph, (int) (this.xPosition / glyphScale), (int) ((this.yPosition / glyphScale)), color);
        GL11.glPopMatrix();

        //button text
        this.drawString(mc.fontRenderer, buttonText, this.xPosition + (glyphWidth + 2), this.yPosition + 4, color);
    }

    public GuiSchematicButton onClick(OnClick onClick) {
        this.onClick = onClick;
        return this;
    }

    public boolean isHovering() {
        return hovering;
    }

    private boolean isHovering(int mouseX, int mouseY) {
        return mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    public boolean mousePressed(int mouseX, int mouseY) {
        if (isHovering() && onClick.onClick(this, mouseX, mouseY)) {

            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GuiSchematicButton button)) return false;
        return button.id == this.id;
    }

    public static class Builder {
        private final int id;
        private int xPos, yPos, width, height;
        private String text = "", glyph = "";
        private float glyphScale = 1.0F;
        private ItemStackSortType sortType = ItemStackSortType.NONE;
        private OnClick onClick = (button, mouseX, mouseY) -> true;

        public Builder() {
            this.id = getNextId();
        }

        public Builder glyph(String glyph, float glyphScale) {
            this.glyph = glyph;
            this.glyphScale = glyphScale;
            return this;
        }

        public Builder setScale(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder setPos(int xPos, int yPos) {
            this.xPos = xPos;
            this.yPos = yPos;
            return this;
        }

        public Builder onCLick(OnClick onClick) {
            this.onClick = onClick;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public GuiSchematicButton build() {
            return new GuiSchematicButton(this);
        }
    }

    public interface OnClick {
        /**
         * @return true if it should pass handling to actionPerformed
         * */
        boolean onClick(GuiSchematicButton button, int mouseX, int mouseY);
    }

    private interface Draw {

        void draw(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5);
    }
}
