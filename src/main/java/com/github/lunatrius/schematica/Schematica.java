package com.github.lunatrius.schematica;

import btw.BTWAddon;
import com.github.lunatrius.schematica.network.PacketHandler;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import com.github.lunatrius.schematica.proxy.CommonProxy;
import com.github.lunatrius.schematica.proxy.ServerProxy;
import com.github.lunatrius.schematica.reference.Reference;
import com.github.lunatrius.schematica.util.DebugItem;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;

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
//    @Override
//    public void postInitialize() {
//        getProxy().postInitialize();
//    }


    //only to set up keybinds
//    @EventHandler
//    public void serverStarting(FMLServerStartingEvent event) {
//        proxy.serverStarting(event);
//    }
}
