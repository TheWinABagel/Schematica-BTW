package net.fabricmc.example.mixin;

import com.github.lunatrius.schematica.handler.client.InputHandler;
import net.fabricmc.example.debug.DebugWorldHelper;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.Minecraft;
import net.minecraft.src.Session;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.net.Proxy;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Profiler;endSection()V"))
    private void onRunTickPost(CallbackInfo ci) {
        for (int i = InputHandler.keyBindStartIndex; i < Minecraft.getMinecraft().gameSettings.keyBindings.length; i++) {
            KeyBinding keyBinding = Minecraft.getMinecraft().gameSettings.keyBindings[i];
            int keyCode = keyBinding.keyCode;
            boolean state = (keyCode < 0 ? Mouse.isButtonDown(keyCode + 100) : Keyboard.isKeyDown(keyCode));
            if (state) {
                InputHandler.INSTANCE.onKeyInput();
            }
        }
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void test(Session par1Session, int par2, int par3, boolean par4, boolean par5, File par6File, File par7File, File par8File, Proxy par9Proxy, String par10Str, CallbackInfo ci) {
        DebugWorldHelper.addAllBlocks();
    }
}
