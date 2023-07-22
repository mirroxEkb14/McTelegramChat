package org.amirov.mctelegramchat.commands.properties;

/**
 * Contains the names of all the commands.
 */
public enum CommandName {
    MENU_COMMAND("menu"),
    KILL_COMMAND("kill"),
    SET_SPAWN_COMMAND("setspawn"),
    SPAWN_COMMAND("spawn"),
    SAVE_LOCATION_COMMAND("saveloc"),
    SPAWN_SIGN_COMMAND("spawnsign"),
    BAN_COMMAND("ban"),
    RANDOM_TP_COMMAND("rtp");

    private final String name;

    CommandName(String name) { this.name = name; }

    public String getName() { return name; }
}
