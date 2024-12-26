package com.github.lunatrius.schematica.client.util;

import com.github.lunatrius.core.entity.EntityHelper;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import com.github.lunatrius.schematica.reference.Reference;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.WorldClient;

import java.util.ArrayList;
import java.util.List;

public class BlockList {
    public List<WrappedItemStack> getList(final EntityPlayer player, final SchematicWorld world, final WorldClient mcWorld) {
        final List<WrappedItemStack> blockList = new ArrayList<>();

        if (world == null) {
            return blockList;
        }

        for (int y = 0; y < world.getHeight(); y++) {
            for (int x = 0; x < world.getWidth(); x++) {
                for (int z = 0; z < world.getLength(); z++) {
                    if (world.isRenderingLayer && y != world.renderingLayer) {
                        continue;
                    }

                    final Block block = world.getBlock(x, y, z);

                    if (block == null || world.isAirBlock(x, y, z)) {
                        continue;
                    }

                    final int wx = world.position.x + x;
                    final int wy = world.position.y + y;
                    final int wz = world.position.z + z;
                    final Block mcBlock = Block.blocksList[mcWorld.getBlockId(wx, wy, wz)];
                    final boolean isPlaced = block == mcBlock && world.getBlockMetadata(x, y, z) == mcWorld.getBlockMetadata(wx, wy, wz);

                    ItemStack stack = null;

                    try {
                        final int idPicked = block.idPicked(world, x, y ,z);
                        if (idPicked == 0) {
                            Reference.logger.debug("Block {}'s picked id is 0, skipping", block);
                            continue;
                        }
                        final int meta = block.getDamageValue(world, x, y, z);
                        stack = new ItemStack(idPicked, 1, meta);
                    } catch (final Exception e) {
                        Reference.logger.debug("Could not get the pick block for: {}", block, e);
                    }

                    if (stack == null || stack.getItem() == null) {
                        Reference.logger.debug("Could not find the item for: {}", block);
                        continue;
                    }

                    final WrappedItemStack wrappedItemStack = findOrCreateWrappedItemStackFor(blockList, stack);
                    if (isPlaced) {
                        wrappedItemStack.placed++;
                    }
                    wrappedItemStack.total++;
                }
            }
        }

        for (WrappedItemStack wrappedItemStack : blockList) {
            wrappedItemStack.creative = player.capabilities.isCreativeMode;
            wrappedItemStack.inventory = EntityHelper.getItemCountInInventory(player.inventory, wrappedItemStack.itemStack.getItem(), wrappedItemStack.itemStack.getItemDamage());
        }

        return blockList;
    }

    private WrappedItemStack findOrCreateWrappedItemStackFor(final List<WrappedItemStack> blockList, final ItemStack itemStack) {
        for (final WrappedItemStack wrappedItemStack : blockList) {
            if (wrappedItemStack.itemStack.isItemEqual(itemStack)) {
                return wrappedItemStack;
            }
        }

        final WrappedItemStack wrappedItemStack = new WrappedItemStack(itemStack.copy());
        blockList.add(wrappedItemStack);
        return wrappedItemStack;
    }

    public static class WrappedItemStack {
        public ItemStack itemStack;
        public int placed;
        public int total;
        public int inventory;
        public boolean creative = false;

        public WrappedItemStack(final ItemStack itemStack) {
            this(itemStack, 0, 0);
        }

        public WrappedItemStack(final ItemStack itemStack, final int placed, final int total) {
            this.itemStack = itemStack;
            this.placed = placed;
            this.total = total;
        }

        public String getItemStackDisplayName() {
            return this.itemStack.getItem().getItemStackDisplayName(this.itemStack);
        }

        public String getTotal() {
            return String.valueOf(total);
        }

        public String getMissingNoColor() {
            final int need = this.total - this.placed;
            return String.format("%d", need);
        }

        public String getMissing() {
            final int need = this.total - this.placed;
            char color = this.placed >= this.total ? 'a' : this.inventory > need ? '6' : 'c';
            return String.format("§%c%s", color, need);
        }

        public String getAvailable() {
            int inv = Math.max(this.inventory, 0);
            final char color = inv >= this.total - this.placed ? 'a' : 'c';
            return "§%c%d".formatted(color, inv);
        }

        public String calculateTotal() {
            final int stackSize = this.itemStack.getMaxStackSize();
//            final int amount = this.total - this.inventory - this.placed;
//            if (amount < stackSize) {
//                return String.format("%d", amount);
//            } else {
            final int amountstack = this.total / stackSize;
            final int amountremainder = this.total % stackSize;
            final double chestPercent = amountstack / 54d;
            return String.format("%d = %d x %d + %d = %.2f DC", this.total, amountstack, stackSize, amountremainder, chestPercent);
//            }
        }
    }
}
