package com.github.lunatrius.schematica.network.util;

import emi.shims.java.net.minecraft.network.PacketByteBuf;
import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

public interface ByteBuf {

    public byte readByte();

    public ItemStack readItemStack();

    public String readString();

    public int readInt();

    public int readVarInt();

    public short readShort();

    public boolean readBoolean();

    public NBTTagCompound readTag();

    public void writeByte(int var1);

    public void writeItemStack(ItemStack var1);

    public void writeString(String var1);

    public void writeInt(int var1);

    public void writeVarInt(int var1);

    public void writeShort(int var1);

    public void writeBoolean(boolean var1);

    public void writeTag(NBTTagCompound compound);

    public static ByteBuf out(final DataOutputStream out) {
        return new ByteBuf() {
            @Override
            public byte readByte() {
                throw new UnsupportedOperationException();
            }

            @Override
            public ItemStack readItemStack() {
                throw new UnsupportedOperationException();
            }

            @Override
            public String readString() {
                throw new UnsupportedOperationException();
            }

            @Override
            public int readInt() {
                throw new UnsupportedOperationException();
            }

            @Override
            public int readVarInt() {
                throw new UnsupportedOperationException();
            }

            @Override
            public short readShort() {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean readBoolean() {
                throw new UnsupportedOperationException();
            }

            @Override
            public NBTTagCompound readTag() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void writeVarInt(int value) {
                while ((value & 0xFFFFFF80) != 0) {
                    this.writeByte(value & 0x7F | 0x80);
                    value >>>= 7;
                }
                this.writeByte(value);
            }

            @Override
            public void writeShort(int s) {
                try {
                    out.writeShort(s);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public void writeString(String str) {
                byte[] bys = str.getBytes(StandardCharsets.UTF_8);
                this.writeVarInt(bys.length);
                try {
                    out.write(bys);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public void writeItemStack(ItemStack stack) {
                try {
                    Packet.writeItemStack(stack, out);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public void writeInt(int i) {
                try {
                    out.writeInt(i);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public void writeByte(int b) {
                try {
                    out.writeByte(b);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public void writeBoolean(boolean b) {
                this.writeByte(b ? 1 : 0);
            }

            @Override
            public void writeTag(NBTTagCompound tag) {
                try {
                    Packet.writeNBTTagCompound(tag, out);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        };
    }

    public static ByteBuf in(final DataInputStream in) {
        return new ByteBuf() {
            @Override
            public int readVarInt() {
                byte b;
                int i = 0;
                int j = 0;
                do {
                    b = this.readByte();
                    i |= (b & 0x7F) << j++ * 7;
                    if (j <= 5) continue;
                    throw new RuntimeException("VarInt too big");
                } while ((b & 0x80) == 128);
                return i;
            }

            @Override
            public String readString() {
                int len = this.readVarInt();
                byte[] bys = new byte[len];
                try {
                    in.readFully(bys);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
                return new String(bys, StandardCharsets.UTF_8);
            }

            @Override
            public ItemStack readItemStack() {
                try {
                    return Packet.readItemStack(in);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public int readInt() {
                try {
                    return in.readInt();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public byte readByte() {
                try {
                    return in.readByte();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public boolean readBoolean() {
                try {
                    return in.readByte() != 0;
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public short readShort() {
                try {
                    return in.readShort();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public NBTTagCompound readTag() {
                try {
                    return Packet.readNBTTagCompound(in);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public void writeShort(int var1) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void writeTag(NBTTagCompound compound) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void writeVarInt(int value) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void writeString(String str) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void writeItemStack(ItemStack stack) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void writeInt(int i) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void writeByte(int b) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void writeBoolean(boolean b) {
                throw new UnsupportedOperationException();
            }
        };
    }
}
