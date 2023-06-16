package org.amirov.mctelegramchat.commands.files.properties;

/**
 * Contains the names for config files.
 */
public enum ConfigFileName {
    LOCATION_FILE("location.yml");

    private final String name;

    ConfigFileName(String name) { this.name = name; }

    public String getName() { return name; }
}
