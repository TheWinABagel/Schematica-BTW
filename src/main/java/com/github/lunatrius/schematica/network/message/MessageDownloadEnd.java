package com.github.lunatrius.schematica.network.message;

import com.github.lunatrius.schematica.Schematica;
import com.github.lunatrius.schematica.handler.DownloadHandler;
import com.github.lunatrius.schematica.network.util.IMessage;
import com.github.lunatrius.schematica.network.util.IMessageHandler;
import com.github.lunatrius.schematica.network.util.MessageContext;
import com.github.lunatrius.schematica.reference.Names;
import com.github.lunatrius.schematica.world.schematic.SchematicFormat;
import net.minecraft.src.Minecraft;

import java.io.File;

public class MessageDownloadEnd implements IMessage, IMessageHandler<MessageDownloadEnd, IMessage> {
    public String name;

    public MessageDownloadEnd() {
    }

    public MessageDownloadEnd(String name) {
        this.name = name;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.name = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.name);
    }

    @Override
    public IMessage onMessage(MessageDownloadEnd message, MessageContext ctx) {
        File directory = Schematica.getProxy().getPlayerSchematicDirectory(null, true);
        boolean success = SchematicFormat.writeToFile(directory, message.name, DownloadHandler.INSTANCE.schematic);

        if (success) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentTranslation(Names.Command.Download.Message.DOWNLOAD_SUCCEEDED, message.name));
        }

        DownloadHandler.INSTANCE.schematic = null;

        return null;
    }
}
