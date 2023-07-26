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
    ON_COMMAND_RTP("Teleported to: "),
    ON_COMMAND_VANISH_REMOVE("Now visible to others"),
    ON_COMMAND_VANISH_ADD("Now invisible to others"),
    ON_COMMAND_STAFFHOME_SET("Temporary Home at: "),
    ON_COMMAND_STAFFHOME_RESET("Overriding Temporary Home at: "),
    ON_COMMAND_STAFFHOME_RETURN_NO_SAVED_LOCATION("No Temporary Home set"),
    ON_COMMAND_STAFFHOME_RETURNED_HOME("At Temporary Home now"),
    ON_COMMAND_STAFFHOME_RELOAD("Staffhome configuration reloaded"),
    ON_COMMAND_STAFFHOME_TO_PLAYER_HOME_NO_HOME("No Temporary Home for this player"),
    ON_COMMAND_STAFFHOME_TO_PLAYER_HOME("Teleported to Temporary Home of %s to: "),
    ON_COMMAND_STAFFHOME_LORE("&7==&a&lStaff&eHome&7==\n&6&o/staffhome set &7- &9Set a Temporary Home\n&6&o/staffhome return &7- &9Return to Temporary Home and remove it\n&6&o/staffhome <name> &7- &9Teleport to a Temporary Home\n&6&o/staffhome reload &7- &9Reload the configuration\n&7========================="),
    ON_ENTITY_HIT("Made your victim glow and bleed"),
    ON_COMMAND_LOCK_SUCCESS("Chest been Locked"),
    ON_COMMAND_LOCK_WRONG_DISTANCE("Lock at Something Nearby"),
    ON_COMMAND_LOCK_WRONG_BLOCK("Block Cannot Be Locked"),
    ON_COMMAND_LOCK_OWNER_ALREADY_LOCKED("You already own Chest"),
    ON_COMMAND_LOCK_THIEF_ALREADY_LOCKED("Chest is locked by "),
    TRIED_TO_OPEN_LOCKED_CHEST_OWNER("You own Chest"),
    TRIED_TO_OPEN_LOCKED_CHEST_THIEF("Chest locked by "),
    TRIED_TO_BREAK_LOCKED_CHEST_THIEF("Cannot be broken due to locking"),
    ON_COMMAND_LOCK_DELETE("Lock been deleted"),
    ON_LOCK_DELETED("You Deleted The Lock"),
    ON_LOCK_VIEW_PLAYERS_NO_PLAYERS("You Didn't Add Players"),
    ON_LOCK_ADD_PLAYER_YOU_ADDED_TO_LOCK(" Was Added To The Lock"),
    ON_LOCK_ADD_PLAYER_YOU_WERE_ADDED_TO_LOCK(" Granted You Access To The Lock"),
    ON_LOCK_ADD_PLAYER_YOU_DELETED_FROM_LOCK(" Was Deleted From The Lock"),
    ON_LOCK_ADD_PLAYER_YOU_WERE_DELETED_FROM_LOCK(" Deleted You From The Lock");

    private final String message;

    ChatMessage(String message) { this.message = message; }

    public String getMessage() { return message; }
}
