package org.amirov.mctelegramchat.properties;

/**
 * Contains the messages that are sent to the game chat.
 */
public enum ChatMessage {
    ON_SUICIDE_ITEM("Malus Island is waiting..."),
    ON_FEED_ITEM("Bon appetit"),
    ON_GOD_ITEM_ON("God mode enabled"),
    ON_GOD_ITEM_OFF("God mode disabled"),
    ON_COMMAND_KILL_NO_NAME("Player's name was not provided"),
    ON_COMMAND_KILL_WRONG_COMMAND_ARGUMENTS("Unknown player's name"),
    ON_COMMAND_KILL_PLAYER_OFFLINE("This player is currently off-line"),
    ON_COMMAND_KILL_NOTIFICATION("Player %s was killed"),
    ON_COMMAND_KILL_EXPLANATION("You were killed by %s"),
    ON_COMMAND_KILL_COOLDOWN("Cooldown time %d"),
    ON_COMMAND_KILL_NO_PERMISSION("No permission"),
    ON_COMMAND_SET_SPAWN("Spawn location set"),
    ON_COMMAND_SPAWN_TELEPORTED("Teleported to spawn point"),
    ON_COMMAND_SPAWN_NOT_SET("No spawn point set"),
    ON_COMMAND_FLY_ON("Now you can fly"),
    ON_COMMAND_FLY_OOF("Now you cannot fly"),
    ON_ARROW_LANDING_TELEPORT("Teleported to arrow point"),
    ON_ARROW_LANDING_LIGHTNING("Lighting hit"),
    ON_COMMAND_LOCATION_SAVED("Location point saved"),
    ON_COMMAND_LOCATION_WRONG_COMMAND_ARGUMENTS("Only one word for point name"),
    COMMAND_SPAWN_SIGN_WRONG_COMMAND_ARGUMENTS_FIRST("Number of command arguments is wrong"),
    COMMAND_SPAWN_SIGN_WRONG_COMMAND_ARGUMENTS_SECOND("/sign {row number} {text}"),
    COMMAND_SPAWN_SIGN_INDEX_ERROR("Row number must be a number 1-4"),
    ON_COMMAND_RTP("Teleported to: ");

    private final String message;

    ChatMessage(String message) { this.message = message; }

    public String getMessage() { return message; }
}
