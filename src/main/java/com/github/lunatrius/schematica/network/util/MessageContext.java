package com.github.lunatrius.schematica.network.util;

import net.minecraft.src.NetClientHandler;
import net.minecraft.src.NetHandler;
import net.minecraft.src.NetServerHandler;

public record MessageContext(NetHandler netHandler) {

    public NetServerHandler getServerHandler()
    {
        return (NetServerHandler) netHandler;
    }

    public NetClientHandler getClientHandler()
    {
        return (NetClientHandler) netHandler;
    }
}
