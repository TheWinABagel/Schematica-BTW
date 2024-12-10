package com.github.lunatrius.schematica.command;

import net.minecraft.src.CommandBase;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.EntityPlayerMP;

public abstract class CommandSchematicaBase extends CommandBase {
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        // TODO: add logic for the client side when ready
        return super.canCommandSenderUseCommand(sender) || (sender instanceof EntityPlayerMP && getRequiredPermissionLevel() <= 0);
    }
}
