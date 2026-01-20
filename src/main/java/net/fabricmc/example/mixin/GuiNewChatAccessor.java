package net.fabricmc.example.mixin;

import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@org.spongepowered.asm.mixin.Mixin(net.minecraft.src.GuiNewChat.class)
public interface GuiNewChatAccessor {
    @Accessor
    List getChatLines();
}
