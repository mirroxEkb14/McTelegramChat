package org.amirov.mctelegramchat.commands.nonsubcommands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.strings.ConfigProperty;
import org.amirov.mctelegramchat.strings.Symbol;
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

    private static final String STAFFHOME_LORE = """
            &7==&a&lStaff&eHome&7==
            &6&o/staffhome set &7- &9Set a Temporary Home
            &6&o/staffhome return &7- &9Return to Temporary Home and Remove It
            &6&o/staffhome <name> &7- &9Teleport to a Temporary Home
            &6&o/staffhome reload &7- &9Reload the Configuration
            &7=========================""";
    private static final String TO_PLAYER_HOME = "Teleported to Temporary Home of %s to: ";
    private static final TextComponent TO_PLAYER_HOME_NO_HOME = Component.text(
            "No Temporary Home for This Player", NamedTextColor.RED);
    private static final TextComponent RELOAD = Component.text(
            "Staffhome Configuration Reloaded", NamedTextColor.WHITE);
    private static final TextComponent RETURNED_HOME = Component.text(
            "At Temporary Home Now", NamedTextColor.BLUE);
    private static final TextComponent RETURN_NO_HOME_SAVED = Component.text(
            "No Temporary Home Set", NamedTextColor.RED);
    private static final TextComponent RESET = Component.text(
            "Overriding Temporary Home at: ", NamedTextColor.GREEN);
    private static final TextComponent SET = Component.text(
            "Temporary Home at: ", NamedTextColor.GREEN);

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
                STAFFHOME_LORE);
    }

    /**
     * The following methods perform the {@code tp to Temporary Home} command.
     */
    private boolean isToPlayerHomeCommand(String @NotNull [] commandArgs) {
        return commandArgs.length == COMMAND_ARGUMENTS_ONE_VALUE;
    }

    /**
     * Teleport a player to another player's staffhome location.
     *
     * @param player Player who performed the command.
     * @param targetPlayerName Player's name typed as a command argument.
     */
    private void performTpToPlayerHomeCommand(@NotNull Player player, String targetPlayerName) {
        final Player targetPlayer = Bukkit.getPlayer(targetPlayerName);
        if (targetPlayer == null) {
            player.sendMessage(TO_PLAYER_HOME_NO_HOME);
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

    /**
     * Forms and returns a message for a player who's been teleported to s staffhome location.
     *
     * @param targetPlayerName
     *
     * @return {@link TextComponent} representing a message that is sent after teleportation.
     */
    private @NotNull TextComponent getMessageAfterTpToPlayerHome(String targetPlayerName) {
        final String messageWithName = String.format(TO_PLAYER_HOME, targetPlayerName);
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
                .append(Component.text(Symbol.SPACE.getSymbol()))
                .append(yCoordinate)
                .append(Component.text(Symbol.SPACE.getSymbol()))
                .append(zCoordinate);
    }

    /**
     * The following methods perform the {@code reload} command.
     */
    private boolean isReloadCommand(String @NotNull [] commandArgs) {
        return commandArgs.length == COMMAND_ARGUMENTS_ONE_VALUE &&
                commandArgs[FIRST_COMMAND_ARGUMENT_INDEX].equalsIgnoreCase(RELOAD_COMMAND);
    }

    /**
     * Reloads the config file.
     *
     * @param player Player who performed the command.
     */
    private void performReloadCommand(@NotNull Player player) {
        plugin.reloadConfig();
        player.sendMessage(RELOAD);
    }

    /**
     * The following methods perform the {@code return} command.
     */
    private boolean isReturnCommand(String @NotNull [] commandArgs) {
        return commandArgs.length == COMMAND_ARGUMENTS_ONE_VALUE &&
                commandArgs[FIRST_COMMAND_ARGUMENT_INDEX].equalsIgnoreCase(RETURN_COMMAND);
    }

    /**
     * Performs the "return" subcommand that teleports this player to the saved staffhome location.
     *
     * @param player Player who typed the command in.
     */
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
            player.sendMessage(RETURNED_HOME);
            clearSection();
            return;
        }
        player.sendMessage(RETURN_NO_HOME_SAVED);
    }

    /**
     * Saves a new section with this player's name.
     */
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
        final TextComponent xCoordinate = Component.text(
                getXCoordinateValueRounded(getXSectionName()), NamedTextColor.GRAY);
        final TextComponent yCoordinate = Component.text(
                getYCoordinateValueRounded(getYSectionName()), NamedTextColor.GRAY);
        final TextComponent zCoordinate = Component.text(
                getZCoordinateValueRounded(getZSectionName()), NamedTextColor.GRAY);
        return RESET
                .append(xCoordinate)
                .append(Component.text())
                .append(yCoordinate)
                .append(Component.text(Symbol.SPACE.getSymbol()))
                .append(zCoordinate);
    }

    @Contract(pure = true)
    private @NotNull TextComponent getMessageAfterSet(String xName, String yName, String zName) {
        final TextComponent xCoordinate = Component.text(
                getXCoordinateValueRounded(xName), NamedTextColor.GRAY);
        final TextComponent yCoordinate = Component.text(
                getYCoordinateValueRounded(yName), NamedTextColor.GRAY);
        final TextComponent zCoordinate = Component.text(
                getZCoordinateValueRounded(zName), NamedTextColor.GRAY);
        return SET
                .append(xCoordinate)
                .append(Component.text(Symbol.SPACE.getSymbol()))
                .append(yCoordinate)
                .append(Component.text(Symbol.SPACE.getSymbol()))
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
