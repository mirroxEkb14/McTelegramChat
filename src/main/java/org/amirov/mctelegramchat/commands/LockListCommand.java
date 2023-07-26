package org.amirov.mctelegramchat.commands;

import org.amirov.mctelegramchat.gui.LockListGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Opens a gui with all the player's locked chests.
 */
public class LockListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player player) {
            LockListGUI.openLockListGUI(player);
        }
        return true;
    }
}
