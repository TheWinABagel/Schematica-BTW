package com.github.lunatrius.schematica.handler.client;

import com.github.lunatrius.schematica.client.world.SchematicUpdater;
import com.github.lunatrius.schematica.reference.Reference;
import net.minecraft.src.IWorldAccess;
import net.minecraft.src.World;
import net.minecraft.src.WorldClient;

public class WorldHandler {
//    @SubscribeEvent
//    public void onLoad(final WorldEvent.Load event) {
//        if (event.world.isRemote) {
//            addWorldAccess(event.world, SchematicUpdater.INSTANCE);
//        }
//    }

    public static void onLoad(WorldClient world) {
        if (world.isRemote) {
            addWorldAccess(world, SchematicUpdater.INSTANCE);
        }
    }
    //todo check if all of the load/unload events need to be implemented
//    @SubscribeEvent
//    public void onUnload(final WorldEvent.Unload event) {
//        if (event.world.isRemote) {
//            removeWorldAccess(event.world, SchematicUpdater.INSTANCE);
//        }
//    }

    public static void onUnload(WorldClient world) {
        if (world.isRemote) {
            removeWorldAccess(world, SchematicUpdater.INSTANCE);
        }
    }

    public static void addWorldAccess(final World world, final IWorldAccess schematic) {
        if (world != null && schematic != null) {
            Reference.logger.debug("Adding world access to {}", world);
            world.addWorldAccess(schematic);
        }
    }

    public static void removeWorldAccess(final World world, final IWorldAccess schematic) {
        if (world != null && schematic != null) {
            Reference.logger.debug("Removing world access from {}", world);
            world.removeWorldAccess(schematic);
        }
    }
}
