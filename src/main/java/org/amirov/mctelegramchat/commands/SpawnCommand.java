package org.amirov.mctelegramchat.commands;

import org.amirov.mctelegramchat.McTelegramChat;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.amirov.mctelegramchat.properties.ConfigProperty;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Teleports a player to the spawn point if it was previously set, doesn't teleport otherwise.
 */
public record SpawnCommand(McTelegramChat plugin) implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player player) {
            final Location location = plugin.getConfig().getLocation(ConfigProperty.SPAWN_LOCATION.getKeyName());
            if (location != null) {
                player.teleport(location);
                player.sendMessage(ChatMessage.ON_COMMAND_SPAWN_TELEPORTED.getMessage());
                return true;
            }
            player.sendMessage(ChatMessage.ON_COMMAND_SPAWN_NOT_SET.getMessage());
        }
        return true;
    }
}
