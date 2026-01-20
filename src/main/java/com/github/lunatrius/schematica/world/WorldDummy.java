package com.github.lunatrius.schematica.world;

import api.world.data.DataEntry;
import com.github.lunatrius.schematica.world.storage.SaveHandlerSchematic;
import net.fabricmc.example.debug.ChunkProviderDebug;
import net.minecraft.src.*;

public class WorldDummy extends World {
    private static WorldDummy instance;

    public WorldDummy(ISaveHandler saveHandler, String name, WorldSettings worldSettings, WorldProvider worldProvider, Profiler profiler) {
        super(saveHandler, name, worldSettings, worldProvider, profiler, null);
    }

    @Override
    protected IChunkProvider createChunkProvider() {
        return new ChunkProviderDebug(this);
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

//    @Override
//    public void notifyBlocksOfNeighborChange(int par1, int par2, int par3, int par4) {
////        super.notifyBlocksOfNeighborChange(par1, par2, par3, par4);
//    }
//
//    @Override
//    public void notifyBlocksOfNeighborChange(int par1, int par2, int par3, int par4, int par5) {
////        super.notifyBlocksOfNeighborChange(par1, par2, par3, par4, par5);
//    }
}
