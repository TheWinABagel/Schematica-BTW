package com.github.lunatrius.schematica.network.message;

import com.github.lunatrius.schematica.Schematica;
import com.github.lunatrius.schematica.client.printer.SchematicPrinter;
import com.github.lunatrius.schematica.network.util.ByteBuf;
import com.github.lunatrius.schematica.network.util.IMessage;
import com.github.lunatrius.schematica.network.util.IMessageHandler;
import com.github.lunatrius.schematica.network.util.MessageContext;
import com.github.lunatrius.schematica.reference.Reference;

public class MessageCapabilities implements IMessage, IMessageHandler<MessageCapabilities, IMessage> {
    public static final int ID = 0;
    public boolean isPrinterEnabled;
    public boolean isSaveEnabled;
    public boolean isLoadEnabled;

    public MessageCapabilities() {
        this(false, false, false);
    }

    public MessageCapabilities(boolean isPrinterEnabled, boolean isSaveEnabled, boolean isLoadEnabled) {
        this.isPrinterEnabled = isPrinterEnabled;
        this.isSaveEnabled = isSaveEnabled;
        this.isLoadEnabled = isLoadEnabled;
    }

    @Override
    public IMessage fromBytes(ByteBuf buf) {
        this.isPrinterEnabled = buf.readBoolean();
        this.isSaveEnabled = buf.readBoolean();
        this.isLoadEnabled = buf.readBoolean();
        return this;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.isPrinterEnabled);
        buf.writeBoolean(this.isSaveEnabled);
        buf.writeBoolean(this.isLoadEnabled);
    }

    @Override
    public int id() {
        return ID;
    }

    @Override
    public IMessage onMessage(MessageCapabilities message, MessageContext ctx) {
        SchematicPrinter.INSTANCE.setEnabled(message.isPrinterEnabled);
        Schematica.getProxy().isSaveEnabled = message.isSaveEnabled;
        Schematica.getProxy().isLoadEnabled = message.isLoadEnabled;

        Reference.logger.info("Server capabilities{printer={}, save={}, load={}}", message.isPrinterEnabled, message.isSaveEnabled, message.isLoadEnabled);

        return null;
    }
}
