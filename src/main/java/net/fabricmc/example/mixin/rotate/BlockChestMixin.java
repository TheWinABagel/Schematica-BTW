package net.fabricmc.example.mixin.rotate;

import net.minecraft.src.Block;
import net.minecraft.src.BlockChest;
import net.minecraft.src.EnumFacing;
import net.minecraft.src.Material;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockChest.class)
public abstract class BlockChestMixin extends Block {
    protected BlockChestMixin(int par1, Material par2Material) {
        super(par1, par2Material);
    }

    @Override
    public int getFacing(int iMetadata) {
        return EnumFacing.values()[iMetadata].ordinal();
    }
}
