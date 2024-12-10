package com.github.lunatrius.schematica;

import com.github.lunatrius.schematica.forge.ForgeDirection;
import net.minecraft.src.Block;

import java.util.HashMap;
import java.util.Map;

public class BlockInfo {
	public static final Map<String, Integer> ALIAS = new HashMap<String, Integer>();
	private static final Map<Integer, Integer> FLIP = new HashMap<Integer, Integer>();
	private static final Map<Integer, Integer> ROTATION = new HashMap<Integer, Integer>();

	public static boolean addMappingAlias(String key, String value) {
		String k = key;
		int v = parseNumber(value);

		if (v > 0) {
			ALIAS.put(k, v);
			return true;
		}

		return false;
	}

	public static boolean addMappingRotation(String key, String value) {
		return addMapping(key, value, ROTATION);
	}

	public static boolean addMappingFlip(String key, String value) {
		return addMapping(key, value, FLIP);
	}

	private static boolean addMapping(String key, String value, Map<Integer, Integer> map) {
		int k = parseInfo(key);
		int v = parseInfo(value);

		if (k > 0 && v > 0) {
			map.put(k, v);
			return true;
		}

		return false;
	}

	private static int parseInfo(String str) {
		String[] parts = str.split("-");

		int blockId = getBlockId(parts[0]) << 8;
		if (blockId > 0) {
			if (parts.length == 1) {
				return blockId;
			}

			if (parts.length == 2) {
				return blockId | (parseNumber(parts[1]) & 0xF);
			}
		}

		return 0;
	}

	private static int getBlockId(String str) {
		if (ALIAS.containsKey(str)) {
			return ALIAS.get(str);
		}

		return parseNumber(str);
	}

	private static int parseNumber(String str) {
		try {
			return Integer.valueOf(str, 10);
		} catch (NumberFormatException e) {
			Settings.logger.logSevereException("Could not parse the given number!", e);
		}
		return 0;
	}

	public static int getTransformedMetadataRotation(int blockId, int metadata) {
		return getTransformedMetadata(blockId, metadata, ROTATION, false);
	}

	public static int getTransformedMetadataFlip(int blockId, int metadata) {
		return getTransformedMetadata(blockId, metadata, FLIP, true);
	}

	private static int getTransformedMetadata(int blockId, int metadata, Map<Integer, Integer> map, boolean flip) {
		int key = (blockId << 8) | metadata;
		if (map.containsKey(key)) {
			return map.get(key) & 0xF;
		}
		Block block = Block.blocksList[blockId];
		if (block != null) {
			if (!flip) {
				return block.rotateMetadataAroundYAxis(metadata, false);
			}
//			else {
//				ForgeDirection dir = ForgeDirection.getOrientation(block.getFacing(metadata));
//				if (dir == ForgeDirection.UNKNOWN ||dir ==  ForgeDirection.NORTH || dir == ForgeDirection.SOUTH) {
//					return metadata;
//				}
//				try {
//					return block.setFacing(metadata, Block.getOppositeFacing(block.getFacing(metadata)));
//				}
//				catch (ArrayIndexOutOfBoundsException e) {
//					System.err.println("block " + block.getLocalizedName() + " failed to flip with metadata " + metadata);
//					System.err.println("--previous error: e.getMessage();");
//					return metadata;
//				}
//			}
		}
		return metadata;
	}
}
