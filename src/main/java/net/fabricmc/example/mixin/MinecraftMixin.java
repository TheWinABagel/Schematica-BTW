package net.fabricmc.example.mixin;

import com.github.lunatrius.schematica.handler.client.InputHandler;
import com.github.lunatrius.schematicaold.Schematica;
import com.github.lunatrius.schematicaold.Settings;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Profiler;endSection()V"))
    private void onRunTickPost(CallbackInfo ci) {
//        Schematica.instance.onTick(false);
        //todo will break with other addons that add keybinds, save start index of keybinds somewhere
        for (int i = Minecraft.getMinecraft().gameSettings.keyBindings.length - InputHandler.KEY_BINDINGS.length; i < Minecraft.getMinecraft().gameSettings.keyBindings.length; i++) {
            KeyBinding keyBinding = Minecraft.getMinecraft().gameSettings.keyBindings[i];
            int keyCode = keyBinding.keyCode;
            boolean state = (keyCode < 0 ? Mouse.isButtonDown(keyCode + 100) : Keyboard.isKeyDown(keyCode));
            if (state) {
                InputHandler.INSTANCE.onKeyInput(i - Minecraft.getMinecraft().gameSettings.keyBindings.length + InputHandler.KEY_BINDINGS.length);
            }
        }
    }
}
