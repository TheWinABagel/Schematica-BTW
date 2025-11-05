package net.fabricmc.example.debug;

import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.List;

public class ChunkProviderDebug implements IChunkProvider {
    private World worldObj;
    private LongHashMap chunkMapping = new LongHashMap();
//    private Random random;
//    private final byte[] cachedBlockIDs = new byte[256];
//    private final byte[] cachedBlockMetadata = new byte[256];
    private final List<MapGenStructure> structureGenerators = new ArrayList<>();

    public ChunkProviderDebug(World par1World/*, long par2, boolean par4, String par5Str*/) {
        this.worldObj = par1World;
//        this.random = new Random(par2);
//        for (FlatLayerInfo var10 : ((List<FlatLayerInfo>) this.flatWorldGenInfo.getFlatLayers())) {
//            for (int var8 = var10.getMinY(); var8 < var10.getMinY() + var10.getLayerCount(); ++var8) {
//                this.cachedBlockIDs[var8] = (byte)(var10.getFillBlock() & 0xFF);
//                this.cachedBlockMetadata[var8] = (byte)var10.getFillBlockMeta();
//            }
//        }
    }

    @Override
    public Chunk loadChunk(int par1, int par2) {
        return this.provideChunk(par1, par2);
    }

    @Override
    public Chunk provideChunk(int chunkX, int chunkY) {
        Chunk chunk = new Chunk(this.worldObj, chunkX, chunkY);
//        chunk.setBlockIDWithMetadata(2,2,2, 1, 1);
//
//        chunk.generateSkylightMap();
        if (chunkX < 0 || chunkX > DebugWorldHelper.maxX || chunkY < 0 || chunkY > DebugWorldHelper.maxY) {
            return chunk;
        }

        var data = new DebugWorldHelper.ChunkData(chunkX, chunkY);
//        var bt = DebugWorldType.ALL_BLOCKS.get(data);
//        if (bt == null) return chunk;
        var thing = DebugWorldHelper.CHUNKS.get(data);
        if (thing == null) return chunk;
        chunk.setStorageArrays(thing.getBlockStorageArray());
//        if (chunkX != 0 || chunkY !=0) return chunk;

//        for (int x = 0; x < 16; x+=2) {
//            for (int y = 0; y < 16; y+=2) {
//                try {
//                    var test = bt.get(y/2 + x/2 * 8);
//                    chunk.setBlockIDWithMetadata(x, 1, y, test.id(), test.meta());
//                }
//                catch (IndexOutOfBoundsException e) {
//                    break;
//                }
//
//            }
//        }

//        for (int var4 = 0; var4 < this.cachedBlockIDs.length; ++var4) {
//            int var5 = var4 >> 4;
//            ExtendedBlockStorage var6 = chunk.getBlockStorageArray()[var5];
//            if (var6 == null) {
//                chunk.getBlockStorageArray()[var5] = var6 = new ExtendedBlockStorage(var4, !this.worldObj.provider.hasNoSky);
//            }
//            for (int var7 = 0; var7 < 16; ++var7) {
//                for (int var8 = 0; var8 < 16; ++var8) {
//                    var6.setExtBlockID(var7, var4 & 0xF, var8, this.cachedBlockIDs[var4] & 0xFF);
//                    var6.setExtBlockMetadata(var7, var4 & 0xF, var8, this.cachedBlockMetadata[var4]);
//                }
//            }
//        }
//        chunk.generateSkylightMap();
//        BiomeGenBase[] var9 = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(null, par1 * 16, par2 * 16, 16, 16);
//        byte[] var10 = chunk.getBiomeArray();
//        for (int var11 = 0; var11 < var10.length; ++var11) {
//            var10[var11] = (byte)var9[var11].biomeID;
//        }
//        for (MapGenStructure var13 : this.structureGenerators) {
//            var13.generate(this, this.worldObj, par1, par2, null);
//        }
        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public boolean chunkExists(int par1, int par2) {
        return true;
    }

    @Override
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
    }

    @Override
    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
        return true;
    }

    @Override
    public void saveExtraData() {
    }

    @Override
    public boolean unloadQueuedChunks() {
        return true;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public String makeString() {
        return "DebugLevelSource";
    }

    @Override
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
        BiomeGenBase var5 = this.worldObj.getBiomeGenForCoords(par2, par4);
        return var5 == null ? null : var5.getSpawnableList(par1EnumCreatureType);
    }

    @Override
    public ChunkPosition findClosestStructure(World par1World, String par2Str, int par3, int par4, int par5) {
        return null;
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    @Override
    public void recreateStructures(int par1, int par2) {}
}
