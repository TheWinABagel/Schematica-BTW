package net.fabricmc.example;

import net.minecraft.src.NBTBase;

import java.io.DataOutput;
import java.io.IOException;

public interface CompoundTagExtension {

    public static void func_150298_a(String name, NBTBase data, DataOutput output) throws IOException
    {
        output.writeByte(data.getId());

        if (data.getId() != 0)
        {
            output.writeUTF(name);
            data.write(output);
        }
    }
}
