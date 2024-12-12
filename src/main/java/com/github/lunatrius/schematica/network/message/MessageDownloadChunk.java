package com.github.lunatrius.schematica.network.message;

import com.github.lunatrius.api.ISchematic;
import com.github.lunatrius.schematica.handler.DownloadHandler;
import com.github.lunatrius.schematica.nbt.NBTHelper;
import com.github.lunatrius.schematica.network.util.ByteBuf;
import com.github.lunatrius.schematica.network.util.IMessage;
import com.github.lunatrius.schematica.network.util.IMessageHandler;
import com.github.lunatrius.schematica.network.util.MessageContext;
import com.github.lunatrius.schematica.reference.Constants;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

import java.util.ArrayList;
import java.util.List;

public class MessageDownloadChunk implements IMessage, IMessageHandler<MessageDownloadChunk, IMessage> {
    public static final int ID = 3;
    public int baseX;
    public int baseY;
    public int baseZ;

    public short blocks[][][];
    public byte metadata[][][];
    public List<TileEntity> tileEntities;
    public List<Entity> entities;

    public MessageDownloadChunk() {
    }

    public MessageDownloadChunk(ISchematic schematic, int baseX, int baseY, int baseZ) {
        this.baseX = baseX;
        this.baseY = baseY;
        this.baseZ = baseZ;

        this.blocks = new short[Constants.SchematicChunk.WIDTH][Constants.SchematicChunk.HEIGHT][Constants.SchematicChunk.LENGTH];
        this.metadata = new byte[Constants.SchematicChunk.WIDTH][Constants.SchematicChunk.HEIGHT][Constants.SchematicChunk.LENGTH];
        this.tileEntities = new ArrayList<TileEntity>();
        this.entities = new ArrayList<Entity>();

        for (int x = 0; x < Constants.SchematicChunk.WIDTH; x++) {
            for (int y = 0; y < Constants.SchematicChunk.HEIGHT; y++) {
                for (int z = 0; z < Constants.SchematicChunk.LENGTH; z++) {
                    final Block block = schematic.getBlock(baseX + x, baseY + y, baseZ + z);
                    final int id = block != null ? block.blockID : 0;
                    this.blocks[x][y][z] = (short) id;
                    this.metadata[x][y][z] = (byte) schematic.getBlockMetadata(baseX + x, baseY + y, baseZ + z);
                    final TileEntity tileEntity = schematic.getTileEntity(baseX + x, baseY + y, baseZ + z);
                    if (tileEntity != null) {
                        this.tileEntities.add(tileEntity);
                    }
                }
            }
        }
    }

    private void copyToSchematic(final ISchematic schematic) {
        for (int x = 0; x < Constants.SchematicChunk.WIDTH; x++) {
            for (int y = 0; y < Constants.SchematicChunk.HEIGHT; y++) {
                for (int z = 0; z < Constants.SchematicChunk.LENGTH; z++) {
                    short id = this.blocks[x][y][z];
                    byte meta = this.metadata[x][y][z];
                    Block block = Block.blocksList[id];

                    schematic.setBlock(this.baseX + x, this.baseY + y, this.baseZ + z, block, meta);
                }
            }
        }

        for (TileEntity tileEntity : this.tileEntities) {
            schematic.setTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, tileEntity);
        }
    }

    @Override
    public IMessage fromBytes(ByteBuf buf) {
        this.baseX = buf.readShort();
        this.baseY = buf.readShort();
        this.baseZ = buf.readShort();

        this.blocks = new short[Constants.SchematicChunk.WIDTH][Constants.SchematicChunk.HEIGHT][Constants.SchematicChunk.LENGTH];
        this.metadata = new byte[Constants.SchematicChunk.WIDTH][Constants.SchematicChunk.HEIGHT][Constants.SchematicChunk.LENGTH];
        this.tileEntities = new ArrayList<TileEntity>();
        this.entities = new ArrayList<Entity>();

        for (int x = 0; x < Constants.SchematicChunk.WIDTH; x++) {
            for (int y = 0; y < Constants.SchematicChunk.HEIGHT; y++) {
                for (int z = 0; z < Constants.SchematicChunk.LENGTH; z++) {
                    this.blocks[x][y][z] = buf.readShort();
                    this.metadata[x][y][z] = buf.readByte();
                }
            }
        }

        final NBTTagCompound compound = buf.readTag();
        this.tileEntities = NBTHelper.readTileEntitiesFromCompound(compound, this.tileEntities);

        final NBTTagCompound compound2 = buf.readTag();
        this.entities = NBTHelper.readEntitiesFromCompound(compound2, this.entities);
        return this;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeShort(this.baseX);
        buf.writeShort(this.baseY);
        buf.writeShort(this.baseZ);

        for (int x = 0; x < Constants.SchematicChunk.WIDTH; x++) {
            for (int y = 0; y < Constants.SchematicChunk.HEIGHT; y++) {
                for (int z = 0; z < Constants.SchematicChunk.LENGTH; z++) {
                    buf.writeShort(this.blocks[x][y][z]);
                    buf.writeByte(this.metadata[x][y][z]);
                }
            }
        }

        final NBTTagCompound compound = NBTHelper.writeTileEntitiesToCompound(this.tileEntities);
        buf.writeTag(compound);

        final NBTTagCompound compound1 = NBTHelper.writeEntitiesToCompound(this.entities);
        buf.writeTag(compound1);
    }

    @Override
    public int id() {
        return ID;
    }

    @Override
    public IMessage onMessage(MessageDownloadChunk message, MessageContext ctx) {
        message.copyToSchematic(DownloadHandler.INSTANCE.schematic);

        return new MessageDownloadChunkAck(message.baseX, message.baseY, message.baseZ);
    }
}
