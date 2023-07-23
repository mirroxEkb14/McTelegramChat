package org.amirov.mctelegramchat.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.amirov.mctelegramchat.properties.ConfigProperty;
import org.amirov.mctelegramchat.properties.Symbol;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Sets a temporary home point.
 */
public record StaffHomeCommand(Plugin plugin) implements CommandExecutor {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final String SET_COMMAND = "set";
    private static final String RETURN_COMMAND = "return";
    private static final String RELOAD_COMMAND = "reload";

    private static final byte COMMAND_ARGUMENTS_ONE_VALUE = 1;
    private static final byte FIRST_COMMAND_ARGUMENT_INDEX = 0;

    private static final byte AMPERSAND_CHAR_INDEX = 0;

    private static final String CONFIG_SECTION_DELIMITER = ".";

    private static final String CONFIG_SECTION_X_COORDINATE = "x";
    private static final String CONFIG_SECTION_Y_COORDINATE = "y";
    private static final String CONFIG_SECTION_Z_COORDINATE = "z";
//</editor-fold>

    private static String currentPlayerSectionName;

    /**
     * If-else blocks:
     * <ol>
     * <li> /staffhome set - Set a Temporary Home
     * <li> /staffhome return - Return to Home and Remove it
     * <li> /staffhome *name* - Teleport to a Temporary Home
     * <li> /staffhome reload - Reload the configuration
     * <li> /staffhome - List of all the available commands
     * </ol>
     *
     * @param sender Player who triggered the command.
     * @param command Executed command itself.
     * @param label Alias of the command.
     * @param args Command arguments.
     *
     * @return Always {@code true} since it is a valid command.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        final boolean staffHomeEnabled = plugin.getConfig().getBoolean(ConfigProperty.STAFFHOME_ENABLE.getKeyName());
        if (sender instanceof Player player && staffHomeEnabled) {
            if (isSetCommand(args)) {
                final boolean isStaffHomeLocation = isStaffHomeLocation(getSectionName(player.getName()));
                if (isStaffHomeLocation) {
                    performSetCommand(player, true);
                    return true;
                }
                performSetCommand(player, false);
            } else if (isReturnCommand(args)) {
                performReturnCommand(player);
            } else if (isReloadCommand(args)) {
                performReloadCommand(player);
            } else if (isToPlayerHomeCommand(args)) {
                performTpToPlayerHomeCommand(player, args[FIRST_COMMAND_ARGUMENT_INDEX]);
            } else {
                player.sendMessage(getLoreMessage());
            }
        }
        return true;
    }

    /**
     * This method provides a message with all the available commands that can be performed (command arguments).
     */
    @Contract(" -> new")
    private @NotNull String getLoreMessage() {
        return ChatColor.translateAlternateColorCodes(
                Symbol.AMPERSAND.getSymbol().charAt(AMPERSAND_CHAR_INDEX),
                ChatMessage.ON_COMMAND_STAFFHOME_LORE.getMessage());
    }

    /**
     * The following methods perform the {@code tp to Temporary Home} command.
     */
    private boolean isToPlayerHomeCommand(String @NotNull [] commandArgs) {
        return commandArgs.length == COMMAND_ARGUMENTS_ONE_VALUE;
    }

    private void performTpToPlayerHomeCommand(@NotNull Player player, String targetPlayerName) {
        final Player targetPlayer = Bukkit.getPlayer(targetPlayerName);
        if (targetPlayer == null) {
            player.sendMessage(Component.text(
                    ChatMessage.ON_COMMAND_STAFFHOME_TO_PLAYER_HOME_NO_HOME.getMessage(), NamedTextColor.DARK_RED));
            return;
        }
        currentPlayerSectionName = getSectionName(targetPlayerName);
        final Location returnLocation = new Location(
                targetPlayer.getWorld(),
                getXCoordinateValue(),
                getYCoordinateValue(),
                getZCoordinateValue());
        player.teleport(returnLocation);
        player.sendMessage(getMessageAfterTpToPlayerHome(targetPlayerName));
    }

    private @NotNull TextComponent getMessageAfterTpToPlayerHome(String targetPlayerName) {
        final String messageWithName = String.format(
                ChatMessage.ON_COMMAND_STAFFHOME_TO_PLAYER_HOME.getMessage(), targetPlayerName);
        final TextComponent tpToPlayer = Component.text(
                messageWithName, NamedTextColor.GREEN);
        final TextComponent xCoordinate = Component.text(
                getXCoordinateValueRounded(getXSectionName()), NamedTextColor.GRAY);
        final TextComponent yCoordinate = Component.text(
                getYCoordinateValueRounded(getYSectionName()), NamedTextColor.GRAY);
        final TextComponent zCoordinate = Component.text(
                getZCoordinateValueRounded(getZSectionName()), NamedTextColor.GRAY);
        return tpToPlayer
                .append(xCoordinate)
                .append(Component.text(" "))
                .append(yCoordinate)
                .append(Component.text(" "))
                .append(zCoordinate);
    }

    /**
     * The following methods perform the {@code reload} command.
     */
    private boolean isReloadCommand(String @NotNull [] commandArgs) {
        return commandArgs.length == COMMAND_ARGUMENTS_ONE_VALUE &&
                commandArgs[FIRST_COMMAND_ARGUMENT_INDEX].equalsIgnoreCase(RELOAD_COMMAND);
    }

    private void performReloadCommand(@NotNull Player player) {
        plugin.reloadConfig();
        player.sendMessage(Component.text(
                ChatMessage.ON_COMMAND_STAFFHOME_RELOAD.getMessage(), NamedTextColor.GRAY));
    }

    /**
     * The following methods perform the {@code return} command.
     */
    private boolean isReturnCommand(String @NotNull [] commandArgs) {
        return commandArgs.length == COMMAND_ARGUMENTS_ONE_VALUE &&
                commandArgs[FIRST_COMMAND_ARGUMENT_INDEX].equalsIgnoreCase(RETURN_COMMAND);
    }

    private void performReturnCommand(@NotNull Player player) {
        final String sectionPlayerName = getSectionName(player.getName());
        final boolean isStaffHomeLocation = isStaffHomeLocation(sectionPlayerName);
        if (isStaffHomeLocation) {
            final Location returnLocation = new Location(
                    player.getWorld(),
                    getXCoordinateValue(),
                    getYCoordinateValue(),
                    getZCoordinateValue());
            player.teleport(returnLocation);
            player.sendMessage(Component.text(
                    ChatMessage.ON_COMMAND_STAFFHOME_RETURNED_HOME.getMessage(), NamedTextColor.GREEN));
            clearSection();
            return;
        }
        player.sendMessage(Component.text(
                ChatMessage.ON_COMMAND_STAFFHOME_RETURN_NO_SAVED_LOCATION.getMessage(), NamedTextColor.DARK_RED));
    }

    private void clearSection() {
        plugin.getConfig().set(currentPlayerSectionName, null);
        plugin.saveConfig();
    }

    private double getZCoordinateValue() {
        return plugin.getConfig().getDouble(CONFIG_SECTION_DELIMITER + getZSectionName());
    }

    private double getYCoordinateValue() {
        return plugin.getConfig().getDouble(CONFIG_SECTION_DELIMITER + getYSectionName());
    }

    private double getXCoordinateValue() {
        return plugin.getConfig().getDouble(CONFIG_SECTION_DELIMITER + getXSectionName());
    }

    private boolean isStaffHomeLocation(@NotNull String playerName) {
        return plugin.getConfig().isConfigurationSection(playerName);
    }

    /**
     * The following methods perform the {@code set} command.
     */
    private void performSetCommand(@NotNull Player player, boolean overriding) {
        if (overriding) {
            player.sendMessage(getMessageAfterReset());
        }
        final Location l = player.getLocation();
        currentPlayerSectionName = getSectionName(player.getName());
        plugin.getConfig().createSection(currentPlayerSectionName);

        final String xSectionName = getXSectionName();
        plugin.getConfig().set(xSectionName, l.getX());
        final String ySectionName = getYSectionName();
        plugin.getConfig().set(ySectionName, l.getY());
        final String zSectionName = getZSectionName();
        plugin.getConfig().set(zSectionName, l.getZ());

        plugin.saveConfig();
        player.sendMessage(getMessageAfterSet(xSectionName, ySectionName, zSectionName));
    }

    private @NotNull TextComponent getMessageAfterReset() {
        final TextComponent tempHome = Component.text(
                ChatMessage.ON_COMMAND_STAFFHOME_RESET.getMessage(), NamedTextColor.GREEN);
        final TextComponent xCoordinate = Component.text(
                getXCoordinateValueRounded(getXSectionName()), NamedTextColor.GRAY);
        final TextComponent yCoordinate = Component.text(
                getYCoordinateValueRounded(getYSectionName()), NamedTextColor.GRAY);
        final TextComponent zCoordinate = Component.text(
                getZCoordinateValueRounded(getZSectionName()), NamedTextColor.GRAY);
        return tempHome
                .append(xCoordinate)
                .append(Component.text(" "))
                .append(yCoordinate)
                .append(Component.text(" "))
                .append(zCoordinate);
    }

    @Contract(pure = true)
    private @NotNull TextComponent getMessageAfterSet(String xName, String yName, String zName) {
        final TextComponent tempHome = Component.text(
                ChatMessage.ON_COMMAND_STAFFHOME_SET.getMessage(), NamedTextColor.GREEN);
        final TextComponent xCoordinate = Component.text(
                getXCoordinateValueRounded(xName), NamedTextColor.GRAY);
        final TextComponent yCoordinate = Component.text(
                getYCoordinateValueRounded(yName), NamedTextColor.GRAY);
        final TextComponent zCoordinate = Component.text(
                getZCoordinateValueRounded(zName), NamedTextColor.GRAY);
        return tempHome
                .append(xCoordinate)
                .append(Component.text(" "))
                .append(yCoordinate)
                .append(Component.text(" "))
                .append(zCoordinate);
    }

    private double getZCoordinateValueRounded(String zName) {
        return Math.round(plugin.getConfig().getDouble(CONFIG_SECTION_DELIMITER + zName));
    }

    private double getYCoordinateValueRounded(String yName) {
        return Math.round(plugin.getConfig().getDouble(CONFIG_SECTION_DELIMITER + yName));
    }

    private double getXCoordinateValueRounded(String xName) {
        return Math.round(plugin.getConfig().getDouble(CONFIG_SECTION_DELIMITER + xName));
    }

    @Contract(pure = true)
    private @NotNull String getZSectionName() {
        return currentPlayerSectionName + CONFIG_SECTION_DELIMITER + CONFIG_SECTION_Z_COORDINATE;
    }

    @Contract(pure = true)
    private @NotNull String getYSectionName() {
        return currentPlayerSectionName + CONFIG_SECTION_DELIMITER + CONFIG_SECTION_Y_COORDINATE;
    }

    @Contract(pure = true)
    private @NotNull String getXSectionName() {
        return currentPlayerSectionName + CONFIG_SECTION_DELIMITER + CONFIG_SECTION_X_COORDINATE;
    }

    @Contract(pure = true)
    private @NotNull String getSectionName(String playerName) {
        return ConfigProperty.SAVED_LOCATIONS.getKeyName() + CONFIG_SECTION_DELIMITER + playerName;
    }

    @Contract(pure = true)
    private boolean isSetCommand(String @NotNull [] commandArgs) {
        return commandArgs.length == COMMAND_ARGUMENTS_ONE_VALUE &&
                commandArgs[FIRST_COMMAND_ARGUMENT_INDEX].equalsIgnoreCase(SET_COMMAND);
    }
}
