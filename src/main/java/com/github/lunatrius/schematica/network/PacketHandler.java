package com.github.lunatrius.schematica.network;

import btw.network.packet.handler.CustomPacketHandler;
import com.github.lunatrius.schematica.Schematica;
import com.github.lunatrius.schematica.network.message.MessageCapabilities;
import com.github.lunatrius.schematica.network.message.MessageDownloadBegin;
import com.github.lunatrius.schematica.network.message.MessageDownloadBeginAck;
import com.github.lunatrius.schematica.network.message.MessageDownloadChunk;
import com.github.lunatrius.schematica.network.message.MessageDownloadChunkAck;
import com.github.lunatrius.schematica.network.message.MessageDownloadEnd;
import com.github.lunatrius.schematica.network.util.ByteBuf;
import com.github.lunatrius.schematica.network.util.IMessage;
import com.github.lunatrius.schematica.network.util.IMessageHandler;
import com.github.lunatrius.schematica.network.util.MessageContext;
import com.github.lunatrius.schematica.reference.Reference;
import net.minecraft.src.*;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class PacketHandler implements CustomPacketHandler {

    private PacketHandler() {
    }

    @Override
    public void handleCustomPacket(Packet250CustomPayload packet, EntityPlayer player) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(packet.data);
        DataInputStream dis = new DataInputStream(bis);
        int id = dis.readInt();
        ByteBuf buf = ByteBuf.in(dis);

        IMessage message = switch (id) {
            case MessageCapabilities.ID -> new MessageCapabilities().fromBytes(buf);
            case MessageDownloadBegin.ID -> new MessageDownloadBegin().fromBytes(buf);
            case MessageDownloadBeginAck.ID -> new MessageDownloadBeginAck().fromBytes(buf);
            case MessageDownloadChunk.ID -> new MessageDownloadChunk().fromBytes(buf);
            case MessageDownloadChunkAck.ID -> new MessageDownloadChunkAck().fromBytes(buf);
            case MessageDownloadEnd.ID -> new MessageDownloadEnd().fromBytes(buf);
            default -> throw new RuntimeException("Invalid Schematica packet id " + id + "!");
        };
        //is this safe?
        if (message instanceof IMessageHandler handler) {
            NetHandler netHandler;
            if (player instanceof EntityPlayerMP emp) {
                netHandler = emp.playerNetServerHandler;
            }
            else {
               netHandler = ((EntityClientPlayerMP) player).sendQueue;
            }
            IMessage reply = handler.onMessage(message, new MessageContext(netHandler));
            if (reply != null) {
                if (player instanceof EntityPlayerMP mp) {
                    System.out.println("sending packet to mp player with id " + reply.id());
                    mp.playerNetServerHandler.sendPacketToPlayer(reply.toPacket());
                }
                else if (player instanceof EntityClientPlayerMP cmp) {
                    System.out.println("sending packet to sp player with id " + reply.id());
                    cmp.sendQueue.addToSendQueue(reply.toPacket());
                }
            }
        }
    }
    //todo networking in its entirety
    public static final PacketHandler INSTANCE = new PacketHandler();

    public static void init() {
        System.out.println("CURRENT PACKET HANDLER ID = " + Schematica.instance.getModID());
        Schematica.instance.registerPacketHandler(Reference.PACKET_ID, INSTANCE);
//        INSTANCE.registerMessage(MessageCapabilities.class, MessageCapabilities.class, 0, Side.CLIENT);
//
//        INSTANCE.registerMessage(MessageDownloadBegin.class, MessageDownloadBegin.class, 1, Side.CLIENT);
//        INSTANCE.registerMessage(MessageDownloadBeginAck.class, MessageDownloadBeginAck.class, 2, Side.SERVER);
//        INSTANCE.registerMessage(MessageDownloadChunk.class, MessageDownloadChunk.class, 3, Side.CLIENT);
//        INSTANCE.registerMessage(MessageDownloadChunkAck.class, MessageDownloadChunkAck.class, 4, Side.SERVER);
//        INSTANCE.registerMessage(MessageDownloadEnd.class, MessageDownloadEnd.class, 5, Side.CLIENT);
    }

    public void sendTo(IMessage message, EntityPlayerMP player) {
        player.playerNetServerHandler.sendPacketToPlayer(message.toPacket());
    }
}
