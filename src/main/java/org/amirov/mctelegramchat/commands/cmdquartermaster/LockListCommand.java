package org.amirov.mctelegramchat.commands.cmdquartermaster;

import org.amirov.mctelegramchat.commands.SubCommand;
import org.amirov.mctelegramchat.gui.LockListGUI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Opens a gui with all the player's locked chests.
 */
public final class LockListCommand extends SubCommand {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final String LOCK_LIST_COMMAND_NAME = "manage";
    private static final String LOCK_LIST_COMMAND_DESC = "Management of Locks";
    private static final String LOCK_LIST_COMMAND_SYNTAX = "/qm manage";
//</editor-fold>

    @Override
    public @NotNull String getName() { return LOCK_LIST_COMMAND_NAME; }

    @Override
    public @NotNull String getDescription() { return LOCK_LIST_COMMAND_DESC; }

    @Override
    public @NotNull String getSyntax() { return LOCK_LIST_COMMAND_SYNTAX; }

    @Override
    public void perform(@NotNull Player performer, String[] args) {
        LockListGUI.openLockListGUI(performer);
    }
}
