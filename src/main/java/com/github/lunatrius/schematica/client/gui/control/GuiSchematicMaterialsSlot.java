package com.github.lunatrius.schematica.client.gui.control;

import com.github.lunatrius.schematica.client.gui.GuiHelper;
import com.github.lunatrius.schematica.client.util.BlockList;
import com.github.lunatrius.schematica.reference.Names;
import com.github.lunatrius.schematica.util.MaterialTooltipComponent;
import emi.dev.emi.emi.EmiRenderHelper;
import emi.dev.emi.emi.runtime.EmiDrawContext;
import emi.shims.java.net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
//todo look at GuiStats for improvements
class GuiSchematicMaterialsSlot extends GuiSlot {
    private final Minecraft minecraft = Minecraft.getMinecraft();
    private final GuiSchematicMaterials guiSchematicMaterials;

    private final String strMaterialRequired = I18n.getString(Names.Gui.Control.MATERIAL_REQUIRED);
    private final String strMaterialAvailable = I18n.getString(Names.Gui.Control.MATERIAL_AVAILABLE);

    private final String strMaterialName = I18n.getString(Names.Gui.Control.MATERIAL_NAME);
    private final String strMaterialTotal = I18n.getString(Names.Gui.Control.MATERIAL_TOTAL);
    private final String strMaterialMissing = I18n.getString(Names.Gui.Control.MATERIAL_MISSING);

    protected int selectedIndex;
    protected int topOffset;

    private final int NAME_START = 24;
    private final int TOTAL_AMOUNT_START = 180;
    private final int MISSING_START = 240;
    private final int AVAILABLE_START = 300;

    public GuiSchematicMaterialsSlot(GuiSchematicMaterials par1) {
        super(Minecraft.getMinecraft(), par1.width, par1.height, 16, par1.height - 34, 21);
        this.guiSchematicMaterials = par1;
        this.selectedIndex = -1;
        this.topOffset = 0;
        this.func_77223_a(true, topOffset);
//        this.left -= 100;
    }

    @Override
    protected int getSize() {
        return this.guiSchematicMaterials.blockList.size();
    }

    @Override
    protected void elementClicked(int index, boolean par2) {
        this.selectedIndex = index;
    }

    @Override
    protected boolean isSelected(int index) {
        return false;
    }

    @Override
    protected void func_77222_a(int x, int yOffset, Tessellator tessellator) {
        x = 0;
        int xOff = this.guiSchematicMaterials.width;
        final int height = this.slotHeight;
        int z = 0;
        for (int i = 0; i < getSize(); i += 2) {
            int y = yOffset + i * height + this.topOffset;
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA(100, 100, 100, 50);
            tessellator.addVertexWithUV(x, y, z, 0, 0);
            tessellator.addVertexWithUV(x, y + height, z, 0, 0);
            tessellator.addVertexWithUV(x + xOff, y + height, z, 0, 0);
            tessellator.addVertexWithUV(x + xOff, y, z, 0, 0);
            tessellator.draw();
        }
    }

    @Override
    protected void drawBackground() {}

    @Override
    public void overlayBackground(int bottom, int height, int alpha1, int alpha2) {
        super.overlayBackground(bottom, height, alpha1, alpha2);
        if (bottom == this.bottom && height == this.guiSchematicMaterials.height) {
            int xStart = 4;
            GuiSchematicMaterials gui = this.guiSchematicMaterials;
            FontRenderer fr = this.minecraft.fontRenderer;
            gui.drawString(fr, this.strMaterialName, xStart, 4, 0x00FFFFFF);
            gui.drawString(fr, this.strMaterialTotal, xStart + TOTAL_AMOUNT_START, 4, 0x00FFFFFF);

            gui.drawString(fr, this.strMaterialMissing, xStart + MISSING_START, 4, 0x00FFFFFF);

            gui.drawString(fr, this.strMaterialAvailable, xStart + AVAILABLE_START, 4, 0x00FFFFFF);
        }
    }

    @Override
    protected void drawSlot(int index, int x, int y, int height, Tessellator tessellator) {
        final BlockList.WrappedItemStack wrappedItemStack = this.guiSchematicMaterials.blockList.get(index);
        final ItemStack itemStack = wrappedItemStack.itemStack;

        final String itemName = wrappedItemStack.getItemStackDisplayName();
        final String total = wrappedItemStack.getTotal();
        final String missing = wrappedItemStack.getMissing();
        final String available = wrappedItemStack.getAvailable();
        int realX = 4;

        GuiHelper.drawItemStack(this.minecraft.renderEngine, this.minecraft.fontRenderer, realX, y, itemStack);
        y += 6;
        this.guiSchematicMaterials.drawString(this.minecraft.fontRenderer, itemName, realX + NAME_START, y, 0xFFFFFF);
        this.guiSchematicMaterials.drawString(this.minecraft.fontRenderer, total, realX + TOTAL_AMOUNT_START, y, 0xFFFFFF);
        this.guiSchematicMaterials.drawString(this.minecraft.fontRenderer, missing, realX + MISSING_START, y, 0xFFFFFF);
        this.guiSchematicMaterials.drawString(this.minecraft.fontRenderer, available, realX + AVAILABLE_START, y, 0xFFFFFF);
    }

    /**
     * Render tooltips
     * */
    @Override
    protected void func_77215_b(int mouseX, int mouseY) {
        int yOffset = this.top + 4 - (int) this.amountScrolled;
        for (int idx = 0; idx < this.getSize(); ++idx) {
            int y = yOffset + idx * this.slotHeight/* + this.field_77242_t*/;
            int n2 = this.slotHeight - 4;
            if (y > this.bottom || y + n2 < this.top) continue;
            final BlockList.WrappedItemStack wrappedItemStack = this.guiSchematicMaterials.blockList.get(idx);

            //Required
            if (mouseX > 4 && mouseX <= this.guiSchematicMaterials.width - 20 && mouseY > y + 2 && mouseY <= y + 2 + this.slotHeight) {
//                List<String> tooltip = new ArrayList<>();
//                tooltip.add("Item:      " + wrappedItemStack.getItemStackDisplayName());
//                tooltip.add("Total:     " + wrappedItemStack.calculateTotal());
//                tooltip.add("Missing:  " + wrappedItemStack.getMissingNoColor());
//                this.guiSchematicMaterials.renderTooltipList(tooltip, mouseX, mouseY);
//                GL11.glEnable(GL11.GL_LIGHTING);
                List<TooltipComponent> comps = new ArrayList<>();
//                comps.add(TooltipComponent.of(Text.literal("haha")));
                comps.add(new MaterialTooltipComponent(wrappedItemStack));
                EmiRenderHelper.drawTooltip(this.guiSchematicMaterials, EmiDrawContext.instance(), comps, this.mouseX, this.mouseY, this.guiSchematicMaterials.width / 2);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
            }
        }

    }
    private boolean notShown = true;
    @Override
    protected int getScrollBarX() {
        var sr = new ScaledResolution(minecraft.gameSettings, minecraft.displayWidth, minecraft.displayHeight);
        int amount = sr.getScaledWidth() - 10;
        if (notShown) {
            System.out.println("CURRENT WIDTH is " + amount);
            notShown = false;
        }

        return amount;
//        return this.guiSchematicMaterials.width - 100;
    }
}
