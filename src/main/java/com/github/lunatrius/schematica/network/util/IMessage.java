package com.github.lunatrius.schematica.network.util;

import com.github.lunatrius.schematica.reference.Reference;
import net.minecraft.src.Packet250CustomPayload;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface IMessage {

    IMessage fromBytes(ByteBuf buf);

    void toBytes(ByteBuf buf);

    int id();

    default Packet250CustomPayload toPacket() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            ByteBuf buf = ByteBuf.out(dos);
            dos.writeInt(id());
            toBytes(buf);
            return new Packet250CustomPayload(Reference.PACKET_ID, baos.toByteArray());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
