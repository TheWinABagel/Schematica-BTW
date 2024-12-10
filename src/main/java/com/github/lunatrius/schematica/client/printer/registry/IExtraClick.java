package com.github.lunatrius.schematica.client.printer.registry;

import net.minecraft.src.Block;

public interface IExtraClick {
    int getExtraClicks(Block block, int metadata);
}
