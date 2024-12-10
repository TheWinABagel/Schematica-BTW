package net.fabricmc.example.mixin;

import com.github.lunatrius.schematicaold.ChatEventHandler;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.Packet3Chat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetClientHandler.class)
public class NetClientHandlerMixin {
    private final ChatEventHandler handler = new ChatEventHandler();
    @Inject(method = "handleChat", at = @At("HEAD"))
    private void schematica$chatEvent(Packet3Chat par1Packet3Chat, CallbackInfo ci) {
        handler.onClientChatReceivedEvent(par1Packet3Chat.message);
    }
}
