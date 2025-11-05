package net.fabricmc.example.mixin.debugworld;

import net.fabricmc.example.debug.DebugWorldHelper;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Mixin(WorldServer.class)
public abstract class WorldServerMixin extends World {
    @Shadow private List pendingTickListEntriesThisTick;

    @Shadow private TreeSet pendingTickListEntriesTreeSet;

    @Shadow private Set pendingTickListEntriesHashSet;

    private boolean debugWorld$cancel = this.worldInfo.getTerrainType().getWorldTypeID() == DebugWorldHelper.DEBUG_WORLD_TYPE.getWorldTypeID();

    public WorldServerMixin(ISaveHandler a, String b, WorldProvider c, WorldSettings d, Profiler e, ILogAgent f) {
        super(a, b, c, d, e, f);
    }

    @Inject(method = "scheduleBlockUpdateWithPriority", at = @At("HEAD"), cancellable = true)
    private void yeetUpdates(int par1, int par2, int par3, int par4, int par5, int par6, CallbackInfo ci) {
//        System.out.println("terrain type schedule = " + this.worldInfo.getTerrainType());
        if (this.worldInfo.getTerrainType().getWorldTypeID() == DebugWorldHelper.DEBUG_WORLD_TYPE.getWorldTypeID()) {
            ci.cancel();
        }
    }
    @Inject(method = "tickUpdates", at = @At("HEAD"), cancellable = true)
    private void yeetUpdates2(boolean par1, CallbackInfoReturnable<Boolean> cir) {
//        System.out.println("terrain type tickupdates = " + this.worldInfo.getTerrainType().getWorldTypeName());
        if (this.worldInfo.getTerrainType().getWorldTypeID() == DebugWorldHelper.DEBUG_WORLD_TYPE.getWorldTypeID()) {
            this.pendingTickListEntriesTreeSet.clear();
            this.pendingTickListEntriesHashSet.clear();
            this.pendingTickListEntriesThisTick.clear();
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    @Inject(method = "tickBlocksAndAmbiance", at = @At("HEAD"), cancellable = true)
    private void yeetUpdates3(CallbackInfo ci) {
        if (this.worldInfo.getTerrainType().getWorldTypeID() == DebugWorldHelper.DEBUG_WORLD_TYPE.getWorldTypeID()) {
            ci.cancel();
        }
    }
}
