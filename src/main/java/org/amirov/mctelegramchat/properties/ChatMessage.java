package org.amirov.mctelegramchat.properties;

/**
 * Contains the messages that are sent to the game chat.
 */
public enum ChatMessage {
    ON_COMMAND_DIE("You have opted to die"),
    ON_COMMAND_GOD_ON("God mode is enabled"),
    ON_COMMAND_GOD_OFF("God mode is disabled"),
    ON_COMMAND_FEED("Bon appetit"),
    ON_COMMAND_KILL_NO_NAME("You did not provide the player's name"),
    ON_COMMAND_KILL_WRONG_COMMAND_ARGUMENTS("Unknown player's name"),
    ON_COMMAND_KILL_PLAYER_OFFLINE("This player is currently off-line"),
    ON_COMMAND_KILL_NOTIFICATION("Player %s was killed"),
    ON_COMMAND_KILL_EXPLANATION("You were killed by %s");

    private final String message;

    ChatMessage(String message) { this.message = message; }

    public String getMessage() { return message; }
}
