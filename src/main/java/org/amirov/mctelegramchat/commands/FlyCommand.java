package org.amirov.mctelegramchat.commands;

import net.kyori.adventure.text.Component;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Allows a player to fly.
 */
public final class FlyCommand implements CommandExecutor {

    /**
     * Stores the players who can fly.
     */
    private final ArrayList<Player> flyingPlayersList = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player player) {
            switch (args.length) {
                case 0 -> makePlayerFly(player);
                case 1 -> {
                    final String targetName = args[0];
                    final Player target = Bukkit.getPlayer(targetName);
                    if (target != null) {
                        makePlayerFly(target);
                        return true;
                    }
                    player.sendMessage(Component.text(ChatMessage.ON_COMMAND_FLY_WRONG_COMMAND_ARGUMENTS.getMessage()));
                }
                default -> player.sendMessage(Component.text(
                        ChatMessage.ON_COMMAND_FLY_WRONG_COMMAND_ARGUMENTS.getMessage()));
            }
        }
        return true;
    }

    /**
     * Adds/removes a player from the list, allows/forbids a player to fly, sends a message.
     *
     * @param player Players who will be allowed to fly.
     */
    private void makePlayerFly(Player player) {
        if (flyingPlayersList.contains(player)) {
            flyingPlayersList.remove(player);
            player.setAllowFlight(false);
            player.sendMessage(Component.text(ChatMessage.ON_COMMAND_FLY_OOF.getMessage()));
            return;
        }
        flyingPlayersList.add(player);
        player.setAllowFlight(true);
        player.sendMessage(Component.text(ChatMessage.ON_COMMAND_FLY_ON.getMessage()));
    }
}
