package org.amirov.mctelegramchat.commands.subcommands.spawn;

import org.amirov.mctelegramchat.commands.SubCommand;
import org.amirov.mctelegramchat.strings.ConfigProperty;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * Sets spawn location for this player saving it to a custom config file.
 */
public final class SetSpawnCommand extends SubCommand {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final String SPAWN_COMMAND_NAME = "set";
    private static final String SPAWN_COMMAND_DESC = "Set a Spawn Location";
    private static final String SPAWN_COMMAND_SYNTAX = "/spawn set";

    private static final String SPAWN_SET =
            ChatColor.BLUE + "Spawn Location Set";
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Private Instance Variables">
    private final Plugin plugin;
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Constructor">
    public SetSpawnCommand(Plugin plugin) { this.plugin = plugin; }
//</editor-fold>

    @Override
    public @NotNull String getName() { return SPAWN_COMMAND_NAME; }

    @Override
    public @NotNull String getDescription() { return SPAWN_COMMAND_DESC; }

    @Override
    public @NotNull String getSyntax() { return SPAWN_COMMAND_SYNTAX; }

    @Override
    public void perform(@NotNull Player performer, String[] args) {
        final Location location = performer.getLocation();
        plugin.getConfig().set(ConfigProperty.SPAWN_LOCATION.getKeyName(), location);
        plugin.saveConfig();
        performer.sendMessage(SPAWN_SET);
    }
}
