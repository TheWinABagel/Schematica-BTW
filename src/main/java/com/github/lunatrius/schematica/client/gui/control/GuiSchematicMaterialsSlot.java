package com.github.lunatrius.schematica.client.gui.control;

import com.github.lunatrius.schematica.client.gui.GuiHelper;
import com.github.lunatrius.schematica.client.util.BlockList;
import com.github.lunatrius.schematica.reference.Names;
import com.github.lunatrius.schematica.util.ItemStackSortType;
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
    private final Minecraft mc = Minecraft.getMinecraft();
    private final GuiSchematicMaterials parent;

    private final String strMaterialAvailable = I18n.getString(Names.Gui.Control.MATERIAL_AVAILABLE);
    private final String strMaterialName = I18n.getString(Names.Gui.Control.MATERIAL_NAME);
    private final String strMaterialTotal = I18n.getString(Names.Gui.Control.MATERIAL_TOTAL);
    private final String strMaterialMissing = I18n.getString(Names.Gui.Control.MATERIAL_MISSING);

    protected int selectedIndex;
    protected int topOffset;

    private final int NAME_START = 0;
    private final int TOTAL_AMOUNT_START = 170;
    private final int MISSING_START = 230;
    private final int AVAILABLE_START = 290;

    protected final List<GuiSchematicButton> buttons = new ArrayList<>();

    public GuiSchematicMaterialsSlot(GuiSchematicMaterials parent) {
        super(Minecraft.getMinecraft(), parent.width, parent.height, 16, parent.height - 34, 21);
        ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        this.parent = parent;
        this.selectedIndex = -1;
        this.topOffset = 0;
        this.func_77223_a(true, topOffset);
        this.setShowSelectionBox(false);
        int width = 59;

        int height = 15;

        addSortButton(new GuiSchematicButton(NAME_START, 2, 169, height, this.strMaterialName, 2.0f, ItemStackSortType.NAME_NONE));
        addSortButton(new GuiSchematicButton(TOTAL_AMOUNT_START, 2, width, height, this.strMaterialTotal, 2.0f, ItemStackSortType.SIZE_NONE));
        addSortButton(new GuiSchematicButton(MISSING_START, 2, width, height, this.strMaterialMissing, 2.0f, ItemStackSortType.MISSING_NONE));
        addSortButton(new GuiSchematicButton(AVAILABLE_START, 2, width, height, this.strMaterialAvailable, 2.0f, ItemStackSortType.AVAILABLE_NONE));

//        addButton(new GuiSchematicButton(AVAILABLE_START + 60, 4, width, height, I18n.getString(Names.Gui.Control.DUMP))).onClick((button, mouseX1, mouseY1) -> {
//            Reference.logger.debug("Dumping!");
//            parent.dumpMaterialList(parent.blockList);
//            return true;
//        });
    }

    @Override
    protected int getSize() {
        return this.parent.blockList.size();
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
        int xOff = this.parent.width;
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
        if (bottom == this.bottom && height == this.parent.height) {
            int xStart = 4;
            GuiSchematicMaterials gui = this.parent;
            FontRenderer fr = this.mc.fontRenderer;
//            gui.drawString(fr, this.strMaterialName, xStart, 4, 0x00FFFFFF);
//            gui.drawString(fr, this.strMaterialTotal, xStart + TOTAL_AMOUNT_START, 4, 0x00FFFFFF);
//            gui.drawString(fr, this.strMaterialMissing, xStart + MISSING_START, 4, 0x00FFFFFF);
//            gui.drawString(fr, this.strMaterialAvailable, xStart + AVAILABLE_START, 4, 0x00FFFFFF);
            for (GuiSchematicButton button : this.buttons) {
                button.render(mouseX, mouseY);
            }
        }
    }

    @Override
    protected void drawSlot(int index, int x, int y, int height, Tessellator tessellator) {
        final BlockList.WrappedItemStack wrapped = this.parent.blockList.get(index);
        final ItemStack itemStack = wrapped.itemStack;

        final String itemName = wrapped.getItemStackDisplayName();
        final String total = wrapped.getTotal();
        final String missing = wrapped.getMissing();
        final String available = wrapped.getAvailable();
        x = 4;

        GuiHelper.drawItemStack(this.mc.renderEngine, this.mc.fontRenderer, x, y, itemStack);
        y += 6;
        this.parent.drawString(this.mc.fontRenderer, itemName, x + NAME_START + 24, y, 0xFFFFFF);
        this.parent.drawString(this.mc.fontRenderer, total, x + TOTAL_AMOUNT_START, y, 0xFFFFFF);
        this.parent.drawString(this.mc.fontRenderer, missing, x + MISSING_START, y, 0xFFFFFF);
        this.parent.drawString(this.mc.fontRenderer, available, x + AVAILABLE_START, y, 0xFFFFFF);

    }

    /**
     * Render tooltips
     * */
    @Override
    protected void func_77215_b(int mouseX, int mouseY) {
        int yOffset = this.top + 4 - (int) this.amountScrolled;
        for (int idx = 0; idx < this.getSize(); ++idx) {
            int y = yOffset + idx * this.slotHeight;
            if (y > this.bottom || y + this.slotHeight - 4 < this.top) continue;
            BlockList.WrappedItemStack wrappedItemStack = this.parent.blockList.get(idx);

            if (mouseX > 4 && mouseX <= this.parent.width - 20 && mouseY > y + 2 && mouseY <= y + 2 + this.slotHeight && mouseY <= bottom) {
                List<TooltipComponent> comps = new ArrayList<>();
                comps.add(new MaterialTooltipComponent(wrappedItemStack));
                EmiRenderHelper.drawTooltip(this.parent, EmiDrawContext.instance(), comps, this.mouseX, this.mouseY, this.parent.width / 2);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
            }
        }

    }

    @Override
    protected int getScrollBarX() {
        var sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        return sr.getScaledWidth() - 10;
    }

    private GuiSchematicButton addButton(GuiSchematicButton button) {
        this.buttons.add(button);

        return button;
    }

    private GuiSchematicButton addSortButton(GuiSchematicButton button) {
        this.buttons.add(button);
        button.onClick((but, mouseX1, mouseY1) -> {
            for (GuiSchematicButton btn : buttons) {
                if (!btn.equals(but)) {
                    btn.resetToNone();
                }
            }
            parent.sortType = but.cycle();
            //if comparator is null, aka if type NONE
            if (!parent.sortType.sort(parent.blockList)) {
                parent.resetList();
            }
            return true;
        });

        return button;
    }
}
