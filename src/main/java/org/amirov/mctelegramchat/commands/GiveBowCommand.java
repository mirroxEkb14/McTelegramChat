package org.amirov.mctelegramchat.commands;

import net.kyori.adventure.text.Component;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.amirov.mctelegramchat.properties.ConfigProperty;
import org.amirov.mctelegramchat.utility.BowUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Gives a teleport bow to a player.
 */
public final class GiveBowCommand implements CommandExecutor {

    private static final int ARROW_AMOUNT = 1;

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission(ConfigProperty.TELEPORT_BOW_GIVE_BOW.getKeyName())) {
                switch (args.length) {
                    case 0 -> {
                        finishGiving(player);
                        player.sendMessage(Component.text(ChatMessage.ON_COMMAND_GIVE_BOW_NOTIFICATION.getMessage()));
                    }
                    case 1 -> {
                        final String targetName = args[0];
                        final Player target = Bukkit.getPlayerExact(targetName);
                        if (target != null) {
                            finishGiving(target);
                            target.sendMessage(Component.text(String.format(
                                    ChatMessage.ON_COMMAND_GIVE_BOW_EXPLANATION.getMessage(), player.getName())));
                            return true;
                        }
                        sendWrongCommandArgumentsMessage(player);
                    }
                    default -> sendWrongCommandArgumentsMessage(player);
                }
                return true;
            }
            player.sendMessage(Component.text(ChatMessage.ON_COMMAND_GIVE_BOW_NO_PERMISSION.getMessage()));
        }
        return true;
    }

    /**
     * Creates a bow and an arrow and adds in to the player's inventory.
     *
     * @param playerToGive Player that will get a bow and an arrow.
     */
    private void finishGiving(@NotNull Player playerToGive) {
        final ItemStack bow = BowUtils.getTeleportBow();
        final ItemStack arrow = new ItemStack(Material.ARROW, ARROW_AMOUNT);
        playerToGive.getInventory().addItem(bow);
        playerToGive.getInventory().addItem(arrow);
    }

    private void sendWrongCommandArgumentsMessage(@NotNull Player player) {
        player.sendMessage(Component.text(ChatMessage.ON_COMMAND_GIVE_BOW_WRONG_COMMAND_ARGUMENTS.getMessage()));
    }
}
