package com.github.lunatrius.schematica.client.printer.registry;

import btw.block.BTWBlocks;
import btw.block.blocks.*;
import net.minecraft.src.*;
import api.block.blocks.*;

import java.util.HashMap;
import java.util.Map;

public class PlacementRegistry {
    public static final PlacementRegistry INSTANCE = new PlacementRegistry();

    private final Map<Class<? extends Block>, PlacementData> classPlacementMap = new HashMap<>();
    private final Map<Block, PlacementData> blockPlacementMap = new HashMap<>();
    private final Map<Item, PlacementData> itemPlacementMap = new HashMap<>();
    private final Map<ExtendedPlacementData.Data, PlacementData> extendedPlacementMap = new HashMap<>();

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

        //todo better than wolves blocks

        addExtendedPlacementMapping(SidingAndCornerBlock.class, new ExtendedPlacementData(PlacementData.PlacementType.BLOCK, new int[] {2, 0, 6, 4, 10, 8}, 0, 2, 4, 6, 8, 10)/*.setOffset(0x2, 0.0f, 1.0f)*/);
        addExtendedPlacementMapping(SidingAndCornerBlock.class, (ExtendedPlacementData) new ExtendedPlacementData(PlacementData.PlacementType.BLOCK, new int[] {1, 3, -1, -1, -1, -1}, 3, 1, 7, 5, 11, 9, 15)/*.setOffset(0x2, 0.0f, 1.0f)*//*.setMaskMeta(0x2)*/);
        addExtendedPlacementMapping(SidingAndCornerAndDecorativeWallBlock.class, (ExtendedPlacementData) new ExtendedPlacementData(PlacementData.PlacementType.BLOCK, new int[] {14, 13, 13, 13, 13, 13}, 13, 14).setOffset(0x1, 0.0f, 1.0f)/*.setMaskMeta(0x2)*/);
        addPlacementMapping(AxleBlock.class, new PlacementData(PlacementData.PlacementType.BLOCK, 0, 0, 8, 8, 4, 4).setMaskMeta(0x3));
        addPlacementMapping(GearBoxBlock.class, new PlacementData(PlacementData.PlacementType.BTW_ORIENTATION_BASED_REVERSED, 0, 1, 2, 3, 4, 5).setMaskMeta(0x7));
        //        addPlacementMapping(SidingAndCornerAndDecorativeWallBlock.class, new PlacementData(PlacementData.PlacementType.BLOCK, 0, 2, 4, 6, 8, 10).setOffset(0x4, 0.0f, 1.0f).setMaskMeta(0x2));
        addPlacementMapping(StairsBlockBase.class, new PlacementData(PlacementData.PlacementType.PLAYER, -1, -1, 3, 2, 1, 0).setOffset(0x4, 0.0f, 1.0f).setMaskMeta(0x3));

        addPlacementMapping(BTWBlocks.stoneDoubleSlab, new PlacementData(PlacementData.PlacementType.BLOCK).setExtraClick(extraClickDoubleSlab));
        addPlacementMapping(BTWBlocks.stoneSlab, new PlacementData(PlacementData.PlacementType.BLOCK).setOffset(0x8, 0.0f, 1.0f).setMaskMeta(0x7));
        addPlacementMapping(BTWBlocks.stoneBrickDoubleSlab, new PlacementData(PlacementData.PlacementType.BLOCK).setExtraClick(extraClickDoubleSlab));
        addPlacementMapping(BTWBlocks.stoneBrickSlab, new PlacementData(PlacementData.PlacementType.BLOCK).setOffset(0x8, 0.0f, 1.0f).setMaskMeta(0x7));
        addPlacementMapping(BTWBlocks.cobblestoneDoubleSlab, new PlacementData(PlacementData.PlacementType.BLOCK).setExtraClick(extraClickDoubleSlab));
        addPlacementMapping(BTWBlocks.cobblestoneSlab, new PlacementData(PlacementData.PlacementType.BLOCK).setOffset(0x8, 0.0f, 1.0f).setMaskMeta(0x7));
        addPlacementMapping(Block.woodDoubleSlab, new PlacementData(PlacementData.PlacementType.BLOCK).setExtraClick(extraClickDoubleSlab));
        PlacementRegistry.INSTANCE.addPlacementMapping(Block.woodSingleSlab, new PlacementData(PlacementData.PlacementType.BLOCK).setOffset(0x8, 0.0f, 1.0f).setMaskMeta(0x7));

//        addPlacementMapping(BTWBlocks.stoneDoubleSlab, new PlacementData(PlacementData.PlacementType.BLOCK).setExtraClick(extraClickDoubleSlab));
//        addPlacementMapping(BTWBlocks.boneSlab, new PlacementData(PlacementData.PlacementType.BLOCK).setOffset(0x1, 0.0f, 1.0f)/*.setMaskMeta(0x7)*/);
//        addPlacementMapping(BTWBlocks.solidSnowSlab, new PlacementData(PlacementData.PlacementType.BLOCK).setOffset(0x1, 0.0f, 1.0f)/*.setMaskMeta(0x7)*/);
//        addPlacementMapping(BTWBlocks.spiderEyeSlab, new PlacementData(PlacementData.PlacementType.BLOCK).setOffset(0x1, 0.0f, 1.0f)/*.setMaskMeta(0x7)*/);
//        addPlacementMapping(BTWBlocks.creeperOysterSlab, new PlacementData(PlacementData.PlacementType.BLOCK).setOffset(0x1, 0.0f, 1.0f)/*.setMaskMeta(0x7)*/);
//        addPlacementMapping(BTWBlocks.rottenFleshSlab, new PlacementData(PlacementData.PlacementType.BLOCK).setOffset(0x1, 0.0f, 1.0f)/*.setMaskMeta(0x7)*/);
//        addPlacementMapping(BTWBlocks.wickerSlab, new PlacementData(PlacementData.PlacementType.BLOCK).setOffset(0x1, 0.0f, 1.0f)/*.setMaskMeta(0x7)*/);
        addPlacementMapping(BTWBlocks.companionCube, new PlacementData(PlacementData.PlacementType.BLOCK).setOffset(0x1, 0.0f, 1.0f));
        addExtendedPlacementMapping(AestheticNonOpaqueBlock.class, (ExtendedPlacementData) new ExtendedPlacementData(PlacementData.PlacementType.BLOCK, new int[]{}, AestheticNonOpaqueBlock.SUBTYPE_WHITE_COBBLE_SLAB, AestheticNonOpaqueBlock.SUBTYPE_WHITE_COBBLE_SLAB_UPSIDE_DOWN).setOffset(0x1, 0.0f, 1.0f));
//        addPlacementMapping(BTWBlocks.woolSlabTop, new PlacementData(PlacementData.PlacementType.BLOCK).setOffset(0x1, 1.0f, 1.0f)/*.setMaskMeta(0x7)*/);

        addPlacementMapping(SlabBlock.class, new PlacementData(PlacementData.PlacementType.BLOCK).setOffset(0x1, 0.0f, 1.0f));

    }

    public PlacementData addExtendedPlacementMapping(Class<? extends Block> clazz, ExtendedPlacementData data) {
        if (clazz == null || data == null) {
            return null;
        }

        return this.extendedPlacementMap.put(new ExtendedPlacementData.Data(clazz, data.validMetas), data);
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

    public PlacementData getPlacementData(Block block, ItemStack itemStack, int metadata) {
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

        for (ExtendedPlacementData.Data data : this.extendedPlacementMap.keySet()) {
            if (data.type().isInstance(block) && data.matches(metadata)) {
                return this.extendedPlacementMap.get(data);
            }
        }

        return null;
    }

    static {
        INSTANCE.populatePlacementMaps();
    }
}
