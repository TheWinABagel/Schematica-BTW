package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.gen.Invoker;

import java.io.DataOutput;

@org.spongepowered.asm.mixin.Mixin(net.minecraft.src.NBTBase.class)
public interface NBTBaseAccessor {
    @Invoker
    void callWrite(DataOutput var1);
}
