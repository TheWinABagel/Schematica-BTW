package com.github.lunatrius.schematica.debug;

import com.github.lunatrius.schematica.client.gui.control.GuiSchematicMaterials;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import net.fabricmc.example.ForgeDirection;
import net.minecraft.src.*;

public class DebugItem extends Item {
    private static final String LINE = "----------------------------\n";
    public DebugItem(int par1) {
        super(par1);
        setUnlocalizedName("debug");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (world.isRemote) {
            if (ClientProxy.schematic == null) {
                new DebugSchematicLoader();
                Minecraft.getMinecraft().displayGuiScreen(new GuiSchematicMaterials(null));
                player.addChatMessage("No Schematic! Loading first schematic...");
                return stack;
            }
            MovingObjectPosition pos = ClientProxy.movingObjectPosition;
            if (pos == null) {
                player.addChatMessage("Not a Schematic Block");
                return stack;
            }
            Block schemBlock = ClientProxy.schematic.getBlock(pos.blockX, pos.blockY, pos.blockZ);
            int schemMeta = ClientProxy.schematic.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ);
            if (schemBlock != null) {
                player.addChatMessage(" "+LINE + "Schematic block: " + schemBlock.getLocalizedName() + ", id: " + schemBlock.blockID + "\nMeta: " + schemMeta + ", facing?: " + ForgeDirection.getOrientation(schemBlock.getFacing(schemMeta)) + "\n" + LINE);
            }
        }
        return stack;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int facing, float clickX, float clickY, float clickZ) {
        if (world.isRemote) {
            Block block = Block.blocksList[world.getBlockId(x, y, z)];
            int meta = world.getBlockMetadata(x, y, z);
            if (block != null) {
                player.addChatMessage(" "+LINE + "Real block: " + block.getLocalizedName() + ", id: " + block.blockID + "\nMeta: " + meta + ", facing?: " + ForgeDirection.getOrientation(block.getFacing(meta)) + "\n" + LINE);
            }
        }
        return false;
    }
}
