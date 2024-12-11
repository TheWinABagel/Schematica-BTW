package com.github.lunatrius.schematica.network.message;

import com.github.lunatrius.api.ISchematic;
import com.github.lunatrius.schematica.handler.DownloadHandler;
import com.github.lunatrius.schematica.network.util.ByteBuf;
import com.github.lunatrius.schematica.network.util.IMessage;
import com.github.lunatrius.schematica.network.util.IMessageHandler;
import com.github.lunatrius.schematica.network.util.MessageContext;
import com.github.lunatrius.schematica.world.storage.Schematic;
import net.minecraft.src.ItemStack;

public class MessageDownloadBegin implements IMessage, IMessageHandler<MessageDownloadBegin, IMessage> {
    public ItemStack icon;
    public int width;
    public int height;
    public int length;

    public MessageDownloadBegin() {
    }

    public MessageDownloadBegin(ISchematic schematic) {
        this.icon = schematic.getIcon();
        this.width = schematic.getWidth();
        this.height = schematic.getHeight();
        this.length = schematic.getLength();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.icon = buf.readItemStack();
        this.width = buf.readShort();
        this.height = buf.readShort();
        this.length = buf.readShort();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeItemStack(this.icon);
        buf.writeShort(this.width);
        buf.writeShort(this.height);
        buf.writeShort(this.length);
    }

    @Override
    public IMessage onMessage(MessageDownloadBegin message, MessageContext ctx) {
        DownloadHandler.INSTANCE.schematic = new Schematic(message.icon, message.width, message.height, message.length);

        return new MessageDownloadBeginAck();
    }
}
