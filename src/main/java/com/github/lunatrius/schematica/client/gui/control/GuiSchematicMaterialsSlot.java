package com.github.lunatrius.schematica.client.gui.control;

import com.github.lunatrius.schematica.client.gui.GuiHelper;
import com.github.lunatrius.schematica.client.util.BlockList;
import com.github.lunatrius.schematica.reference.Names;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

import java.util.List;

class GuiSchematicMaterialsSlot extends GuiSlot {
    private final Minecraft minecraft = Minecraft.getMinecraft();

    private final GuiSchematicMaterials guiSchematicMaterials;

    private final String strMaterialRequired = I18n.getString(Names.Gui.Control.MATERIAL_REQUIRED);
    private final String strMaterialAvailable = I18n.getString(Names.Gui.Control.MATERIAL_AVAILABLE);

    protected int selectedIndex;

    public GuiSchematicMaterialsSlot(GuiSchematicMaterials par1) {
        super(Minecraft.getMinecraft(), par1.width, par1.height, 16, par1.height - 34, 25);
        this.guiSchematicMaterials = par1;
        this.selectedIndex = -1;
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
        return index == this.selectedIndex;
    }

    @Override
    protected void drawBackground() {
    }

//    @Override
//    protected void drawContainerBackground(Tessellator tessellator) {
//    }

    @Override
    protected void drawSlot(int index, int x, int y, int par4, Tessellator tessellator) {
        final BlockList.WrappedItemStack wrappedItemStack = this.guiSchematicMaterials.blockList.get(index);
        final ItemStack itemStack = wrappedItemStack.itemStack;

        final String itemName = wrappedItemStack.getItemStackDisplayName();
        final String amount = wrappedItemStack.getFormattedAmount();
        final String amountRequired = wrappedItemStack.getFormattedAmountRequired(strMaterialRequired, strMaterialAvailable);

        GuiHelper.drawItemStack(this.minecraft.renderEngine, this.minecraft.fontRenderer, x, y, itemStack);

        this.guiSchematicMaterials.drawString(this.minecraft.fontRenderer, itemName, x + 24, y + 6, 0xFFFFFF);
        int amountXWidth = this.minecraft.fontRenderer.getStringWidth(amount);
        this.guiSchematicMaterials.drawString(this.minecraft.fontRenderer, amount, x + 215 - amountXWidth, y + 6, 0xFFFFFF);
        int amountRequiredXWidth = this.minecraft.fontRenderer.getStringWidth(amountRequired);
        this.guiSchematicMaterials.drawString(this.minecraft.fontRenderer, amountRequired, x + 215 - amountRequiredXWidth, y + 16, 0xFFFFFF);

        if (mouseX > x + 215 - amountXWidth && mouseX <= x + 215 && mouseY > y + 2 && mouseY <= y + 2 + 9) {
            this.guiSchematicMaterials.renderTooltipList(List.of(wrappedItemStack.getFormattedAmountTooltip()), mouseX, mouseY);
            GL11.glDisable(GL11.GL_LIGHTING);
        }

        if (mouseX > x + 215 - amountRequiredXWidth && mouseX <= x + 215 && mouseY > y + 2 + 9 && mouseY <= y + 2 + 18) {
            this.guiSchematicMaterials.renderTooltipList(List.of(wrappedItemStack.getFormattedAmountRequiredTooltip(strMaterialRequired, strMaterialAvailable)), mouseX, mouseY);
            GL11.glDisable(GL11.GL_LIGHTING);
        }


        if (mouseX >= x + 215 - x && mouseY >= y && mouseX <= x + 18 && mouseY <= y + 18) {

            this.guiSchematicMaterials.renderToolTip(itemStack, mouseX, mouseY);
            GL11.glDisable(GL11.GL_LIGHTING);
        }
    }
}
