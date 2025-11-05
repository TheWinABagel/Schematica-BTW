package net.fabricmc.example.mixin.debugworld;

import net.minecraft.src.BlockBaseRailLogic;
import net.minecraft.src.BlockRailBase;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockBaseRailLogic.class)
public abstract class BlockBaseRailLogicMixin {

    @Shadow @Final BlockRailBase theRail;

    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "net/minecraft/src/BlockRailBase.isPowered : Z"))
    private boolean thing(BlockRailBase instance) {
        if (instance == null) {
            if (theRail == null) {
                return false;
            }
            else return theRail.isPowered();
        }
        else return instance.isPowered();
    }
}
