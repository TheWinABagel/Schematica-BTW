package com.github.lunatrius.schematica.world.schematic;

import com.github.lunatrius.api.ISchematic;
import net.minecraft.src.NBTTagCompound;

public class SchematicClassic extends SchematicFormat {
    @Override
    public ISchematic readFromNBT(NBTTagCompound tagCompound) {
        throw new RuntimeException("not implemented");
    }

    @Override
    public boolean writeToNBT(NBTTagCompound tagCompound, ISchematic schematic) {
        throw new RuntimeException("not implemented");
    }
}
