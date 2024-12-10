package com.github.lunatrius.schematica.client.util;

import com.github.lunatrius.schematica.client.world.SchematicWorld;
import com.github.lunatrius.schematica.reference.Reference;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Vec3;

public class BlockToItemStack {
    public static ItemStack getItemStack(final EntityPlayer player, final Block block, final SchematicWorld world, final int x, final int y, final int z) {
        try {
//            final ItemStack itemStack = block.getPickBlock(new MovingObjectPosition(x, y, z, 0, Vec3.createVectorHelper(0, 0, 0)), world, x, y, z, player);
            ItemStack itemStack = new ItemStack(block.idPicked(world, x, y, z), 1, 0);
            if (itemStack != null) {
                return itemStack;
            }
        } catch (final Exception e) {
            Reference.logger.debug("Could not get the pick block for: {}", block, e);
        }

        return null;
    }
}
