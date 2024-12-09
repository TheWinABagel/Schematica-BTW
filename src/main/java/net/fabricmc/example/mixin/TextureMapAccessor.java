package net.fabricmc.example.mixin;

import net.minecraft.src.TextureMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(TextureMap.class)
public interface TextureMapAccessor {
    @Accessor(value = "mapUploadedSprites")
    public Map getMapUploadedSprites();
}
