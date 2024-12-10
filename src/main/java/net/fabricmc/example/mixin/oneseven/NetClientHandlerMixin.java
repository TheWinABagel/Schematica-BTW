package net.fabricmc.example.mixin.oneseven;

import com.github.lunatrius.schematica.handler.client.ChatEventHandler;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.Packet3Chat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetClientHandler.class)
public class NetClientHandlerMixin {
    @Inject(method = "handleChat", at = @At("HEAD"))
    private void schematica$chatEvent(Packet3Chat par1Packet3Chat, CallbackInfo ci) {
        ChatEventHandler.INSTANCE.onClientChatReceivedEvent(par1Packet3Chat.message);
    }
}
