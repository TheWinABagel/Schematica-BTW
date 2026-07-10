package net.fabricmc.example.mixin;

import net.minecraft.src.WorldType;
import org.spongepowered.asm.mixin.gen.Invoker;

@org.spongepowered.asm.mixin.Mixin(net.minecraft.src.WorldType.class)
public interface WorldTypeAccessor {
    @Invoker("<init>")
    static WorldType createWorldType(int i, String string) {
        throw new UnsupportedOperationException();
    }
}
