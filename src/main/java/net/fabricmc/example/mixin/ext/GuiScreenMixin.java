package net.fabricmc.example.mixin.ext;

import emi.dev.emi.emi.screen.EmiScreenManager;
import net.fabricmc.example.ext.GuiScreenExtension;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(GuiScreen.class)
public class GuiScreenMixin extends Gui implements GuiScreenExtension {

    @Shadow protected FontRenderer fontRenderer;

    @Shadow protected Minecraft mc;

    @Shadow public int width;

    @Shadow public int height;

    @Override
    public void renderToolTip(ItemStack par1ItemStack, int mouseX, int mouseY) {
        EmiScreenManager.lastStackTooltipRendered = par1ItemStack;
        List<String> var4 = par1ItemStack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
        for (int var5 = 0; var5 < var4.size(); ++var5) {
            if (var5 == 0) {
                var4.set(var5, "§" + Integer.toHexString(par1ItemStack.getRarity().rarityColor) + var4.get(var5));
                continue;
            }
            var4.set(var5, EnumChatFormatting.GRAY + var4.get(var5));
        }
        this.renderTooltipList(var4, mouseX, mouseY);
    }

    @Override
    public void renderTooltipList(List<String> tooltip, int mouseX, int mouseY) {
        if (!tooltip.isEmpty()) {
            GL11.glDisable(32826);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            int var4 = 0;
            for (String var6 : tooltip) {
                int var7 = this.fontRenderer.getStringWidth(var6);
                if (var7 <= var4) continue;
                var4 = var7;
            }
            int var14 = mouseX + 12;
            int var15 = mouseY - 12;
            int var8 = 8;
            if (tooltip.size() > 1) {
                var8 += 2 + (tooltip.size() - 1) * 10;
            }
            if (var14 + var4 > this.width) {
                var14 -= 28 + var4;
            }
            if (var15 + var8 + 6 > this.height) {
                var15 = this.height - var8 - 6;
            }
            this.zLevel = 300.0f;
//            itemRenderer.zLevel = 300.0f;
            int var9 = -267386864;
            this.drawGradientRect(var14 - 3, var15 - 4, var14 + var4 + 3, var15 - 3, var9, var9);
            this.drawGradientRect(var14 - 3, var15 + var8 + 3, var14 + var4 + 3, var15 + var8 + 4, var9, var9);
            this.drawGradientRect(var14 - 3, var15 - 3, var14 + var4 + 3, var15 + var8 + 3, var9, var9);
            this.drawGradientRect(var14 - 4, var15 - 3, var14 - 3, var15 + var8 + 3, var9, var9);
            this.drawGradientRect(var14 + var4 + 3, var15 - 3, var14 + var4 + 4, var15 + var8 + 3, var9, var9);
            int var10 = 0x505000FF;
            int var11 = (var10 & 0xFEFEFE) >> 1 | var10 & 0xFF000000;
            this.drawGradientRect(var14 - 3, var15 - 3 + 1, var14 - 3 + 1, var15 + var8 + 3 - 1, var10, var11);
            this.drawGradientRect(var14 + var4 + 2, var15 - 3 + 1, var14 + var4 + 3, var15 + var8 + 3 - 1, var10, var11);
            this.drawGradientRect(var14 - 3, var15 - 3, var14 + var4 + 3, var15 - 3 + 1, var10, var10);
            this.drawGradientRect(var14 - 3, var15 + var8 + 2, var14 + var4 + 3, var15 + var8 + 3, var11, var11);
            for (int var12 = 0; var12 < tooltip.size(); ++var12) {
                String var13 = (String)tooltip.get(var12);
                this.fontRenderer.drawStringWithShadow(var13, var14, var15, -1);
                if (var12 == 0) {
                    var15 += 2;
                }
                var15 += 10;
            }
            this.zLevel = 0.0f;
//            itemRenderer.zLevel = 0.0f;
            GL11.glEnable(2896);
            GL11.glEnable(2929);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(32826);
        }
    }
}
