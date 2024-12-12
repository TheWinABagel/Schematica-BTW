package com.github.lunatrius.schematica.network.message;

import com.github.lunatrius.schematica.Schematica;
import com.github.lunatrius.schematica.handler.DownloadHandler;
import com.github.lunatrius.schematica.network.util.ByteBuf;
import com.github.lunatrius.schematica.network.util.IMessage;
import com.github.lunatrius.schematica.network.util.IMessageHandler;
import com.github.lunatrius.schematica.network.util.MessageContext;
import com.github.lunatrius.schematica.reference.Names;
import com.github.lunatrius.schematica.world.schematic.SchematicFormat;
import net.minecraft.src.Minecraft;
import net.minecraft.src.StringTranslate;

import java.io.File;

public class MessageDownloadEnd implements IMessage, IMessageHandler<MessageDownloadEnd, IMessage> {
    public static final int ID = 5;
    public String name;

    public MessageDownloadEnd() {
    }

    public MessageDownloadEnd(String name) {
        this.name = name;
    }

    @Override
    public IMessage fromBytes(ByteBuf buf) {
        this.name = buf.readString();
        return this;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeString(this.name);
    }

    @Override
    public int id() {
        return ID;
    }

    @Override
    public IMessage onMessage(MessageDownloadEnd message, MessageContext ctx) {
        File directory = Schematica.getProxy().getPlayerSchematicDirectory(null, true);
        boolean success = SchematicFormat.writeToFile(directory, message.name, DownloadHandler.INSTANCE.schematic);

        if (success) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(StringTranslate.getInstance().translateKeyFormat(Names.Command.Download.Message.DOWNLOAD_SUCCEEDED, message.name));
        }

        DownloadHandler.INSTANCE.schematic = null;

        return null;
    }
}
