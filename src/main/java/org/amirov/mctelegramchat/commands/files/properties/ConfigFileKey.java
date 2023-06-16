package org.amirov.mctelegramchat.commands.files.properties;

/**
 * Contains the keys for custom config files.
 */
public enum ConfigFileKey {
    PLAYER_UUID("player-uuid"),
    PLAYER_LOCATION("player-location-");

    private final String key;

    ConfigFileKey(String key) {this.key = key; }

    public String getKey() { return key; }
}
