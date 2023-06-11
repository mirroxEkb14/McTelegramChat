package org.amirov.mctelegramchat.properties;

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
    TELEPORT_BOW_GIVE_BOW("teleportbow-givebow"),
    LIGHTNING_CROSSBOW_GIVE_CROSSBOW("lightningcrossbow-givecrossbow"),
    SPAWN_LOCATION("spawn");

    private final String keyName;

    ConfigProperty(String value) { this.keyName = value; }

    public String getKeyName() { return this.keyName; }
}
