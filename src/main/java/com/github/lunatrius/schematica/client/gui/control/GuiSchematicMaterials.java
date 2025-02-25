package com.github.lunatrius.schematica.client.gui.control;

import com.github.lunatrius.core.client.gui.GuiScreenBase;
import com.github.lunatrius.schematica.Schematica;
import com.github.lunatrius.schematica.client.gui.shim.GuiUnicodeGlyphButton;
import com.github.lunatrius.schematica.client.util.BlockList;
import com.github.lunatrius.schematica.handler.ConfigurationHandler;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import com.github.lunatrius.schematica.reference.Names;
import com.github.lunatrius.schematica.reference.Reference;
import com.github.lunatrius.schematica.util.ItemStackSortType;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.I18n;
import net.minecraft.src.Minecraft;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Formatter;
import java.util.List;

public class GuiSchematicMaterials extends GuiScreenBase {
    private GuiSchematicMaterialsSlot guiSchematicMaterialsSlot;

    protected ItemStackSortType sortType = ItemStackSortType.fromString(ConfigurationHandler.sortType);

    private GuiUnicodeGlyphButton btnSort = null;
    private GuiButton btnDump = null;
    private GuiButton btnDone = null;

    private long lastClicked = 0;

    protected final List<BlockList.WrappedItemStack> blockList;
    protected final List<BlockList.WrappedItemStack> blockListOther;

    public GuiSchematicMaterials(GuiScreen guiScreen) {
        super(guiScreen);
        Minecraft mc = Minecraft.getMinecraft();
        this.blockList = new BlockList().getList(mc.thePlayer, ClientProxy.schematic, mc.theWorld);
        this.sortType.sort(this.blockList);
        this.blockListOther = new BlockList().getList(mc.thePlayer, ClientProxy.schematic, mc.theWorld);
    }

    public void resetList() {
        this.blockList.clear();
        this.blockList.addAll(blockListOther);
    }

    @Override
    public void drawBackground(int par1) {
        super.drawBackground(par1);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseEvent) {
        super.mouseClicked(mouseX, mouseY, mouseEvent);
        if (Minecraft.getSystemTime() - this.lastClicked > 125L) {
            this.lastClicked = Minecraft.getSystemTime();
            for (GuiSchematicButton button : this.guiSchematicMaterialsSlot.buttons) {
                button.mousePressed(mouseX, mouseY);
            }
        }
    }

    @Override
    public void initGui() {
        int id = 0;
//        this.width += 100;

//        this.btnSort = new GuiUnicodeGlyphButton(++id, this.width / 2 - 154, this.height - 28, 100, 20, " " + I18n.getString(Names.Gui.Control.SORT_PREFIX + this.sortType.label), this.sortType.glyph, 2.0f);
//        this.buttonList.add(this.btnSort);

        this.btnDump = new GuiButton(++id, this.width / 2 - 100, this.height - 28, 100, 20, I18n.getString(Names.Gui.Control.DUMP));
        this.buttonList.add(this.btnDump);

        this.btnDone = new GuiButton(++id, this.width / 2 + 4, this.height - 28, 100, 20, I18n.getString(Names.Gui.DONE));
        this.buttonList.add(this.btnDone);

        this.guiSchematicMaterialsSlot = new GuiSchematicMaterialsSlot(this);
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) {
        Reference.logger.info("Action performed: button {} with id  {}, hovering is {}", guiButton, guiButton.id, guiButton.func_82252_a());
        if (guiButton.enabled) {
//            if (guiButton.id == this.btnSort.id) {
//                this.sortType = this.sortType.next();
//                this.sortType.sort(this.blockList);
//                this.btnSort.displayString = " " + I18n.getString(Names.Gui.Control.SORT_PREFIX + this.sortType.label);
//                this.btnSort.glyph = this.sortType.glyph;
//
//                ConfigurationHandler.propSortType.set(String.valueOf(this.sortType));
//                ConfigurationHandler.loadConfiguration();
//            } else
                if (guiButton.id == this.btnDump.id) {
                dumpMaterialList(this.blockList);
            } else if (guiButton.id == this.btnDone.id) {
                this.mc.displayGuiScreen(this.parentScreen);
            } else {
//                this.guiSchematicMaterialsSlot.actionPerformed(guiButton);
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.guiSchematicMaterialsSlot.drawScreen(mouseX, mouseY, partialTicks);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void dumpMaterialList(final List<BlockList.WrappedItemStack> blockList) {
        if (blockList.isEmpty()) {
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
