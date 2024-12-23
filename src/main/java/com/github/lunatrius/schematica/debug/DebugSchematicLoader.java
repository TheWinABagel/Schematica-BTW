package com.github.lunatrius.schematica.debug;

import com.github.lunatrius.schematica.FileFilterSchematic;
import com.github.lunatrius.schematica.Schematica;
import com.github.lunatrius.schematica.client.gui.load.GuiSchematicEntry;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import com.github.lunatrius.schematica.handler.ConfigurationHandler;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import com.github.lunatrius.schematica.reference.Names;
import com.github.lunatrius.schematica.reference.Reference;
import com.github.lunatrius.schematica.world.schematic.SchematicUtil;
import net.minecraft.src.Block;
import net.minecraft.src.I18n;
import net.minecraft.src.Item;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DebugSchematicLoader {
    private static final FileFilterSchematic FILE_FILTER_FOLDER = new FileFilterSchematic(true);
    private static final FileFilterSchematic FILE_FILTER_SCHEMATIC = new FileFilterSchematic(false);

    protected File currentDirectory = ConfigurationHandler.schematicDirectory;
    protected final List<GuiSchematicEntry> schematicFiles = new ArrayList<>();

    public DebugSchematicLoader() {
        reloadSchematics();
        for (GuiSchematicEntry entry : schematicFiles) {
            if (entry.getName().contains("Building")) {
                loadSchematic(entry);
                return;
            }
        }
    }

    public void initGui() {
        int id = 0;

//        this.btnOpenDir = new GuiButton(id++, this.width / 2 - 154, this.height - 36, 150, 20, I18n.getString(Names.Gui.Load.OPEN_FOLDER));
//        this.buttonList.add(this.btnOpenDir);
//
//        this.btnDone = new GuiButton(id++, this.width / 2 + 4, this.height - 36, 150, 20, I18n.getString(Names.Gui.DONE));
//        this.buttonList.add(this.btnDone);
//
//        this.guiSchematicLoadSlot = new GuiSchematicLoadSlot(this);

        reloadSchematics();
    }

    protected void changeDirectory(String directory) {
        this.currentDirectory = new File(this.currentDirectory, directory);

        reloadSchematics();
    }

    protected void reloadSchematics() {
        String name = null;
        Item item = null;

        this.schematicFiles.clear();

        try {
            if (!this.currentDirectory.getCanonicalPath().equals(ConfigurationHandler.schematicDirectory.getCanonicalPath())) {
                this.schematicFiles.add(new GuiSchematicEntry("..", Item.bucketLava, 0, true));
            }
        } catch (IOException e) {
            Reference.logger.error("Failed to add GuiSchematicEntry!", e);
        }

        File[] filesFolders = this.currentDirectory.listFiles(FILE_FILTER_FOLDER);
        if (filesFolders == null) {
            Reference.logger.error("listFiles returned null (directory: {})!", this.currentDirectory);
        } else {
            for (File file : filesFolders) {
                if (file == null) {
                    continue;
                }

                name = file.getName();

                File[] files = file.listFiles();
                item = (files == null || files.length == 0) ? Item.bucketEmpty : Item.bucketWater;

                this.schematicFiles.add(new GuiSchematicEntry(name, item, 0, file.isDirectory()));
            }
        }

        File[] filesSchematics = this.currentDirectory.listFiles(FILE_FILTER_SCHEMATIC);
        if (filesSchematics == null || filesSchematics.length == 0) {
            this.schematicFiles.add(new GuiSchematicEntry(I18n.getString(Names.Gui.Load.NO_SCHEMATIC), Block.dirt, 0, false));
        } else {
            for (File file : filesSchematics) {
                name = file.getName();

                this.schematicFiles.add(new GuiSchematicEntry(name, SchematicUtil.getIconFromFile(file), file.isDirectory()));
            }
        }
    }

    private void loadSchematic(GuiSchematicEntry schematicEntry) {

        try {
            if (Schematica.getProxy().loadSchematic(null, this.currentDirectory, schematicEntry.getName())) {
                SchematicWorld schematic = ClientProxy.schematic;
                if (schematic != null) {
                    ClientProxy.moveSchematicToPlayer(schematic);
                }
            }
        } catch (Exception e) {
            Reference.logger.error("Failed to load schematic!", e);
        }
    }
}
