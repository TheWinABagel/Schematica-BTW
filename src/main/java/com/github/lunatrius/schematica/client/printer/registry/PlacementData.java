package com.github.lunatrius.schematica.client.printer.registry;

import net.fabricmc.example.ForgeDirection;
import net.minecraft.src.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlacementData {
    public static enum PlacementType {
        BLOCK, PLAYER, PISTON
    }

    public static final ForgeDirection[] VALID_DIRECTIONS = ForgeDirection.VALID_DIRECTIONS;

    public final PlacementType type;
    public int maskOffset = 0x0;
    public float offsetLowY = 0.0f;
    public float offsetHighY = 1.0f;
    public int maskMeta = 0xF;
    public final Map<ForgeDirection, Integer> mapping = new HashMap<ForgeDirection, Integer>();
    private IExtraClick extraClick;

    public PlacementData(PlacementType type, int... metadata) {
        this.type = type;

        for (int i = 0; i < VALID_DIRECTIONS.length && i < metadata.length; i++) {
            if (metadata[i] >= 0x0 && metadata[i] <= 0xF) {
                this.mapping.put(VALID_DIRECTIONS[i], metadata[i]);
            }
        }
    }

    public PlacementData setOffset(int maskOffset, float offsetLowY, float offsetHighY) {
        this.maskOffset = maskOffset;
        this.offsetLowY = offsetLowY;
        this.offsetHighY = offsetHighY;
        return this;
    }

    public PlacementData setMaskMeta(int maskMeta) {
        this.maskMeta = maskMeta;
        return this;
    }

    public float getOffsetFromMetadata(int metadata) {
        return (metadata & this.maskOffset) == 0 ? this.offsetLowY : this.offsetHighY;
    }

    public ForgeDirection[] getValidDirections(ForgeDirection[] solidSides, int metadata) {
        List<ForgeDirection> list = new ArrayList<ForgeDirection>();

        for (ForgeDirection direction : solidSides) {
            if (this.maskOffset != 0) {
                if ((metadata & this.maskOffset) == 0) {
                    if (this.offsetLowY < 0.5f && direction == ForgeDirection.UP) {
                        continue;
                    }
                } else {
                    if (this.offsetLowY < 0.5f && direction == ForgeDirection.DOWN) {
                        continue;
                    }
                }
            }

            if (this.type == PlacementType.BLOCK) {
                Integer meta = this.mapping.get(direction);
                if ((meta != null ? meta : -1) != (this.maskMeta & metadata) && this.mapping.size() != 0) {
                    continue;
                }
            }

            list.add(direction);
        }

        ForgeDirection[] directions = new ForgeDirection[list.size()];
        return list.toArray(directions);
    }

    public PlacementData setExtraClick(final IExtraClick extraClick) {
        this.extraClick = extraClick;
        return this;
    }

    public int getExtraClicks(final Block block, final int metadata) {
        if (this.extraClick != null) {
            return this.extraClick.getExtraClicks(block, metadata);
        }

        return 0;
    }
}
