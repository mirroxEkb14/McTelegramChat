package org.amirov.mctelegramchat.commands;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Defines what a subcommand is and what methods it has.
 * <p>
 * Definition of the implementation.
 */
public abstract class SubCommand {

    /**
     * Provides a name of this subcommand.
     * <p>
     * Command {@code prank} has a subcommand of {@code explode}, the latter would be that name.
     *
     * @return Name of a subcommand.
     */
    public abstract @NotNull String getName();

    /**
     * Provides a description of this subcommand.
     *
     * @return Description of a subcommand.
     */
    public abstract @NotNull String getDescription();

    /**
     * Provides a syntax of this subcommand. How the whole command should look like.
     * <p>
     * {@code /prank explode *player_name*}
     *
     * @return Syntax of a subcommand.
     */
    public abstract @NotNull String getSyntax();

    /**
     * Executes the code of this subcommand.
     *
     * @param performer {@link Player} object of a player who performed the command.
     * @param args {@link String} arrays of the command arguments.
     */
    public abstract void perform(@NotNull Player performer, String[] args);
}
