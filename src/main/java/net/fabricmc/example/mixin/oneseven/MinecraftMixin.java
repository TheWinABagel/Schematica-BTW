package net.fabricmc.example.mixin.oneseven;

import com.github.lunatrius.schematica.handler.QueueTickHandler;
import com.github.lunatrius.schematica.handler.client.InputHandler;
import com.github.lunatrius.schematica.handler.client.RenderTickHandler;
import com.github.lunatrius.schematica.handler.client.TickHandler;
import com.github.lunatrius.schematica.handler.client.WorldHandler;
import net.minecraft.src.Minecraft;
import net.minecraft.src.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow public WorldClient theWorld;

    @Inject(method = "loadWorld(Lnet/minecraft/src/WorldClient;Ljava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/StatFileWriter;syncStats()V"))
    private void schematica$onWorldUnload(WorldClient par1WorldClient, String par2Str, CallbackInfo ci) {
        WorldHandler.onUnload(this.theWorld);
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Profiler;endSection()V"))
    private void schematica$onRunTickPost(CallbackInfo ci) {
        QueueTickHandler.INSTANCE.onClientEndTick();
        TickHandler.INSTANCE.onClientTick();
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Minecraft;updateDebugProfilerName(I)V", ordinal = 1, shift = At.Shift.AFTER))
    private void handlekeyboardinputhopefullyidkeven(CallbackInfo ci) {
        InputHandler.INSTANCE.onKeyInput();
    }

    @Inject(method = "runGameLoop", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 2))
    private void preRenderTick(CallbackInfo ci) {
        RenderTickHandler.INSTANCE.onRenderTick();
    }
}
