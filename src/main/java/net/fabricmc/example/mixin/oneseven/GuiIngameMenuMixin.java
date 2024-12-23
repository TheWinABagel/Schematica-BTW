package net.fabricmc.example.mixin.oneseven;

import com.github.lunatrius.schematica.client.gui.config.GuiButtonConfig;
import com.github.lunatrius.schematica.client.gui.config.GuiConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiIngameMenu;
import net.minecraft.src.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameMenu.class)
@Environment(EnvType.CLIENT)
public abstract class GuiIngameMenuMixin extends GuiScreen {

    @Inject(method = "initGui", at = @At("TAIL"))
    private void schematica$addConfigButton(CallbackInfo ci) {
        this.buttonList.add(new GuiButtonConfig(GuiButtonConfig.ID, this.width / 2 - 100 - 28, this.height / 4 + 96 - 16));
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    private void schematica$checkForButton(GuiButton button, CallbackInfo ci) {
        if (button.id == 17) {
            this.mc.displayGuiScreen(new GuiConfig(this));
        }
    }
}
