package org.amirov.mctelegramchat.commands.properties;

/**
 * Contains the names of all the commands.
 */
public enum CommandName {
    MAIN_MENU_COMMAND("menu"),
    SET_SPAWN_COMMAND("setspawn"),
    SPAWN_COMMAND("spawn"),
    SAVE_LOCATION_COMMAND("saveloc"),
    SPAWN_SIGN_COMMAND("spawnsign"),
    BAN_COMMAND("ban"),
    RANDOM_TP_COMMAND("rtp"),
    VANISH_COMMAND("vanish"),
    STAFF_HOME_COMMAND("staffhome"),
    SCORE_BOARD_COMMAND("scoreboard"),
    PRANK_COMMAND("prank"),
    QUARTERMASTER_COMMAND("quartermaster");

    private final String name;

    CommandName(String name) { this.name = name; }

    public String getName() { return name; }
}
