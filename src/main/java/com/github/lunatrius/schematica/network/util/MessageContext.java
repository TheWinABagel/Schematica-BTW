package com.github.lunatrius.schematica.network.util;

import net.fabricmc.api.EnvType;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.NetHandler;
import net.minecraft.src.NetServerHandler;

public record MessageContext(NetHandler netHandler, EnvType side) {

    public NetServerHandler getServerHandler()
    {
        return (NetServerHandler) netHandler;
    }

    public NetClientHandler getClientHandler()
    {
        return (NetClientHandler) netHandler;
    }
}
