package net.fabricmc.example.mixin.debugworld;

import net.fabricmc.example.debug.ChunkProviderDebug;
import net.fabricmc.example.debug.DebugWorldHelper;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldProvider.class)
public abstract class WorldProviderMixin {

    @Shadow public World worldObj;

    @Shadow public WorldChunkManager worldChunkMgr;

    @Shadow public WorldType terrainType;

    @Inject(method = "registerWorldChunkManager", at = @At("HEAD"), cancellable = true)
    private void test(CallbackInfo ci) {
        if (this.worldObj.getWorldInfo().getTerrainType() == DebugWorldHelper.DEBUG_WORLD_TYPE) {
            this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.biomeList[2], 0.5f, 0.5f);
            ci.cancel();
        }
    }

    @Inject(method = "createChunkGenerator", at = @At("HEAD"), cancellable = true)
    private void chunkGenerator(CallbackInfoReturnable<IChunkProvider> cir) {
        if(this.terrainType == DebugWorldHelper.DEBUG_WORLD_TYPE) {
            cir.setReturnValue(new ChunkProviderDebug(this.worldObj));
        }
    }

    @Inject(method = "getAverageGroundLevel", at = @At("HEAD"), cancellable = true)
    private void avgGround(CallbackInfoReturnable<Integer> cir) {
        if(this.terrainType == DebugWorldHelper.DEBUG_WORLD_TYPE) {
            cir.setReturnValue(1);
        }
    }
}
