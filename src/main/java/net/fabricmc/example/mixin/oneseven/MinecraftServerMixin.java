package net.fabricmc.example.mixin.oneseven;

import com.github.lunatrius.schematica.handler.DownloadHandler;
import com.github.lunatrius.schematica.handler.QueueTickHandler;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void schematica$serverEndTick(CallbackInfo ci) {
        DownloadHandler.INSTANCE.onServerStartTick();
        QueueTickHandler.INSTANCE.onServerStartTick();
    }
}
