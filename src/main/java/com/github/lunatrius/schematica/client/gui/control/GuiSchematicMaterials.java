package com.github.lunatrius.schematica.client.gui.control;

import com.github.lunatrius.core.client.gui.GuiScreenBase;
import com.github.lunatrius.schematica.Schematica;
import com.github.lunatrius.schematica.client.gui.shim.GuiUnicodeGlyphButton;
import com.github.lunatrius.schematica.client.util.BlockList;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import com.github.lunatrius.schematica.handler.ConfigurationHandler;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import com.github.lunatrius.schematica.reference.Names;
import com.github.lunatrius.schematica.reference.Reference;
import com.github.lunatrius.schematica.util.ItemStackSortType;
import emi.dev.emi.emi.screen.EmiScreenManager;
import net.minecraft.src.*;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL11;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Formatter;
import java.util.List;

public class GuiSchematicMaterials extends GuiScreenBase {
    private GuiSchematicMaterialsSlot guiSchematicMaterialsSlot;

    private ItemStackSortType sortType = ItemStackSortType.fromString(ConfigurationHandler.sortType);

    private GuiUnicodeGlyphButton btnSort = null;
    private GuiButton btnDump = null;
    private GuiButton btnDone = null;

    private final String strMaterialName = I18n.getString(Names.Gui.Control.MATERIAL_NAME);
    private final String strMaterialAmount = I18n.getString(Names.Gui.Control.MATERIAL_AMOUNT);

    protected final List<BlockList.WrappedItemStack> blockList;

    public GuiSchematicMaterials(GuiScreen guiScreen) {
        super(guiScreen);
        final Minecraft minecraft = Minecraft.getMinecraft();
        final SchematicWorld schematic = ClientProxy.schematic;
        this.blockList = new BlockList().getList(minecraft.thePlayer, schematic, minecraft.theWorld);
        this.sortType.sort(this.blockList);
    }

    @Override
    public void initGui() {
        int id = 0;

        this.btnSort = new GuiUnicodeGlyphButton(++id, this.width / 2 - 154, this.height - 30, 100, 20, " " + I18n.getString(Names.Gui.Control.SORT_PREFIX + this.sortType.label), this.sortType.glyph, 2.0f);
        this.buttonList.add(this.btnSort);

        this.btnDump = new GuiButton(++id, this.width / 2 - 50, this.height - 30, 100, 20, I18n.getString(Names.Gui.Control.DUMP));
        this.buttonList.add(this.btnDump);

        this.btnDone = new GuiButton(++id, this.width / 2 + 54, this.height - 30, 100, 20, I18n.getString(Names.Gui.DONE));
        this.buttonList.add(this.btnDone);

        this.guiSchematicMaterialsSlot = new GuiSchematicMaterialsSlot(this);
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == this.btnSort.id) {
                this.sortType = this.sortType.next();
                this.sortType.sort(this.blockList);
                this.btnSort.displayString = " " + I18n.getString(Names.Gui.Control.SORT_PREFIX + this.sortType.label);
                this.btnSort.glyph = this.sortType.glyph;

                //todo change config when button pressed
//                ConfigurationHandler.propSortType.set(String.valueOf(this.sortType));
//                ConfigurationHandler.loadConfiguration();
            } else if (guiButton.id == this.btnDump.id) {
                dumpMaterialList(this.blockList);
            } else if (guiButton.id == this.btnDone.id) {
                this.mc.displayGuiScreen(this.parentScreen);
            } else {
                this.guiSchematicMaterialsSlot.actionPerformed(guiButton);
            }
        }
    }

//    @Override
    //straight up copied from GuiContainer
public void renderToolTip(ItemStack par1ItemStack, int par2, int par3) {
    EmiScreenManager.lastStackTooltipRendered = par1ItemStack;
    List<String> var4 = par1ItemStack.getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
    for (int var5 = 0; var5 < var4.size(); ++var5) {
        if (var5 == 0) {
            var4.set(var5, "§" + Integer.toHexString(par1ItemStack.getRarity().rarityColor) + var4.get(var5));
            continue;
        }
        var4.set(var5, EnumChatFormatting.GRAY + var4.get(var5));
    }
    this.func_102021_a(var4, par2, par3);
}

    protected void func_102021_a(List<String> par1List, int par2, int par3) {
        if (!par1List.isEmpty()) {
            GL11.glDisable(32826);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            int var4 = 0;
            for (String var6 : par1List) {
                int var7 = this.fontRenderer.getStringWidth(var6);
                if (var7 <= var4) continue;
                var4 = var7;
            }
            int var14 = par2 + 12;
            int var15 = par3 - 12;
            int var8 = 8;
            if (par1List.size() > 1) {
                var8 += 2 + (par1List.size() - 1) * 10;
            }
            if (var14 + var4 > this.width) {
                var14 -= 28 + var4;
            }
            if (var15 + var8 + 6 > this.height) {
                var15 = this.height - var8 - 6;
            }
            this.zLevel = 300.0f;
            itemRenderer.zLevel = 300.0f;
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
            for (int var12 = 0; var12 < par1List.size(); ++var12) {
                String var13 = (String)par1List.get(var12);
                this.fontRenderer.drawStringWithShadow(var13, var14, var15, -1);
                if (var12 == 0) {
                    var15 += 2;
                }
                var15 += 10;
            }
            this.zLevel = 0.0f;
            itemRenderer.zLevel = 0.0f;
            GL11.glEnable(2896);
            GL11.glEnable(2929);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(32826);
        }
    }

    @Override
    public void drawScreen(int x, int y, float partialTicks) {
        this.guiSchematicMaterialsSlot.drawScreen(x, y, partialTicks);

        drawString(this.fontRenderer, this.strMaterialName, this.width / 2 - 108, 4, 0x00FFFFFF);
        drawString(this.fontRenderer, this.strMaterialAmount, this.width / 2 + 108 - this.fontRenderer.getStringWidth(this.strMaterialAmount), 4, 0x00FFFFFF);
        super.drawScreen(x, y, partialTicks);
    }

    private void dumpMaterialList(final List<BlockList.WrappedItemStack> blockList) {
        if (blockList.size() <= 0) {
            return;
        }

        int maxLengthName = 0;
        int maxSize = 0;
        for (final BlockList.WrappedItemStack wrappedItemStack : blockList) {
            maxLengthName = Math.max(maxLengthName, wrappedItemStack.getItemStackDisplayName().length());
            maxSize = Math.max(maxSize, wrappedItemStack.total);
        }

        final int maxLengthSize = String.valueOf(maxSize).length();
        final String formatName = "%-" + maxLengthName + "s";
        final String formatSize = "%" + maxLengthSize + "d";

        final StringBuilder stringBuilder = new StringBuilder((maxLengthName + 1 + maxLengthSize) * blockList.size());
        final Formatter formatter = new Formatter(stringBuilder);
        for (final BlockList.WrappedItemStack wrappedItemStack : blockList) {
            formatter.format(formatName, wrappedItemStack.getItemStackDisplayName());
            stringBuilder.append(" ");
            formatter.format(formatSize, wrappedItemStack.total);
            stringBuilder.append(System.lineSeparator());
        }

        final File dumps = Schematica.getProxy().getDirectory("dumps");
        try {
            final FileOutputStream outputStream = new FileOutputStream(new File(dumps, Reference.NAME + "-materials.txt"));
            try {
                IOUtils.write(stringBuilder.toString(), outputStream);
            } finally {
                outputStream.close();
            }
        } catch (final Exception e) {
            Reference.logger.error("Could not dump the material list!", e);
        }
    }
}
