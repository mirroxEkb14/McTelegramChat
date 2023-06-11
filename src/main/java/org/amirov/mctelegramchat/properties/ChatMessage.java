package org.amirov.mctelegramchat.properties;

/**
 * Contains the messages that are sent to the game chat.
 */
public enum ChatMessage {
    ON_COMMAND_DIE("Malus Island is waiting..."),
    ON_COMMAND_GOD_ON("God mode enabled"),
    ON_COMMAND_GOD_OFF("God mode disabled"),
    ON_COMMAND_FEED("Bon appetit"),
    ON_COMMAND_KILL_NO_NAME("Player's name was not provided"),
    ON_COMMAND_KILL_WRONG_COMMAND_ARGUMENTS("Unknown player's name"),
    ON_COMMAND_KILL_PLAYER_OFFLINE("This player is currently off-line"),
    ON_COMMAND_KILL_NOTIFICATION("Player %s was killed"),
    ON_COMMAND_KILL_EXPLANATION("You were killed by %s"),
    ON_COMMAND_KILL_COOLDOWN("Cooldown time %d"),
    ON_COMMAND_SET_SPAWN("Spawn location set"),
    ON_COMMAND_SPAWN_TELEPORTED("Teleported to spawn point"),
    ON_COMMAND_SPAWN_NOT_SET("No spawn point set"),
    ON_COMMAND_FLY_ON("Now you can fly"),
    ON_COMMAND_FLY_OOF("Now you cannot fly"),
    ON_COMMAND_FLY_WRONG_COMMAND_ARGUMENTS("Unknown player's name"),
    ON_COMMAND_GIVE_BOW_NO_PERMISSION("No safe conduct present"),
    ON_COMMAND_GIVE_BOW_NOTIFICATION("Teleported bow is now yours"),
    ON_COMMAND_GIVE_BOW_EXPLANATION("Teleported bow given by %s"),
    ON_COMMAND_GIVE_BOW_WRONG_COMMAND_ARGUMENTS("Unknown player's name"),
    ON_ARROW_LANDING("Teleported to arrow point");

    private final String message;

    ChatMessage(String message) { this.message = message; }

    public String getMessage() { return message; }
}
