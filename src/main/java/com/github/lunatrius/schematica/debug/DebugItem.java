package com.github.lunatrius.schematica.debug;

import com.github.lunatrius.schematica.proxy.ClientProxy;
import com.github.lunatrius.schematica.util.ChatUtils;
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
//            if (ClientProxy.schematic == null) {
//                new DebugSchematicLoader();
//                Minecraft.getMinecraft().displayGuiScreen(new GuiSchematicMaterials(null));
//                player.addChatMessage("No Schematic! Loading first schematic...");
//                return stack;
//            }
            MovingObjectPosition pos = ClientProxy.movingObjectPosition;
            if (pos == null) {
                ChatUtils.printToChatWhileRemovingLast("Not a Schematic Block", "Not a Schematic Block");
                return stack;
            }
            Block schemBlock = ClientProxy.schematic.getBlock(pos.blockX, pos.blockY, pos.blockZ);
            int schemMeta = ClientProxy.schematic.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ);
            if (schemBlock != null) {
                String sb = "Schematic block: " + schemBlock.getLocalizedName() + "\n Schem Id: " + schemBlock.blockID + "\n Schem Meta: " + schemMeta + ", facing?: " + ForgeDirection.getOrientation(schemBlock.getFacing(schemMeta));
                ChatUtils.printToChatWhileRemovingLast(sb, "Schem Meta");

//                player.addChatMessage(" "+LINE + "Schematic block: " + schemBlock.getLocalizedName() + ", id: " + schemBlock.blockID + "\nMeta: " + schemMeta + ", facing?: " + ForgeDirection.getOrientation(schemBlock.getFacing(schemMeta)) + "\n" + LINE);
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
                String sb ="Real block: " + block.getLocalizedName() + "\n Real Id: " + block.blockID + "\n Real Meta: " + meta + ", facing?: " + ForgeDirection.getOrientation(block.getFacing(meta));
                ChatUtils.printToChatWhileRemovingLast(sb, "Real Meta");

//                player.addChatMessage(" "+LINE + "Real block: " + block.getLocalizedName() + ", id: " + block.blockID + "\nMeta: " + meta + ", facing?: " + ForgeDirection.getOrientation(block.getFacing(meta)) + "\n" + LINE);
            }
        }
        /*if (!world.isRemote) {
            int meta = world.getBlockMetadata(x, y, z);
            int newMeta = cycle(meta, 16);
            world.SetBlockMetadataWithNotify(x, y, z, newMeta, 2);
            Block block = Block.blocksList[world.getBlockId(x,y,z)];
            if (block == null) {
                player.addChatMessage("Block is null! wat");
                return false;
            }
            player.addChatMessage("Setting meta for %s from %d to %d".formatted(block.getLocalizedName(), meta, newMeta));
        }*/
        return false;
    }

    private int cycle(int old, int to) {
        if (old <= to) {
            return ++old;
        }
        else return 0;
    }
}
