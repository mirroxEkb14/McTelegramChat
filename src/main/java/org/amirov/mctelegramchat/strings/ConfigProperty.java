package org.amirov.mctelegramchat.strings;

import org.amirov.mctelegramchat.telegrambot.SPWBot;

/**
 * Contains the names of the keys that are in the "config.yml" file.
 * <p>
 * This Enum is required in {@link SPWBot} where the values of these keys are needed.
 */
public enum ConfigProperty {
    TELEGRAM_BOT_API_KEY("telegram-bot-api-key"),
    TELEGRAM_BOT_USERNAME("telegram-bot-username"),
    TELEGRAM_BOT_ADMIN_ID("telegram-bot-admin-id"),
    PLAYER_KILL("player-kill"),
    SPAWN_LOCATION("spawn"),
    WORLD_BORDER("world-border"),
    BORDER("border"),
    LOBBY_WORLD("lobby-world"),
    MOTD_ENABLED("motd"),
    MOTD_MESSAGE("motd-message"),
    STAFFHOME_ENABLE("staffhome-enable"),
    SAVED_LOCATIONS("savedlocations"),
    MONGODB_CONNECTION_STRING("mongodb-connection-string"),
    MONGODB_DATABASE_NAME("mongodb-database-name"),
    MONGODB_COLLECTION_NAME("mongodb-collection-name"),
    LOCKABLE_BLOCKS("lockable-blocks");

    private final String keyName;

    ConfigProperty(String value) { this.keyName = value; }

    public String getKeyName() { return this.keyName; }
}
