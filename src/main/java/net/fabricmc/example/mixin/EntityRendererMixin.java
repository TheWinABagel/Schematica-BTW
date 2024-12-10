package net.fabricmc.example.mixin;

import com.github.lunatrius.schematicaold.client.renderer.RendererSchematicGlobal;
import net.minecraft.src.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {
    private RendererSchematicGlobal schemRender = new RendererSchematicGlobal();

    @Inject(method = "renderWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityRenderer;disableLightmap(D)V"))
    private void test(float partial, long par2, CallbackInfo ci) {
        schemRender.onRender(partial);
    }
}
