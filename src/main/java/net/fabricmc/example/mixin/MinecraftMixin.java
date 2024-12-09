package net.fabricmc.example.mixin;

import com.github.lunatrius.schematica.Schematica;
import com.github.lunatrius.schematica.Settings;
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
        Schematica.instance.onTick(false);
        //todo will break with other addons that add keybinds, save start index of keybinds somewhere
        for (int i = Minecraft.getMinecraft().gameSettings.keyBindings.length - Settings.instance().keyBindings.length; i < Minecraft.getMinecraft().gameSettings.keyBindings.length; i++) {
            KeyBinding keyBinding = Minecraft.getMinecraft().gameSettings.keyBindings[i];
            int keyCode = keyBinding.keyCode;
            boolean state = (keyCode < 0 ? Mouse.isButtonDown(keyCode + 100) : Keyboard.isKeyDown(keyCode));
            if (/*state != keyDown[i] || (state && repeatings[i]) || */true)
            {
                if (state)
                {
                    Schematica.instance.keyboardEvent(keyBinding, true);
//                    keyDown(type, keyBinding, tickEnd, state!=keyDown[i]);
                }
                else
                {
                    Schematica.instance.keyboardEvent(keyBinding, false);
//                    keyUp(type, keyBinding, tickEnd);
                }
//                if (tickEnd)
//                {
//                    keyDown[i] = state;
//                }
            }
        }
    }
}
