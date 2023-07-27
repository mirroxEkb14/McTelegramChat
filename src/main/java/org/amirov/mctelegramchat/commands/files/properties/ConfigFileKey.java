package org.amirov.mctelegramchat.commands.files.properties;

/**
 * Contains the keys for custom config files and the name of a file where the location is stored.
 */
public enum ConfigFileKey {
    LOCATION_FILE("location.yml"),
    PLAYER_UUID("player-uuid"),
    PLAYER_LOCATION("player-location-");

    private final String key;

    ConfigFileKey(String key) {this.key = key; }

    public String getKey() { return key; }
}
