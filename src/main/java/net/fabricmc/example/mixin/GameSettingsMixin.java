package net.fabricmc.example.mixin;

import com.github.lunatrius.schematicaold.Settings;
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

    @Inject(method = "<init>(Lnet/minecraft/src/Minecraft;Ljava/io/File;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/GameSettings;loadOptions()V"))
    private void schematica$addOptions(Minecraft par1Minecraft, File par2File, CallbackInfo ci) {
        final int oldLength = this.keyBindings.length;
        this.keyBindings = Arrays.copyOf(this.keyBindings, this.keyBindings.length + Settings.instance().keyBindings.length);
        System.arraycopy(Settings.instance().keyBindings, 0, this.keyBindings, oldLength, Settings.instance().keyBindings.length);
    }
}
