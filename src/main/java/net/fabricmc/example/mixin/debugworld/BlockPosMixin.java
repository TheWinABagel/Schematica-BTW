package net.fabricmc.example.mixin.debugworld;

import btw.world.util.BlockPos;
import net.minecraft.src.Facing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockPos.class)
public class BlockPosMixin {

    @Shadow(remap = false) public int x;

    @Shadow(remap = false) public int y;

    @Shadow(remap = false) public int z;

    /**
     * @author Bagel
     * @reason pain
     */
    @Overwrite(remap = false)
    public void addFacingAsOffset(int facing) {
        try {
            this.x += Facing.offsetsXForSide[facing];
            this.y += Facing.offsetsYForSide[facing];
            this.z += Facing.offsetsZForSide[facing];
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("Failed to set offset " + facing + "! Printing...");
            e.printStackTrace();
        }
    }
}
