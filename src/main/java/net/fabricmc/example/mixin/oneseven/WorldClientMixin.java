package net.fabricmc.example.mixin.oneseven;

import com.github.lunatrius.schematica.handler.client.WorldHandler;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldClient.class)
public abstract class WorldClientMixin {

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void schematica$loadEvent(NetClientHandler par1NetClientHandler, WorldSettings par2WorldSettings, int par3, int par4, Profiler par5Profiler, ILogAgent par6ILogAgent, CallbackInfo ci) {
        WorldHandler.onLoad(((WorldClient) (Object) this));
    }
}
