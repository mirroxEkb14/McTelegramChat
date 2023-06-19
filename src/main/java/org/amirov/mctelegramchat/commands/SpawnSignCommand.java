package org.amirov.mctelegramchat.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Spawns a sign on the block where the player is currently standing.
 */
public final class SpawnSignCommand implements CommandExecutor {

//<editor-fold default-state="collapsed" desc="Private Constants">
    /**
     * These constants are needed to process the command arguments passed by a player.
     * <p>
     * The command itself: /spawnsign {row number} {text}
     */
    private static final int COMMAND_ARGUMENTS_AMOUNT = 2;
    private static final int ROW_INDEX = 0;
    private static final int TEXT_INDEX = 1;
    private static final int INDEX_SUBTRAHEND = 1;
//</editor-fold>

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (!areCommandArguments(args)) {
                player.sendMessage(ChatMessage.COMMAND_SPAWN_SIGN_WRONG_COMMAND_ARGUMENTS_FIRST.getMessage());
                player.sendMessage(ChatMessage.COMMAND_SPAWN_SIGN_WRONG_COMMAND_ARGUMENTS_SECOND.getMessage());
            } else if (!isIndex(args)) {
                player.sendMessage(ChatMessage.COMMAND_SPAWN_SIGN_INDEX_ERROR.getMessage());
            } else {
                final Location playerLocation = player.getLocation();
                player.getWorld().getBlockAt(playerLocation).setType(Material.OAK_SIGN);
                Sign sign = (Sign) player.getWorld().getBlockAt(playerLocation).getState();

                final int row = Integer.parseInt(args[ROW_INDEX]) - INDEX_SUBTRAHEND;
                final TextComponent text = Component.text(args[TEXT_INDEX]);

                sign.line(row, text);
                sign.update();
            }
        }
        return true;
    }

    /**
     * Checks if the second value of the array of command arguments is a number.
     * <p>
     * This second value represents an index of a row for a sign.
     *
     * @param args Command arguments.
     * @return {@code true} if the index is a number, {@code false} otherwise.
     */
    private boolean isIndex(String @NotNull [] args) {
        try {
            final int parsedIndex = Integer.parseInt(args[ROW_INDEX]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if a player entered some command arguments after the command itself.
     *
     * @param args Command arguments passed by user (player's text after the command itself).
     * @return {@code true} if the amount of command arguments equals to the appropriate constant.
     */
    private boolean areCommandArguments(String @NotNull [] args) {
        return args.length == COMMAND_ARGUMENTS_AMOUNT;
    }
}
