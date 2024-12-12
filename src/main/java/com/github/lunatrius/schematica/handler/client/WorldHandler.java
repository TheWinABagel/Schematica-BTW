package com.github.lunatrius.schematica.handler.client;

import com.github.lunatrius.schematica.client.world.SchematicUpdater;
import com.github.lunatrius.schematica.reference.Reference;
import net.minecraft.src.IWorldAccess;
import net.minecraft.src.World;

public class WorldHandler {

    public static void onLoad(World world) {
        if (world != null && world.isRemote) {
            addWorldAccess(world, SchematicUpdater.INSTANCE);
        }
    }

    public static void onUnload(World world) {
        if (world != null && world.isRemote) {
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
