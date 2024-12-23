package com.github.lunatrius.schematica.client.util;


import net.fabricmc.example.mixin.access.MinecraftAccessor;
import net.minecraft.src.Minecraft;

public class WorldServerName {

    /**
     * Gets the world name if in singleplayer, or the name in server list when in multiplayer
     * 
     * @param mc {@link Minecraft}
     * @return {@link String} world name or server name
     */
    public static String worldServerName(Minecraft mc) {
        String WorldOrServerName;
        if (mc.isSingleplayer()) {
            WorldOrServerName = Minecraft.getMinecraft().getIntegratedServer().getWorldName();
        } else {
            // Gets the server data, only works if you're playing on a server. if you're using direct connect the name
            // will be "Minecraft Server". Crashes if singleplayer
            WorldOrServerName = ((MinecraftAccessor) mc).getCurrentServerData().serverName;
        }
        return WorldOrServerName;
    }
}
