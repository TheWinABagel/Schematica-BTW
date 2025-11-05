package net.fabricmc.example.debug;

import btw.block.BTWBlocks;
import com.github.lunatrius.schematica.reference.Reference;
import com.github.lunatrius.schematica.world.WorldDummy;
import net.minecraft.src.Block;
import net.minecraft.src.Chunk;
import net.minecraft.src.WorldType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DebugWorldHelper {
    public static final Map<ChunkData, List<BlockType>> ALL_BLOCKS = new HashMap<>();
    public static int maxY = 0;
    public static final int maxX = 8;

    public static final Map<Integer, int[]> MAX_METAS = new HashMap<>();
    public static void addAllBlocks() {
        int chunkX = 0, chunkY = 0;
        List<BlockType> blockTypes = new ArrayList<>();
        for (Block block : Block.blocksList) {
            if (block == null || block.blockID == 0) continue;
            if (block.hasTileEntity()) {
                //idk yet
            }
//            System.out.printf("Attempting to create data for block %s with id %d%n", block, block.blockID);
            int[] maxes = fillUntil(16);
            if (MAX_METAS.containsKey(block.blockID)) {
                maxes = MAX_METAS.get(block.blockID);
            }
            if (block.blockID == Block.woodenButton.blockID) {
                int adfsdf = 3;
            }
            for (int meta : maxes) {
                blockTypes.add(new BlockType(block.blockID, meta));
                if (blockTypes.size() >= 64) {
                    ALL_BLOCKS.put(new ChunkData(chunkX++, chunkY), new ArrayList<>(blockTypes));
                    if (chunkX >= maxX) {
                        chunkX = 0;
                        chunkY++;
                    }
                    blockTypes.clear();
                }
            }
        }

        ALL_BLOCKS.put(new ChunkData(chunkX, chunkY), new ArrayList<>(blockTypes));
        thing();
    }
    public static void thing() {
        for (int chunkY = 0; chunkY < ALL_BLOCKS.size(); chunkY++) {
            for (int chunkX = 0; chunkX < maxX; chunkX++) {
                Chunk chunk = new Chunk(WorldDummy.instance(), chunkX, chunkY);
                var data = new ChunkData(chunkX, chunkY);
                var list = ALL_BLOCKS.get(data);
                if (list == null) {
                    maxY = chunkY;
                    return;
                }
                thing:
                for (int x = 0; x < 16; x+=2) {
                    for (int y = 0; y < 16; y+=2) {
                        int id;
                        try {
                            /*int */id = y/2 + x/2 * 8;
                            if (id >= 63) continue;
                            if (id >= list.size()) continue;
                            DebugWorldHelper.BlockType test = list.get(id);
                            chunk.setBlockIDWithMetadata(x, 1, y, test.id(), test.meta());
                        }
                        catch (IndexOutOfBoundsException | NullPointerException e) {
                            Reference.logger.error("caught {} with x,y [{},{}] ", e.toString(), chunkX, chunkY);
                            CHUNKS.put(data, chunk);
                            continue thing;
//                            break;
                        }

                    }
                }
                CHUNKS.put(data, chunk);
            }

        }
//        int i = 0;
    }
    public static Map<ChunkData, Chunk> CHUNKS = new HashMap<>();
    static {
        //These cause crashes when loaded
        Block[] cobblestones = {
                Block.cobblestone, Block.cobblestoneMossy, Block.cobblestoneWall, BTWBlocks.cobblestoneSlab, BTWBlocks.looseCobblestoneSlab, BTWBlocks.cobblestoneDoubleSlab,
                BTWBlocks.looseCobblestone, BTWBlocks.looseCobblestoneStairs, BTWBlocks.looseStoneBrick, BTWBlocks.looseStoneBrickSlab
        };
        max(cobblestones, 3);

        Block[] wood = { Block.wood, Block.planks, Block.woodSingleSlab, Block.woodDoubleSlab };
        max(wood, 5);

        max(Block.pistonExtension.blockID, 5); //piston extension
        max(Block.pistonBase, 6);
        max(Block.pistonStickyBase, 6);
        max(new Block[]{Block.stoneBrick, }, 12);
        max(new Block[]{BTWBlocks.stoneBrickSlab, BTWBlocks.stoneBrickDoubleSlab, BTWBlocks.stoneSlab, BTWBlocks.stoneDoubleSlab}, 3);

        max(BTWBlocks.axle, 3);
        max(BTWBlocks.buddyBlock, 6);
        max(BTWBlocks.stake, new int[]{0, 1, 2, 3, 4, 5, 8, 9, 10, 11, 12, 13});
        max(new Block[]{BTWBlocks.idleOven, BTWBlocks.burningOven}, 5);
        max(new Block[]{Block.torchRedstoneActive, Block.torchRedstoneIdle}, 6);
        max(BTWBlocks.miningCharge, 6);
        max(BTWBlocks.blockDispenser, 6);
        max(BTWBlocks.detectorBlock, 6);
        max(new Block[]{BTWBlocks.gearBox, BTWBlocks.redstoneClutch}, 6);

        disableBoringBlocks();
        disableInvisBlocks();
    }

    public static void disableInvisBlocks() {
        /*
        block
        pistonMoving

        fire doesnt render but is technically still there...? prob due to cancelling ticking
        */
        max(Block.ladder, 4);
        max(Block.rail, 2);
    }

    public static void disableBoringBlocks() {
        max(Block.stone, 6);
        max(Block.grass, 2);

        Block[] boring = {
                Block.dirt, Block.bedrock, Block.sand, Block.gravel, Block.sponge, Block.glass, Block.music, Block.coalBlock, Block.blockIron, Block.blockGold,
                Block.blockDiamond, Block.mushroomBrown, Block.mushroomRed, Block.plantRed, Block.plantYellow, Block.brick, Block.tnt, Block.bookShelf, Block.obsidian,
                Block.mobSpawner, Block.chest, Block.blockLapis
        };
        max(boring, 1);

        Block[] ore = {Block.oreCoal, Block.oreDiamond, Block.oreEmerald, Block.oreGold, Block.oreIron, Block.oreLapis};
        max(ore, 3);

        max(Block.sandStone, 3);
                /*Boring
        block, last interesting id
        sandstone, 2

        */
        //These just add nothing new/don't render right and not even in a funny way

    }

    /**
     * @param validMetas Valid meta values for blocks
     * */
    public static void max(int id, int[] validMetas) {
        for (int meta : validMetas) {
            if (meta > 16 || meta < 0) {
                throw new IllegalArgumentException("Metadata [" + meta + "] cannot be less than 0 or greater than 16!");
            }
        }
        MAX_METAS.put(id, validMetas);
    }

    /**
     * @param validMetas Valid meta values for blocks
     * */
    public static void max(Block block, int[] validMetas) {
        max(block.blockID, validMetas);
    }

    /**
     * @param maxMeta Maximum metadata for this block id. Starts from 0 (inclusive) and ends at maxMeta (exclusive)
     * */
    public static void max(int id, int maxMeta) {
        max(id, fillUntil(maxMeta));
    }

    /**
     * @param ids Valid block ids for this. Can group up similar blocks with different ids, ie wood, cobblestone
     * @param maxMeta Maximum metadata for these block ids. Starts from 0 (inclusive) and ends at maxMeta (exclusive)
     * */
    public static void max(int[] ids, int maxMeta) {
        for (int id : ids) {
            max(id, fillUntil(maxMeta));
        }
    }

    /**
     * @param ids Valid block ids for this. Can group up similar blocks with different ids, ie wood, cobblestone
     * @param maxMeta Maximum metadata for these block ids. Starts from 0 (inclusive) and ends at maxMeta (exclusive)
     * */
    public static void max(Block[] ids, int maxMeta) {
        for (Block id : ids) {
            max(id, fillUntil(maxMeta));
        }
    }

    /**
     * @param maxMeta Maximum metadata for this block id. Starts from 0 (inclusive) and ends at maxMeta (exclusive)
     * */
    public static void max(Block block, int maxMeta) {
        max(block.blockID, maxMeta);
    }

    public static WorldType DEBUG_WORLD_TYPE = new WorldType(6, "debug");

    public record ChunkData(int chunkX, int chunkY) {

    }

    public record BlockType(int id, int meta) {

    }

    private static int[] fillUntil(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i;
        }
        return arr;
    }
}
