package org.amirov.mctelegramchat.commands;

/**
 * Contains the names of some commands.
 */
public enum CommandName {
    DIE_COMMAND("die"),
    GOD_COMMAND("god"),
    FEED_COMMAND("feed"),
    KILL_COMMAND("kill");

    private final String name;

    CommandName(String name) { this.name = name; }

    public String getName() { return name; }
}