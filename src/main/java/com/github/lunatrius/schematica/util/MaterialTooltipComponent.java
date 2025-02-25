package com.github.lunatrius.schematica.util;

import com.github.lunatrius.schematica.client.util.BlockList;
import emi.dev.emi.emi.runtime.EmiDrawContext;
import emi.dev.emi.emi.screen.tooltip.EmiTooltipComponent;
import emi.shims.java.net.minecraft.text.Text;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Minecraft;

public class MaterialTooltipComponent implements EmiTooltipComponent {
    private final BlockList.WrappedItemStack wrapped;
    private static final int OFFSET = 45;

    public MaterialTooltipComponent(BlockList.WrappedItemStack wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public int getHeight() {
        return 34;
    }

    @Override
    public int getWidth(FontRenderer fr) {
        int firstWidth = fr.getStringWidth(wrapped.getItemStackDisplayName()) + OFFSET + 18;
        int secondWidth = fr.getStringWidth(wrapped.calculateTotal()) + OFFSET;
        return Math.max(firstWidth, secondWidth);
    }

    @Override
    public void drawTooltipText(TextRenderData text) {
        text.draw(Text.literal("Item:"), 0, 0, 0xFFFFFFFF, true);
        text.draw(Text.literal(wrapped.getItemStackDisplayName()), OFFSET/* + 18*/, 0, 0xFFFFFFFF, true);

        text.draw(Text.literal("Total:"), 0, 12, 0xFFFFFFFF, true);
        text.draw(Text.literal(wrapped.calculateTotal()), OFFSET, 12, 0xFFFFFFFF, true);

        text.draw(Text.literal("Missing:"), 0, 24, 0xFFFFFFFF, true);
        text.draw(Text.literal(wrapped.getMissingNoColor()), OFFSET, 24, 0xFFFFFFFF, true);
    }

    @Override
    public void drawTooltip(EmiDrawContext context, TooltipRenderData tooltip) {
        context.push();

//        GL11.glDisable(2929);
        context.matrices().translate(OFFSET + tooltip.text.getStringWidth(wrapped.getItemStackDisplayName()) + 2, -1.2, 0);
        context.matrices().scale(0.7, 0.7, 0.7);
//        context.raw().drawItem(this.wrapped.itemStack, 0, 0);
//        GL11.glDisable(2896);
//        GL11.glEnable(GL11.GL_DEPTH_TEST);
//        GL11.glEnable(GL11.GL_LIGHTING);
//        GL11.glEnable(GL11.GL_DEPTH);
//        GL11.glEnable(GL11.GL_BLEND);
        tooltip.item.renderItemIntoGUI(tooltip.text, Minecraft.getMinecraft().renderEngine, this.wrapped.itemStack, 0, 0);
//        GL11.glDisable(GL11.GL_DEPTH_TEST);
//        GL11.glDisable(GL11.GL_LIGHTING);
//        GL11.glEnable(3042);
//        GL11.glBlendFunc(770, 771);
//        GL11.glDisable(3008);
//        GL11.glShadeModel(7425);
//        GL11.glDisable(3553);
        context.pop();
    }
}
