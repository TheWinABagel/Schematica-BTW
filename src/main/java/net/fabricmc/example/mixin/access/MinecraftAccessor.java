package net.fabricmc.example.mixin.access;

import net.minecraft.src.Minecraft;
import net.minecraft.src.ServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface MinecraftAccessor {

    @Accessor("currentServerData")
    ServerData getCurrentServerData();
}
