package com.github.lunatrius.schematica.client.gui.control;

import com.github.lunatrius.schematica.client.gui.GuiHelper;
import com.github.lunatrius.schematica.client.util.BlockList;
import com.github.lunatrius.schematica.reference.Names;

import net.minecraft.src.Minecraft;
import net.minecraft.src.GuiSlot;
import net.minecraft.src.Tessellator;
import net.minecraft.src.I18n;
import net.minecraft.src.ItemStack;
import org.lwjgl.opengl.GL11;

class GuiSchematicMaterialsSlot extends GuiSlot {
    private final Minecraft minecraft = Minecraft.getMinecraft();

    private final GuiSchematicMaterials guiSchematicMaterials;

    private final String strMaterialRequired = I18n.getString(Names.Gui.Control.MATERIAL_REQUIRED);
    private final String strMaterialAvailable = I18n.getString(Names.Gui.Control.MATERIAL_AVAILABLE);

    protected int selectedIndex = -1;

    public GuiSchematicMaterialsSlot(GuiSchematicMaterials par1) {
        super(Minecraft.getMinecraft(), par1.width, par1.height, 16, par1.height - 34, 24);
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
        final String amountrequired = wrappedItemStack.getFormattedAmountRequired(strMaterialRequired, strMaterialAvailable);

        GuiHelper.drawItemStack(this.minecraft.renderEngine, this.minecraft.fontRenderer, x, y, itemStack);

        this.guiSchematicMaterials.drawString(this.minecraft.fontRenderer, itemName, x + 24, y + 6, 0xFFFFFF);
        this.guiSchematicMaterials.drawString(this.minecraft.fontRenderer, amount, x + 215 - this.minecraft.fontRenderer.getStringWidth(amount), y + 6, 0xFFFFFF);
        this.guiSchematicMaterials.drawString(this.minecraft.fontRenderer, amountrequired, x + 215 - this.minecraft.fontRenderer.getStringWidth(amountrequired), y + 16, 0xFFFFFF);

        if (mouseX > x && mouseY > y && mouseX <= x + 18 && mouseY <= y + 18) {
            this.guiSchematicMaterials.renderToolTip(itemStack, mouseX, mouseY);
            GL11.glDisable(GL11.GL_LIGHTING);
        }
    }
}
