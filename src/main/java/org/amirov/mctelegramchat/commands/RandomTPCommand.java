package org.amirov.mctelegramchat.commands;

import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.commands.performers.TeleportPerformer;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

/**
 * Teleports a player to a random location.
 */
public record RandomTPCommand(Plugin plugin) implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player player) {
            player.teleport(TeleportPerformer.generateLocation(plugin, player));
            sendTeleportationMessage(player);
        }
        return true;
    }

    private void sendTeleportationMessage(@NotNull Player player) {
        final int x = player.getLocation().getBlockX();
        final int y = player.getLocation().getBlockY();
        final int z = player.getLocation().getBlockZ();

        final TextComponent coordinates = Component.text(x + " " + y + " " + z, NamedTextColor.LIGHT_PURPLE);
        final TextComponent fullMessage = Component.text(ChatMessage.ON_COMMAND_RTP.getMessage()).append(coordinates);
        player.sendMessage(fullMessage);
    }
}
