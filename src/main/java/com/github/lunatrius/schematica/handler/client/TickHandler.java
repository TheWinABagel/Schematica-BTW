package com.github.lunatrius.schematica.handler.client;

import com.github.lunatrius.schematica.Schematica;
import com.github.lunatrius.schematica.client.printer.SchematicPrinter;
import com.github.lunatrius.schematica.client.renderer.RendererSchematicChunk;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import com.github.lunatrius.schematica.handler.ConfigurationHandler;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import com.github.lunatrius.schematica.reference.Reference;
import net.minecraft.src.Minecraft;

public class TickHandler {
    public static final TickHandler INSTANCE = new TickHandler();

    private final Minecraft minecraft = Minecraft.getMinecraft();

    private int ticks = -1;

    private TickHandler() {}

    //todo Client setting reset, mod will likely not function without it, tail of constructor or NetClientHandler
/*    @SubscribeEvent
    public void onClientConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        Reference.logger.info("Scheduling client settings reset.");
        ClientProxy.isPendingReset = true;
    }

    @SubscribeEvent
    public void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        Reference.logger.info("Scheduling client settings reset.");
        ClientProxy.isPendingReset = true;
    }*/

    public void onClientTick() {
        this.minecraft.mcProfiler.startSection("schematica");
        SchematicWorld schematic = ClientProxy.schematic;
        if (this.minecraft.thePlayer != null && schematic != null && schematic.isRendering) {
            this.minecraft.mcProfiler.startSection("printer");
            SchematicPrinter printer = SchematicPrinter.INSTANCE;
            if (printer.isEnabled() && printer.isPrinting() && this.ticks-- < 0) {
                this.ticks = ConfigurationHandler.placeDelay;

                printer.print();
            }

            this.minecraft.mcProfiler.endStartSection("canUpdate");
            RendererSchematicChunk.setCanUpdate(true);

            this.minecraft.mcProfiler.endSection();
        }

        if (ClientProxy.isPendingReset) {
            Schematica.getProxy().resetSettings();
            ClientProxy.isPendingReset = false;
        }

        this.minecraft.mcProfiler.endSection();
    }
}
