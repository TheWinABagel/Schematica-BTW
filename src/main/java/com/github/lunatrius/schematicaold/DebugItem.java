package com.github.lunatrius.schematicaold;

import net.minecraft.src.*;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class DebugItem extends Item {
    public DebugItem(int par1) {
        super(par1);
        setUnlocalizedName("schematica.item.debug");
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int facing, float clickX, float clickY, float clickZ) {
        if (world.isRemote) return true;
        int id = world.getBlockId(x, y, z);
        Block block = Block.blocksList[id];
        if (block == null) return false;
        String test = "Block: " + block.getLocalizedName() + "\nID: " + id + "\nMetadata: " + world.getBlockMetadata(x, y, z);
        player.addChatMessage(test);
        Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(
                        new StringSelection(test),
                        null
                );
        return false;
    }
}
