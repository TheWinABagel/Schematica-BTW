package com.github.lunatrius.schematica.client.printer.registry;

import net.minecraft.src.Block;
import net.minecraft.src.BlockButton;
import net.minecraft.src.BlockChest;
import net.minecraft.src.BlockDispenser;
import net.minecraft.src.BlockEnderChest;
import net.minecraft.src.BlockFurnace;
import net.minecraft.src.BlockHopper;
import net.minecraft.src.BlockPistonBase;
import net.minecraft.src.BlockPumpkin;
import net.minecraft.src.BlockRotatedPillar;
import net.minecraft.src.BlockStairs;
import net.minecraft.src.BlockTorch;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PlacementRegistry {
    public static final PlacementRegistry INSTANCE = new PlacementRegistry();

    private final Map<Class<? extends Block>, PlacementData> classPlacementMap = new HashMap<Class<? extends Block>, PlacementData>();
    private final Map<Block, PlacementData> blockPlacementMap = new HashMap<Block, PlacementData>();
    private final Map<Item, PlacementData> itemPlacementMap = new HashMap<Item, PlacementData>();

    public void populatePlacementMaps() {
        this.classPlacementMap.clear();
        this.blockPlacementMap.clear();
        this.itemPlacementMap.clear();

        final IExtraClick extraClickDoubleSlab = (block, metadata) -> block.isOpaqueCube() ? 1 : 0;

        /**
         * minecraft
         */
        addPlacementMapping(BlockButton.class, new PlacementData(PlacementData.PlacementType.BLOCK, -1, -1, 3, 4, 1, 2).setMaskMeta(0x7));
        addPlacementMapping(BlockChest.class, new PlacementData(PlacementData.PlacementType.PLAYER, -1, -1, 3, 2, 5, 4));
        addPlacementMapping(BlockDispenser.class, new PlacementData(PlacementData.PlacementType.PISTON, 0, 1, 2, 3, 4, 5).setMaskMeta(0x7));
        addPlacementMapping(BlockEnderChest.class, new PlacementData(PlacementData.PlacementType.PLAYER, -1, -1, 3, 2, 5, 4));
        addPlacementMapping(BlockFurnace.class, new PlacementData(PlacementData.PlacementType.PLAYER, -1, -1, 3, 2, 5, 4));
        addPlacementMapping(BlockHopper.class, new PlacementData(PlacementData.PlacementType.BLOCK, 0, 1, 2, 3, 4, 5).setMaskMeta(0x7));
        addPlacementMapping(BlockPistonBase.class, new PlacementData(PlacementData.PlacementType.PISTON, 0, 1, 2, 3, 4, 5).setMaskMeta(0x7));
        addPlacementMapping(BlockPumpkin.class, new PlacementData(PlacementData.PlacementType.PLAYER, -1, -1, 0, 2, 3, 1).setMaskMeta(0xF));
        addPlacementMapping(BlockRotatedPillar.class, new PlacementData(PlacementData.PlacementType.BLOCK, 0, 0, 8, 8, 4, 4).setMaskMeta(0xC));
        addPlacementMapping(BlockStairs.class, new PlacementData(PlacementData.PlacementType.PLAYER, -1, -1, 3, 2, 1, 0).setOffset(0x4, 0.0f, 1.0f).setMaskMeta(0x3));
        addPlacementMapping(BlockTorch.class, new PlacementData(PlacementData.PlacementType.BLOCK, 5, -1, 3, 4, 1, 2).setMaskMeta(0xF));

        addPlacementMapping(Block.dirt, new PlacementData(PlacementData.PlacementType.BLOCK));
        addPlacementMapping(Block.planks, new PlacementData(PlacementData.PlacementType.BLOCK));
        addPlacementMapping(Block.sandStone, new PlacementData(PlacementData.PlacementType.BLOCK));
        addPlacementMapping(Block.cloth, new PlacementData(PlacementData.PlacementType.BLOCK));
        addPlacementMapping(Block.plantYellow, new PlacementData(PlacementData.PlacementType.BLOCK));
        addPlacementMapping(Block.plantRed, new PlacementData(PlacementData.PlacementType.BLOCK));
        addPlacementMapping(Block.stoneDoubleSlab, new PlacementData(PlacementData.PlacementType.BLOCK).setExtraClick(extraClickDoubleSlab));
        addPlacementMapping(Block.stoneSingleSlab, new PlacementData(PlacementData.PlacementType.BLOCK).setOffset(0x8, 0.0f, 1.0f).setMaskMeta(0x7));
//        addPlacementMapping(Block.stained_glass, new PlacementData(PlacementData.PlacementType.BLOCK));
        addPlacementMapping(Block.ladder, new PlacementData(PlacementData.PlacementType.BLOCK, -1, -1, 3, 2, 5, 4));
        addPlacementMapping(Block.lever, new PlacementData(PlacementData.PlacementType.BLOCK, -1, -1, 3, 4, 1, 2).setMaskMeta(0x7));
        addPlacementMapping(Block.snow, new PlacementData(PlacementData.PlacementType.BLOCK));
        addPlacementMapping(Block.trapdoor, new PlacementData(PlacementData.PlacementType.BLOCK, -1, -1, 1, 0, 3, 2).setOffset(0x8, 0.0f, 1.0f).setMaskMeta(0x3));
//        addPlacementMapping(Block.monster_egg, new PlacementData(PlacementData.PlacementType.BLOCK));
        addPlacementMapping(Block.stoneBrick, new PlacementData(PlacementData.PlacementType.BLOCK));
        addPlacementMapping(Block.tripWireSource, new PlacementData(PlacementData.PlacementType.BLOCK, -1, -1, 0, 2, 3, 1).setMaskMeta(0x3));
        addPlacementMapping(Block.blockNetherQuartz, new PlacementData(PlacementData.PlacementType.BLOCK));
        addPlacementMapping(Block.fenceGate, new PlacementData(PlacementData.PlacementType.PLAYER, -1, -1, 2, 0, 1, 3).setMaskMeta(0x3));
        addPlacementMapping(Block.woodDoubleSlab, new PlacementData(PlacementData.PlacementType.BLOCK));
        addPlacementMapping(Block.woodSingleSlab, new PlacementData(PlacementData.PlacementType.BLOCK).setOffset(0x8, 0.0f, 1.0f).setMaskMeta(0x7).setExtraClick(extraClickDoubleSlab));
        addPlacementMapping(Block.anvil, new PlacementData(PlacementData.PlacementType.PLAYER, -1, -1, 1, 3, 0, 2).setMaskMeta(0x3));
        addPlacementMapping(Block.stainedClay, new PlacementData(PlacementData.PlacementType.BLOCK));
        addPlacementMapping(Block.carpet, new PlacementData(PlacementData.PlacementType.BLOCK));
//        addPlacementMapping(Block.stained_glass_pane, new PlacementData(PlacementData.PlacementType.BLOCK));
        addPlacementMapping(Item.doorWood, new PlacementData(PlacementData.PlacementType.PLAYER, -1, -1, 3, 1, 2, 0).setMaskMeta(0x7));
        addPlacementMapping(Item.doorIron, new PlacementData(PlacementData.PlacementType.PLAYER, -1, -1, 3, 1, 2, 0).setMaskMeta(0x7));
        addPlacementMapping(Item.redstoneRepeater, new PlacementData(PlacementData.PlacementType.PLAYER, -1, -1, 0, 2, 3, 1).setMaskMeta(0x3));
        addPlacementMapping(Item.comparator, new PlacementData(PlacementData.PlacementType.PLAYER, -1, -1, 0, 2, 3, 1).setMaskMeta(0x3));
    }

    public PlacementData addPlacementMapping(Class<? extends Block> clazz, PlacementData data) {
        if (clazz == null || data == null) {
            return null;
        }

        return this.classPlacementMap.put(clazz, data);
    }

    public PlacementData addPlacementMapping(Block block, PlacementData data) {
        if (block == null || data == null) {
            return null;
        }

        return this.blockPlacementMap.put(block, data);
    }

    public PlacementData addPlacementMapping(Item item, PlacementData data) {
        if (item == null || data == null) {
            return null;
        }

        return this.itemPlacementMap.put(item, data);
    }

    public PlacementData getPlacementData(Block block, ItemStack itemStack) {
        final PlacementData placementDataItem = this.itemPlacementMap.get(itemStack.getItem());
        if (placementDataItem != null) {
            return placementDataItem;
        }

        final PlacementData placementDataBlock = this.blockPlacementMap.get(block);
        if (placementDataBlock != null) {
            return placementDataBlock;
        }

        for (Class<? extends Block> clazz : this.classPlacementMap.keySet()) {
            if (clazz.isInstance(block)) {
                return this.classPlacementMap.get(clazz);
            }
        }

        return null;
    }

    static {
        INSTANCE.populatePlacementMaps();
    }
}
