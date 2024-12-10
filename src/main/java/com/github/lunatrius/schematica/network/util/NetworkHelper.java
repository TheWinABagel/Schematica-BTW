package com.github.lunatrius.schematica.network.util;

import net.minecraft.src.EntityPlayerMP;

public class NetworkHelper {

    public static final NetworkHelper INSTANCE = new NetworkHelper();

    private NetworkHelper() {
    }

    public void sendTo(IMessage message, EntityPlayerMP player)
    {

    }
}
