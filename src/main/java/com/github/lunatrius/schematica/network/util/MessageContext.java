package com.github.lunatrius.schematica.network.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

public record MessageContext(NetHandler netHandler) {

    public NetServerHandler getServerHandler()
    {
        return (NetServerHandler) netHandler;
    }

    public static MessageContext fromPlayer(EntityPlayerMP emp) {
        return new MessageContext(emp.playerNetServerHandler);
    }

    @Environment(EnvType.CLIENT)
    public static MessageContext fromPlayerSP(EntityPlayer player) {
        return new MessageContext(((EntityClientPlayerMP) player).sendQueue);
    }
}
