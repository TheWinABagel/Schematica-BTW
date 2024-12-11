package com.github.lunatrius.schematica.network.util;

import net.minecraft.src.Packet250CustomPayload;

public interface IMessage {

    void fromBytes(ByteBuf buf);

    void toBytes(ByteBuf buf);

    default Packet250CustomPayload toPacket() {
        return new Packet250CustomPayload();
    }
}
