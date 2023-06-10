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
 * Restores player's food level to its maximum.
 */
public final class FeedCommand implements CommandExecutor {

    private static final int MAX_FOOD_LEVEL = 20;

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player player) {
            player.setFoodLevel(MAX_FOOD_LEVEL);
            player.sendMessage(Component.text(ChatMessage.ON_COMMAND_FEED.getMessage(), NamedTextColor.GREEN));
        }
        return true;
    }
}
