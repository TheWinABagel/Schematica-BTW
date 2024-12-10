package com.github.lunatrius.schematica.handler;

import com.github.lunatrius.schematica.network.PacketHandler;
import com.github.lunatrius.schematica.network.message.MessageCapabilities;
import com.github.lunatrius.schematica.reference.Reference;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;

public class PlayerHandler {
    public static final PlayerHandler INSTANCE = new PlayerHandler();

    private PlayerHandler() {}

    public void onPlayerLoggedIn(EntityPlayer player) {
        if (player instanceof EntityPlayerMP playerMP) {
            try {
                PacketHandler.INSTANCE.sendTo(new MessageCapabilities(ConfigurationHandler.printerEnabled, ConfigurationHandler.saveEnabled, ConfigurationHandler.loadEnabled), playerMP);
            } catch (Exception ex) {
                Reference.logger.error("Failed to send capabilities!", ex);
            }
        }
    }

    public void onPlayerLoggedOut(EntityPlayer player) {
        if (player instanceof EntityPlayerMP) {
            DownloadHandler.INSTANCE.transferMap.remove(player);
        }
    }
}
