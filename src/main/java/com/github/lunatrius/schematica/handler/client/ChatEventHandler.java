package com.github.lunatrius.schematica.handler.client;

import com.github.lunatrius.schematica.Schematica;
import com.github.lunatrius.schematica.client.printer.SchematicPrinter;
import com.github.lunatrius.schematica.reference.Names;
import com.github.lunatrius.schematica.reference.Reference;

public class ChatEventHandler {
    public static final ChatEventHandler INSTANCE = new ChatEventHandler();

    public int chatLines = 0;

    private ChatEventHandler() {}

    public void onClientChatReceivedEvent(String message) {
        if (this.chatLines < 20) {
            this.chatLines++;
            Reference.logger.debug("Message #{}: {}", this.chatLines, message);
            if (message.contains(Names.SBC.DISABLE_PRINTER)) {
                Reference.logger.info("Printer is disabled on this server.");
                SchematicPrinter.INSTANCE.setEnabled(false);
            }
            if (message.contains(Names.SBC.DISABLE_SAVE)) {
                Reference.logger.info("Saving is disabled on this server.");
                Schematica.getProxy().isSaveEnabled = false;
            }
            if (message.contains(Names.SBC.DISABLE_LOAD)) {
                Reference.logger.info("Loading is disabled on this server.");
                Schematica.getProxy().isLoadEnabled = false;
            }
        }
    }
}
