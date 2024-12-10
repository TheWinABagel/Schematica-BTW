package net.fabricmc.example.mixin;

import btw.BTWMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = BTWMod.class, remap = false)
public interface BTWModAccessor {
    @Accessor("propertyValues")
    Map<String, String> getPropertyValues();
}
