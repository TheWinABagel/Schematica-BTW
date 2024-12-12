package com.github.lunatrius.schematica.network.message;

import com.github.lunatrius.schematica.handler.DownloadHandler;
import com.github.lunatrius.schematica.network.transfer.SchematicTransfer;
import com.github.lunatrius.schematica.network.util.ByteBuf;
import com.github.lunatrius.schematica.network.util.IMessage;
import com.github.lunatrius.schematica.network.util.IMessageHandler;
import com.github.lunatrius.schematica.network.util.MessageContext;
import net.minecraft.src.EntityPlayerMP;

public class MessageDownloadChunkAck implements IMessage, IMessageHandler<MessageDownloadChunkAck, IMessage> {
    public static final int ID = 4;
    private int baseX;
    private int baseY;
    private int baseZ;

    public MessageDownloadChunkAck() {
    }

    public MessageDownloadChunkAck(final int baseX, final int baseY, final int baseZ) {
        this.baseX = baseX;
        this.baseY = baseY;
        this.baseZ = baseZ;
    }

    @Override
    public IMessage fromBytes(ByteBuf buf) {
        this.baseX = buf.readShort();
        this.baseY = buf.readShort();
        this.baseZ = buf.readShort();
        return this;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeShort(this.baseX);
        buf.writeShort(this.baseY);
        buf.writeShort(this.baseZ);
    }

    @Override
    public int id() {
        return ID;
    }

    @Override
    public IMessage onMessage(MessageDownloadChunkAck message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        SchematicTransfer transfer = DownloadHandler.INSTANCE.transferMap.get(player);
        if (transfer != null) {
            transfer.confirmChunk(message.baseX, message.baseY, message.baseZ);
        }

        return null;
    }
}
