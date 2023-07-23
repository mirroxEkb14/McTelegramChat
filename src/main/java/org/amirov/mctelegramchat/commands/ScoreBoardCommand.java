package org.amirov.mctelegramchat.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;

/**
 * Opens a score boards for a player.
 */
public final class ScoreBoardCommand implements CommandExecutor {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final String SCORE_BOARD_NAME = "Test";
    private static final String SCORE_BOARD_TITLE = "ScoreBoard Title";

    private static final String SCORE_EXAMPLE_1;
    private static final String SCORE_EXAMPLE_2;

    private static final byte SCORE_LINE_1 = 1;
    private static final byte SCORE_LINE_2 = 2;
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Static block">
    static {
        SCORE_EXAMPLE_1 = ChatColor.GOLD + "Money: $" + ChatColor.GREEN + "1000";
        SCORE_EXAMPLE_2 = ChatColor.GOLD + "Level: " + ChatColor.GREEN + "35";
    }
//</editor-fold>

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player player) {
            final ScoreboardManager sbManager = Bukkit.getScoreboardManager();
            final Scoreboard scoreboard = sbManager.getNewScoreboard();

            final Objective objective = scoreboard.registerNewObjective(
                    SCORE_BOARD_NAME, Criteria.DUMMY, Component.text(SCORE_BOARD_TITLE, NamedTextColor.BLUE));
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            final Score score1 = objective.getScore(SCORE_EXAMPLE_1);
            score1.setScore(SCORE_LINE_2);
            final Score score2 = objective.getScore(SCORE_EXAMPLE_2);
            score2.setScore(SCORE_LINE_1);

            player.setScoreboard(scoreboard);
        }
        return true;
    }
}
