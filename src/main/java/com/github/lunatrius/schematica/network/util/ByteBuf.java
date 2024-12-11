package com.github.lunatrius.schematica.network.util;

import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
//todo currently no op
public interface ByteBuf {

    public byte readByte();

    public ItemStack readItemStack();

    public String readString();

    public int readInt();

    public int readVarInt();

    public short readShort();

    public boolean readBoolean();

    public void writeByte(int var1);

    public void writeItemStack(ItemStack var1);

    public void writeString(String var1);

    public void writeInt(int var1);

    public void writeVarInt(int var1);

    public void writeShort(int var1);

    public void writeBoolean(boolean var1);

    public NBTTagCompound readTag();

    public void writeTag(NBTTagCompound compound);
}
