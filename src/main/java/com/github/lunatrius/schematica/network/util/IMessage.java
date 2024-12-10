package com.github.lunatrius.schematica.network.util;

import net.minecraft.src.Packet250CustomPayload;

public interface IMessage {

    default Packet250CustomPayload toPacket() {
        return new Packet250CustomPayload();
    }
}
