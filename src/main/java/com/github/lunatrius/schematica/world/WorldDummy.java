package com.github.lunatrius.schematica.world;

import btw.world.util.data.DataEntry;
import com.github.lunatrius.schematica.world.storage.SaveHandlerSchematic;
import net.minecraft.src.*;

public class WorldDummy extends World {
    private static WorldDummy instance;

    public WorldDummy(ISaveHandler saveHandler, String name, WorldSettings worldSettings, WorldProvider worldProvider, Profiler profiler) {
        super(saveHandler, name, worldSettings, worldProvider, profiler, null);
    }

    @Override
    protected IChunkProvider createChunkProvider() {
        return null;
    }


    @Override
    public Entity getEntityByID(int id) {
        return null;
    }

    @Override
    public <T> T getData(DataEntry.WorldDataEntry<T> worldDataEntry) {
        return Minecraft.getMinecraft().theWorld.getData(worldDataEntry);
    }

    @Override
    public <T> void setData(DataEntry.WorldDataEntry<T> worldDataEntry, T t) {

    }

    public static WorldDummy instance() {
        if (instance == null) {
            final WorldSettings worldSettings = new WorldSettings(0, EnumGameType.CREATIVE, false, false, WorldType.FLAT);
            instance = new WorldDummy(new SaveHandlerSchematic(), "Schematica", worldSettings, null, new Profiler());
        }

        return instance;
    }
}
