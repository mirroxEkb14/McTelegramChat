package org.amirov.mctelegramchat.commands.subcommands.admin;

import org.amirov.mctelegramchat.commands.SubCommand;
import org.amirov.mctelegramchat.gui.BanListGUI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Opens an inventory with the heads of the players on the server currently online.
 */
public final class BanCommand extends SubCommand {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final String BAN_COMMAND_NAME = "ban";
    private static final String BAN_COMMAND_DESC = "Open Ban List";
    private static final String BAN_COMMAND_SYNTAX = "/admin ban";
//</editor-fold>

    @Override
    public @NotNull String getName() { return BAN_COMMAND_NAME; }

    @Override
    public @NotNull String getDescription() { return BAN_COMMAND_DESC; }

    @Override
    public @NotNull String getSyntax() { return BAN_COMMAND_SYNTAX; }

    @Override
    public void perform(@NotNull Player performer, String[] args) { BanListGUI.openBanListGUI(performer); }
}
