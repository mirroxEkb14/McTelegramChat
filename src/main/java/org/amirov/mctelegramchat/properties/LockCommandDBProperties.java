package org.amirov.mctelegramchat.properties;

/**
 * @author zea1ot 7/25/2023
 */
public enum LockCommandDBProperties {
    PLAYER_UUID_KEY_NAME("uuid"),
    BLOCK_TYPE_KEY_NAME("type"),
    BLOCK_TYPE_VALUE_NAME("chest"),
    BLOCK_LOCATION_KEY_NAME("location"),
    CREATION_DATE_KEY_NAME("creation-date"),
    ACCESS_KEY_NAME("access"),
    X_COORDINATE_VALUE("x"),
    Y_COORDINATE_VALUE("y"),
    Z_COORDINATE_VALUE("z");

    private final String key;

    private static final String SAVE_COMMAND = "$set";

    LockCommandDBProperties(String key) { this.key = key; }

    public String getKey() { return key; }

    public static String getSaveCommand() { return SAVE_COMMAND; }
}
