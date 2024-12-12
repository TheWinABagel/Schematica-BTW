package net.fabricmc.example.mixin.oneseven;

import com.github.lunatrius.schematica.handler.client.InputHandler;
import net.minecraft.src.GameSettings;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.Arrays;

@Mixin(GameSettings.class)
public class GameSettingsMixin {
    @Shadow public KeyBinding[] keyBindings;
    //todo custom config screen for keybinds
    @Inject(method = "<init>(Lnet/minecraft/src/Minecraft;Ljava/io/File;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/GameSettings;loadOptions()V"))
    private void schematica$addOptions(Minecraft par1Minecraft, File par2File, CallbackInfo ci) {
        InputHandler.keyBindStartIndex = keyBindings.length;
        for (KeyBinding keyBinding : InputHandler.KEY_BINDINGS) {
            keyBindings = Arrays.copyOf(keyBindings, keyBindings.length + 1);
            keyBindings[keyBindings.length - 1] = keyBinding;
        }
    }
}
