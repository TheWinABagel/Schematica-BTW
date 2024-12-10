package com.github.lunatrius.schematica.util;

import com.github.lunatrius.schematica.SchematicWorld;
import net.minecraft.src.Block;
import net.minecraft.src.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockConfig {
    public static final List<Integer> blockListIgnoreID = new ArrayList<>();
    public static final List<Integer> blockListIgnoreMetadata = new ArrayList<>();
    public static final Map<Integer, ItemInfo> blockListMapping = new HashMap<>();

    public static void addBtwBlocks() {
        //siding and corner flip, 1 - 3, 9 - 11

    }

    public static void addIgnoreMeta(Block block) {
        addIgnoreMeta(block.blockID);
    }

    public static void addIgnoreMeta(int blockId) {
        SchematicWorld.addIgnoreMeta(blockId);
    }

    /**
     * Makes the material list show that item id instead of the block id
     * */
    public static void addBlockToItem(int blockId, int itemId) {
        SchematicWorld.addBlockItemMap(blockId, itemId);
    }

    public static void addBlockToItem(Block block, Item item) {
        addBlockToItem(block.blockID, item.itemID);
    }

    public record ItemInfo(int itemId, int meta) {

        public static ItemInfo of(int itemId) {
            return new ItemInfo(itemId, 0);
        }

        public static ItemInfo ignoreMeta(int itemId) {
            return new ItemInfo(itemId, -1);
        }

        public boolean hasMeta() {
            return meta != -1;
        }
    }
}
