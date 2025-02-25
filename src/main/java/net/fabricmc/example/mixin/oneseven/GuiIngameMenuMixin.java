package net.fabricmc.example.mixin.oneseven;

//import com.github.lunatrius.schematica.client.gui.config.GuiButtonOpenConfig;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.GuiIngameMenu;
import net.minecraft.src.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GuiIngameMenu.class)
@Environment(EnvType.CLIENT)
public abstract class GuiIngameMenuMixin extends GuiScreen {

//    @Inject(method = "initGui", at = @At("TAIL"))
//    private void schematica$addConfigButton(CallbackInfo ci) {
//        this.buttonList.add(new GuiButtonOpenConfig(this.width / 2 - 100 - 28, this.height / 4 + 96 - 16));
//    }
}
