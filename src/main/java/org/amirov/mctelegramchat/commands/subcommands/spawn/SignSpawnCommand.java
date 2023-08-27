package org.amirov.mctelegramchat.commands.subcommands.spawn;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.commands.SubCommand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Spawns a sign on the block where the player is currently standing.
 */
public final class SignSpawnCommand extends SubCommand {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final String SIGN_COMMAND_NAME = "sign";
    private static final String SIGN_COMMAND_DESC = "Spawns a Sign with Your Text";
    private static final String SIGN_COMMAND_SYNTAX = "/spawn sign";

    private static final String DEFAULT_STRING = "";
    private static final TextComponent INDEX_ERROR = Component.text(
            "Row Number Must Be from 1 to 4", NamedTextColor.RED);

    private static final int ROW_INDEX = 1;
    private static final int ROW_LOWER_BOUND = 1;
    private static final int ROW_UPPER_BOUND = 4;
    private static final int TEXT_INDEX = 2;
    private static final int INDEX_SUBTRAHEND = 1;
//</editor-fold>

    @Override
    public @NotNull String getName() { return SIGN_COMMAND_NAME; }

    @Override
    public @NotNull String getDescription() { return SIGN_COMMAND_DESC; }

    @Override
    public @NotNull String getSyntax() { return SIGN_COMMAND_SYNTAX; }

    @Override
    public void perform(@NotNull Player performer, String[] args) {
        if (!isIndex(args) || !isIndexCorrect(args)) {
            performer.sendMessage(INDEX_ERROR);
        } else {
            final Location playerLocation = performer.getLocation();
            performer.getWorld().getBlockAt(playerLocation).setType(Material.OAK_SIGN);
            final Sign sign = (Sign) performer.getWorld().getBlockAt(playerLocation).getState();

            final int row = Integer.parseInt(args[ROW_INDEX]) - INDEX_SUBTRAHEND;
            TextComponent text = Component.text(DEFAULT_STRING);
            try { text = Component.text(args[TEXT_INDEX]);
            } catch (ArrayIndexOutOfBoundsException ignored) {}

            sign.line(row, text);
            sign.update();
        }
    }

    /**
     * Determines either the user called this subcommand or not.
     *
     * @param subcommandName Name of the subcommand typed by the user.
     *
     * @return {@code true}, if the passed subcommand name equals to this subcommands name.
     */
    public static boolean isSignSpawnSubcommand(@NotNull String subcommandName) {
        return subcommandName.equalsIgnoreCase(SIGN_COMMAND_NAME);
    }

    /**
     * Checks if the third value of the array of command arguments is a number.
     * <p>
     * This third value represents an index of a row for a sign.
     *
     * @param args Command arguments.
     * @return {@code true}, if the index is a number, {@code false} otherwise.
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
     * Checks if the third value of the array of command arguments is a number within required bounds.
     *
     * @param args Command arguments.
     * @return {@code true}, if the index is a number within the needed range, {@code false} otherwise.
     */
    private boolean isIndexCorrect(String @NotNull [] args) {
        final int index = Integer.parseInt(args[ROW_INDEX]);
        return index >= ROW_LOWER_BOUND && index <= ROW_UPPER_BOUND;
    }
}
