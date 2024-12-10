package net.fabricmc.example.mixin;

import com.github.lunatrius.schematica.handler.PlayerHandler;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.INetworkManager;
import net.minecraft.src.NetServerHandler;
import net.minecraft.src.ServerConfigurationManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerConfigurationManager.class)
public abstract class NetServerHandlerMixin {

    @Inject(method = "playerLoggedOut", at = @At(value = "HEAD"))
    private void schematica$loggedOut(EntityPlayerMP player, CallbackInfo ci) {
        PlayerHandler.INSTANCE.onPlayerLoggedOut(player);
    }

    @Inject(method = "initializeConnectionToPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayerMP;addSelfToInternalCraftingInventory()V", shift = At.Shift.AFTER))
    private void schematica$loggedIn(INetworkManager manager, EntityPlayerMP player, CallbackInfo ci) {
        PlayerHandler.INSTANCE.onPlayerLoggedIn(player);
    }

}
