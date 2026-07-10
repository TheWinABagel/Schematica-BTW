package net.fabricmc.example.mixin;

import net.minecraft.src.NBTTagCompound;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.io.DataOutput;
import java.io.IOException;

@org.spongepowered.asm.mixin.Mixin(net.minecraft.src.Packet.class)
public interface PacketAccessor {
    @Invoker
    static void callWriteNBTTagCompound(NBTTagCompound par0NBTTagCompound, DataOutput par1DataOutput) throws IOException {
        throw new UnsupportedOperationException();
    }
}
