package com.github.lunatrius.schematica;

import api.BTWAddon;
import com.github.lunatrius.schematica.debug.DebugItem;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import com.github.lunatrius.schematica.proxy.CommonProxy;
import com.github.lunatrius.schematica.proxy.ServerProxy;
import com.github.lunatrius.schematica.reference.Reference;
import net.fabricmc.example.debug.DebugWorldHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.NetServerHandler;

public class Schematica extends BTWAddon {
    public static Schematica instance = new Schematica();

    @Override
    public void postSetup() {
    }

    public Schematica() {
        this.modID = Reference.MODID;
    }

    public static CommonProxy getProxy() {
        if (MinecraftServer.getIsServer()) {
            return new ServerProxy();
        }
        else return new ClientProxy();
    }

    //todo proxy might not work on dedicated servers properly

//    @EventHandler
//    public void preInit(FMLPreInitializationEvent event) {
//        getProxy().preInitialize();
//    }

    @Override
    public void preInitialize() {
        getProxy().preInitialize();
    }

    //    @EventHandler
//    public void init(FMLInitializationEvent event) {
//        proxy.init(event);
//    }

    @Override
    public void initialize() {
        getProxy().init();
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            //todo config for debug item
            new DebugItem(22000);
        }
    }

//    @EventHandler
//    public void postInit(FMLPostInitializationEvent event) {
//        proxy.postInit(event);
//    }


    //not needed anymore, only for forge multipart
    @Override
    public void postInitialize() {

//        getProxy().postInitialize();
    }

    @Override
    public void serverPlayerConnectionInitialized(NetServerHandler serverHandler, EntityPlayerMP player) {
        System.out.println("Server player connection initalized " + player);
        if (player.worldObj.getWorldInfo().getTerrainType() == DebugWorldHelper.DEBUG_WORLD_TYPE) {
            System.out.println("debug world type!");

            serverHandler.setPlayerLocation(0d, 3d, 0d, player.rotationYaw, player.rotationPitch);
            player.capabilities.allowFlying = true;
            player.capabilities.isFlying = true;
//            player.setPositionAndUpdate(1d, 1d, 1d);
        }
    }

//only to set up keybinds
//    @EventHandler
//    public void serverStarting(FMLServerStartingEvent event) {
//        proxy.serverStarting(event);
//    }
}
