package net.fabricmc.example.mixin;

import net.minecraft.src.RenderGlobal;
import net.minecraft.src.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderGlobal.class)
public interface RenderGlobalAccessor {

    @Accessor(value = "sortedWorldRenderers")
    public WorldRenderer[] getWorldRenderer();
}
