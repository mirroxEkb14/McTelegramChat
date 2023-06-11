package org.amirov.mctelegramchat.commands;

import net.kyori.adventure.text.Component;
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
 * Sets the spawn location for a player.
 */
public record SetSpawnCommand(McTelegramChat plugin) implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player player) {
            final Location location = player.getLocation();
            plugin.getConfig().set(ConfigProperty.SPAWN_LOCATION.getKeyName(), location);
            plugin.saveConfig();
            player.sendMessage(Component.text(ChatMessage.ON_COMMAND_SET_SPAWN.getMessage()));
        }
        return true;
    }
}
