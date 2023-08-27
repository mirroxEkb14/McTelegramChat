package org.amirov.mctelegramchat.commands;

/**
 * Contains the names of all the commands.
 */
public enum CommandName {
    MAIN_MENU_COMMAND("menu"),
    SAVE_LOCATION_COMMAND("saveloc"),
    RANDOM_TP_COMMAND("rtp"),
    VANISH_COMMAND("vanish"),
    STAFF_HOME_COMMAND("staffhome"),
    SCORE_BOARD_COMMAND("scoreboard"),
    PRANK_COMMAND("prank"),
    QUARTERMASTER_COMMAND("quartermaster"),
    SPAWN_COMMAND("spawn"),
    ADMIN_COMMAND("admin");

    private final String name;

    CommandName(String name) { this.name = name; }

    public String getName() { return name; }
}
