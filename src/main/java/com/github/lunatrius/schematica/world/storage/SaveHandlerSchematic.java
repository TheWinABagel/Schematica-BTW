package com.github.lunatrius.schematica.world.storage;

import net.minecraft.src.*;

import java.io.File;

public class SaveHandlerSchematic implements ISaveHandler {
    @Override
    public WorldInfo loadWorldInfo() {
        return null;
    }

    @Override
    public void checkSessionLock() throws MinecraftException {}

    @Override
    public IChunkLoader getChunkLoader(WorldProvider provider) {
        return null;
    }

    @Override
    public void saveWorldInfoWithPlayer(WorldInfo info, NBTTagCompound compound) {}

    @Override
    public void saveWorldInfo(WorldInfo info) {}

    @Override
    public IPlayerFileData getSaveHandler() {
        return null;
    }

    @Override
    public void flush() {}

    @Override
    public void loadModSpecificData(WorldServer var1) {

    }

    @Override
    public void saveModSpecificData(WorldServer var1) {

    }

    @Override
    public File getMapFileFromName(String name) {
        return null;
    }

    @Override
    public String getWorldDirectoryName() {
        return null;
    }
}
