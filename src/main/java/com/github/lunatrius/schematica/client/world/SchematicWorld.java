package com.github.lunatrius.schematica.client.world;

import com.github.lunatrius.core.util.vector.Vector3f;
import com.github.lunatrius.core.util.vector.Vector3i;
import com.github.lunatrius.api.ISchematic;
import com.github.lunatrius.schematica.reference.Reference;
import com.github.lunatrius.schematica.world.chunk.ChunkProviderSchematic;
import com.github.lunatrius.schematica.world.storage.SaveHandlerSchematic;
import com.github.lunatrius.schematica.world.storage.Schematic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.TileEntitySkull;
import net.minecraft.src.EnumSkyBlock;
import net.minecraft.src.World;
import net.minecraft.src.WorldSettings;
import net.minecraft.src.WorldType;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.IChunkProvider;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class SchematicWorld extends World {
    private static final WorldSettings WORLD_SETTINGS = new WorldSettings(0, WorldSettings.GameType.CREATIVE, false, false, WorldType.FLAT);

    public static final ItemStack DEFAULT_ICON = new ItemStack(Blocks.grass);

    private ISchematic schematic;

    public final Vector3i position = new Vector3i();
    public boolean isRendering;
    public boolean isRenderingLayer;
    public int renderingLayer;

    public SchematicWorld(ISchematic schematic) {
        super(new SaveHandlerSchematic(), "Schematica", WORLD_SETTINGS, null, null);
        this.schematic = schematic;

        for (TileEntity tileEntity : schematic.getTileEntities()) {
            initializeTileEntity(tileEntity);
        }

        this.isRendering = false;
        this.isRenderingLayer = false;
        this.renderingLayer = 0;
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        if (this.isRenderingLayer && this.renderingLayer != y) {
            return Blocks.air;
        }

        return this.schematic.getBlock(x, y, z);
    }

    @Override
    public boolean setBlock(int x, int y, int z, Block block, int metadata, int flags) {
        return this.schematic.setBlock(x, y, z, block, metadata);
    }

    @Override
    public TileEntity getTileEntity(int x, int y, int z) {
        return this.schematic.getTileEntity(x, y, z);
    }

    @Override
    public void setTileEntity(int x, int y, int z, TileEntity tileEntity) {
        this.schematic.setTileEntity(x, y, z, tileEntity);
        initializeTileEntity(tileEntity);
    }

    @Override
    public void removeTileEntity(int x, int y, int z) {
        this.schematic.removeTileEntity(x, y, z);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getSkyBlockTypeBrightness(EnumSkyBlock skyBlock, int x, int y, int z) {
        return 15;
    }

    @Override
    public float getLightBrightness(int x, int y, int z) {
        return 1.0f;
    }

    @Override
    public int getBlockMetadata(int x, int y, int z) {
        return this.schematic.getBlockMetadata(x, y, z);
    }

    @Override
    public boolean isBlockNormalCubeDefault(int x, int y, int z, boolean _default) {
        return getBlock(x, y, z).isNormalCube();
    }

    @Override
    protected int func_152379_p() {
        return 0;
    }

    @Override
    public boolean isAirBlock(int x, int y, int z) {
        return getBlock(x, y, z).isAir(this, x, y, z);
    }

    @Override
    public BiomeGenBase getBiomeGenForCoords(int x, int z) {
        return BiomeGenBase.jungle;
    }

    public int getWidth() {
        return this.schematic.getWidth();
    }

    public int getLength() {
        return this.schematic.getLength();
    }

    @Override
    public int getHeight() {
        return this.schematic.getHeight();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean extendedLevelsInChunkCache() {
        return false;
    }

    @Override
    protected IChunkProvider createChunkProvider() {
        return new ChunkProviderSchematic(this);
    }

    @Override
    public Entity getEntityByID(int id) {
        return null;
    }

    @Override
    public boolean blockExists(int x, int y, int z) {
        return false;
    }

    @Override
    public boolean setBlockMetadataWithNotify(int x, int y, int z, int metadata, int flag) {
        return this.schematic.setBlockMetadata(x, y, z, metadata);
    }

    @Override
    public boolean isSideSolid(int x, int y, int z, ForgeDirection side) {
        return isSideSolid(x, y, z, side, false);
    }

    @Override
    public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default) {
        return getBlock(x, y, z).isSideSolid(this, x, y, z, side);
    }

    public void initializeTileEntity(TileEntity tileEntity) {
        tileEntity.setWorldObj(this);
        tileEntity.getBlockType();
        try {
            tileEntity.validate();
        } catch (Exception e) {
            Reference.logger.error("TileEntity validation for {} failed!", tileEntity.getClass(), e);
        }
    }

    public void setIcon(ItemStack icon) {
        this.schematic.setIcon(icon);
    }

    public ItemStack getIcon() {
        return this.schematic.getIcon();
    }

    public List<TileEntity> getTileEntities() {
        return this.schematic.getTileEntities();
    }

    public boolean toggleRendering() {
        this.isRendering = !this.isRendering;
        return this.isRendering;
    }

    public void refreshChests() {
        for (TileEntity tileEntity : this.schematic.getTileEntities()) {
            if (tileEntity instanceof TileEntityChest) {
                TileEntityChest tileEntityChest = (TileEntityChest) tileEntity;
                tileEntityChest.adjacentChestChecked = false;
                tileEntityChest.checkForAdjacentChests();
            }
        }
    }

    public void flip() {
        // won't be implemented in 1.7.10 due to the lack of an API
    }

    public void rotate() {
        final ItemStack icon = this.schematic.getIcon();
        final int width = this.schematic.getWidth();
        final int height = this.schematic.getHeight();
        final int length = this.schematic.getLength();

        final ISchematic schematicRotated = new Schematic(icon, length, height, width);

        for (int y = 0; y < height; y++) {
            for (int z = 0; z < length; z++) {
                for (int x = 0; x < width; x++) {
                    try {
                        getBlock(x, y, length - 1 - z).rotateBlock(this, x, y, length - 1 - z, ForgeDirection.UP);
                    } catch (Exception e) {
                        Reference.logger.debug("Failed to rotate block!", e);
                    }

                    final Block block = getBlock(x, y, length - 1 - z);
                    final int metadata = getBlockMetadata(x, y, length - 1 - z);
                    schematicRotated.setBlock(z, y, x, block, metadata);
                }
            }
        }

        for (TileEntity tileEntity : this.schematic.getTileEntities()) {
            final int coord = tileEntity.zCoord;
            tileEntity.zCoord = tileEntity.xCoord;
            tileEntity.xCoord = length - 1 - coord;
            tileEntity.blockMetadata = schematicRotated.getBlockMetadata(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);

            if (tileEntity instanceof TileEntitySkull && tileEntity.blockMetadata == 0x1) {
                TileEntitySkull skullTileEntity = (TileEntitySkull) tileEntity;
                skullTileEntity.func_145903_a((skullTileEntity.func_145906_b() + 12) & 15);
            }

            schematicRotated.setTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, tileEntity);
        }

        this.schematic = schematicRotated;

        refreshChests();
    }

    public Vector3f dimensions() {
        return new Vector3f(this.schematic.getWidth(), this.schematic.getHeight(), this.schematic.getLength());
    }

    public String getDebugDimensions() {
        return "WHL: " + getWidth() + " / " + getHeight() + " / " + getLength();
    }
}
