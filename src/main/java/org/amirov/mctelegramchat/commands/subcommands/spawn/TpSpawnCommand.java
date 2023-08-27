package org.amirov.mctelegramchat.commands.subcommands.spawn;

import org.amirov.mctelegramchat.commands.SubCommand;
import org.amirov.mctelegramchat.strings.ChatMessage;
import org.amirov.mctelegramchat.strings.ConfigProperty;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Teleports a player to the spawn point if it was previously set, doesn't teleport otherwise.
 * <p>
 * Takes the spawn location of this player from a custom config file and teleport the player to this point. If
 * there is no such a location set in the file, sends a notification to the player.
 */
public final class TpSpawnCommand extends SubCommand {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final String COMMAND_NAME_DELIMITER = "/";

    private static final String TP_COMMAND_NAME = String.format("teleport%stp", COMMAND_NAME_DELIMITER);
    private static final String TP_COMMAND_DESC = "Teleports to the Spawn Location";
    private static final String TP_COMMAND_SYNTAX = "/spawn tp";

    private static final String SPAWN_NOT_SET_MESSAGE =
            ChatColor.RED + ChatMessage.ON_COMMAND_TP_SPAWN_NOT_SET.getMessage() + ChatColor.ITALIC;
    private static final String TELEPORTED_MESSAGE =
            ChatColor.BLUE + ChatMessage.ON_COMMAND_TP_TELEPORTED.getMessage();
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Private Instance Variables">
    private final Plugin plugin;
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Constructor">
    public TpSpawnCommand(Plugin plugin) { this.plugin = plugin; }
//</editor-fold>

    @Override
    public @NotNull String getName() { return TP_COMMAND_NAME; }

    @Override
    public @NotNull String getDescription() { return TP_COMMAND_DESC; }

    @Override
    public @NotNull String getSyntax() { return TP_COMMAND_SYNTAX; }

    @Override
    public void perform(@NotNull Player performer, String[] args) {
        final Location location = plugin.getConfig().getLocation(ConfigProperty.SPAWN_LOCATION.getKeyName());
        if (location != null) {
            performer.teleport(location);
            performer.sendMessage(TELEPORTED_MESSAGE);
            return;
        }
        performer.sendMessage(SPAWN_NOT_SET_MESSAGE);
    }
}
