package com.github.lunatrius.schematica;

public class ChatEventHandler {
	private final Settings settings = Settings.instance();

	public void onClientChatReceivedEvent(String message) {
		this.settings.chatLines++;

		if (this.settings.isPrinterEnabled && this.settings.chatLines < 10) {
			if (message.contains(Settings.sbcDisablePrinter)) {
				Settings.logger.logInfo("Printer is disabled on this server.");
				this.settings.isPrinterEnabled = false;
			}
			if (message.contains(Settings.sbcDisableSave)) {
				Settings.logger.logInfo("Saving is disabled on this server.");
				this.settings.isSaveEnabled = false;
			}
			if (message.contains(Settings.sbcDisableLoad)) {
				Settings.logger.logInfo("Loading is disabled on this server.");
				this.settings.isLoadEnabled = false;
			}
		}
	}
}
