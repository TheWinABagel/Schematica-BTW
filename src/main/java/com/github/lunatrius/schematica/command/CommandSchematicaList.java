package com.github.lunatrius.schematica.command;

import com.github.lunatrius.schematica.FileFilterSchematic;
import com.github.lunatrius.schematica.Schematica;
import com.github.lunatrius.schematica.reference.Names;
import com.github.lunatrius.schematica.reference.Reference;
import com.github.lunatrius.schematica.util.FileUtils;
import emi.shims.java.net.minecraft.text.Text;
import net.minecraft.src.CommandException;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.WrongUsageException;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumChatFormatting;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.LinkedList;

public class CommandSchematicaList extends CommandSchematicaBase {
    private static final FileFilterSchematic FILE_FILTER_SCHEMATIC = new FileFilterSchematic(false);

    @Override
    public String getCommandName() {
        return Names.Command.List.NAME;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return Names.Command.List.Message.USAGE;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] arguments) {
        if (!(sender instanceof EntityPlayer player)) {
            throw new CommandException(Names.Command.Save.Message.PLAYERS_ONLY);
        }
        //todo Schematica list command, seems to rely on links
        player.addChatMessage("Not currently supported, sorry!");
//
//        int page = 0;
//        try {
//            if (arguments.length > 0) {
//                page = Integer.parseInt(arguments[0]) - 1;
//                if (page < 0) {
//                    page = 0;
//                }
//            }
//        } catch (NumberFormatException e) {
//            throw new WrongUsageException(getCommandUsage(sender));
//        }
//
//        int pageSize = 9; //maximum number of lines available without opening chat.
//        int pageStart = page * pageSize;
//        int pageEnd = pageStart + pageSize;
//        int currentFile = 0;
//
//        LinkedList<IChatComponent> componentsToSend = new LinkedList<IChatComponent>();
//
//        File schematicDirectory = Schematica.getProxy().getPlayerSchematicDirectory(player, true);
//        if (schematicDirectory == null) {
//            Reference.logger.warn("Unable to determine the schematic directory for player {}", player);
//            throw new CommandException(Names.Command.Save.Message.PLAYER_SCHEMATIC_DIR_UNAVAILABLE);
//        }
//
//        if (!schematicDirectory.exists()) {
//            if (!schematicDirectory.mkdirs()) {
//                Reference.logger.warn("Could not create player schematic directory {}", schematicDirectory.getAbsolutePath());
//                throw new CommandException(Names.Command.Save.Message.PLAYER_SCHEMATIC_DIR_UNAVAILABLE);
//            }
//        }
//
//        final File[] files = schematicDirectory.listFiles(FILE_FILTER_SCHEMATIC);
//        for (File path : files) {
//            if (currentFile >= pageStart && currentFile < pageEnd) {
//                String fileName = FilenameUtils.removeExtension(path.getName());
//
//                Text text = Text.literal(String.format("%2d (%s): %s [", currentFile + 1, FileUtils.humanReadableByteCount(path.length()), fileName));
//                IChatComponent chatComponent = new ChatComponentText(String.format("%2d (%s): %s [", currentFile + 1, FileUtils.humanReadableByteCount(path.length()), fileName));
//                String removeCommand = String.format("/%s %s", Names.Command.Remove.NAME, fileName);
//
//                IChatComponent removeLink = new ChatComponentTranslation(Names.Command.List.Message.REMOVE)
//                        .setChatStyle(
//                                new ChatStyle()
//                                        .setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, removeCommand))
//                                        .setColor(EnumChatFormatting.RED)
//                        );
//                chatComponent.appendSibling(removeLink);
//                chatComponent.appendText("][");
//
//                String downloadCommand = String.format("/%s %s", Names.Command.Download.NAME, fileName);
//                IChatComponent downloadLink = new ChatComponentTranslation(Names.Command.List.Message.DOWNLOAD)
//                        .setChatStyle(
//                                new ChatStyle()
//                                        .setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, downloadCommand))
//                                        .setColor(EnumChatFormatting.GREEN)
//                        );
//                chatComponent.appendSibling(downloadLink);
//                chatComponent.appendText("]");
//
//                componentsToSend.add(chatComponent);
//            }
//            ++currentFile;
//        }
//
//        if (currentFile == 0) {
//            sender.addChatMessage(new ChatComponentTranslation(Names.Command.List.Message.NO_SCHEMATICS));
//            return;
//        }
//
//        final int totalPages = (currentFile - 1) / pageSize;
//        if (page > totalPages) {
//            throw new CommandException(Names.Command.List.Message.NO_SUCH_PAGE);
//        }
//
//        sender.addChatMessage(new ChatComponentTranslation(Names.Command.List.Message.PAGE_HEADER, page + 1, totalPages + 1)
//                .setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_GREEN)));
//        for (IChatComponent chatComponent : componentsToSend) {
//            sender.addChatMessage(chatComponent);
//        }
    }
}
