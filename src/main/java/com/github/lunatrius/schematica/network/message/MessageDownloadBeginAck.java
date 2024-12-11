package com.github.lunatrius.schematica.network.message;

import com.github.lunatrius.schematica.handler.DownloadHandler;
import com.github.lunatrius.schematica.network.transfer.SchematicTransfer;
import com.github.lunatrius.schematica.network.util.ByteBuf;
import com.github.lunatrius.schematica.network.util.IMessage;
import com.github.lunatrius.schematica.network.util.IMessageHandler;
import com.github.lunatrius.schematica.network.util.MessageContext;
import net.minecraft.src.EntityPlayerMP;

public class MessageDownloadBeginAck implements IMessage, IMessageHandler<MessageDownloadBeginAck, IMessage> {
    @Override
    public void fromBytes(ByteBuf buf) {
        // NOOP
    }

    @Override
    public void toBytes(ByteBuf buf) {
        // NOOP
    }

    @Override
    public IMessage onMessage(MessageDownloadBeginAck message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        SchematicTransfer transfer = DownloadHandler.INSTANCE.transferMap.get(player);
        if (transfer != null) {
            transfer.setState(SchematicTransfer.State.CHUNK_WAIT);
        }

        return null;
    }
}
