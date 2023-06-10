package org.amirov.mctelegramchat.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Makes a player invincible.
 * <p>
 * This command can be run only by a player himself.
 */
public final class GodCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (player.isInvulnerable()) {
                player.setInvulnerable(false);
                player.sendMessage(Component.text(ChatMessage.ON_COMMAND_GOD_OFF.getMessage(), NamedTextColor.GREEN));
            } else {
                player.setInvulnerable(true);
                player.sendMessage(Component.text(ChatMessage.ON_COMMAND_GOD_ON.getMessage(), NamedTextColor.GREEN));
            }
        }
        return true;
    }
}
