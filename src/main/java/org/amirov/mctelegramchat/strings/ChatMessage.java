package org.amirov.mctelegramchat.strings;

/**
 * Contains the messages that are sent to the game chat.
 */
public enum ChatMessage {
    ON_SUICIDE_ITEM("Malus Island is Waiting..."),
    ON_FEED_ITEM("Bon Appetit"),
    ON_GOD_ITEM_ON("God Mode Enabled"),
    ON_GOD_ITEM_OFF("God Mode Disabled"),
    ON_COMMAND_TP_TELEPORTED("Teleported to Spawn Point"),
    ON_COMMAND_TP_SPAWN_NOT_SET("No Spawn Point Set"),
    ON_COMMAND_FLY_ON("Now You Can Fly"),
    ON_COMMAND_FLY_OOF("Now You Cannot Fly");

    private final String message;

    ChatMessage(String message) { this.message = message; }

    public String getMessage() { return message; }
}
