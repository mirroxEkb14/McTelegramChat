package org.amirov.mctelegramchat.properties;

/**
 * Contains the messages that are sent to the game chat.
 */
public enum ChatMessage {
    ON_SUICIDE_ITEM("Malus Island is Waiting..."),
    ON_FEED_ITEM("Bon Appetit"),
    ON_GOD_ITEM_ON("God Mode Enabled"),
    ON_GOD_ITEM_OFF("God Mode Disabled"),
    ON_COMMAND_KILL_PLAYER_OFFLINE("This Player Is Currently off-line"),
    ON_COMMAND_KILL_NOTIFICATION("Player %s Was Killed"),
    ON_COMMAND_KILL_EXPLANATION("You Were Killed by %s"),
    ON_COMMAND_KILL_COOLDOWN("Cooldown Time %d"),
    ON_COMMAND_KILL_NO_PERMISSION("No Permission"),
    ON_COMMAND_SET_SPAWN("Spawn Location Set"),
    ON_COMMAND_SPAWN_TELEPORTED("Teleported to Spawn Point"),
    ON_COMMAND_SPAWN_NOT_SET("No Spawn Point Set"),
    ON_COMMAND_FLY_ON("Now You Can Fly"),
    ON_COMMAND_FLY_OOF("Now You Cannot Fly"),
    ON_ARROW_LANDING_TELEPORT("Teleported to Arrow Point"),
    ON_ARROW_LANDING_LIGHTNING("Lighting Hit"),
    ON_COMMAND_LOCATION_SAVED("Location Point Saved"),
    ON_COMMAND_LOCATION_WRONG_COMMAND_ARGUMENTS("Only One Word for Point Name"),
    COMMAND_SPAWN_SIGN_WRONG_COMMAND_ARGUMENTS_FIRST("Number of Command Arguments Is Wrong"),
    COMMAND_SPAWN_SIGN_WRONG_COMMAND_ARGUMENTS_SECOND("/sign {row number} {text}"),
    COMMAND_SPAWN_SIGN_INDEX_ERROR("Row Number Must Be a Number 1-4"),
    ON_COMMAND_RTP("Teleported to: "),
    ON_COMMAND_VANISH_REMOVE("Now Visible to Others"),
    ON_COMMAND_VANISH_ADD("Now Invisible to Others"),
    ON_COMMAND_STAFFHOME_SET("Temporary Home at: "),
    ON_COMMAND_STAFFHOME_RESET("Overriding Temporary Home at: "),
    ON_COMMAND_STAFFHOME_RETURN_NO_SAVED_LOCATION("No Temporary Home Set"),
    ON_COMMAND_STAFFHOME_RETURNED_HOME("At Temporary Home Now"),
    ON_COMMAND_STAFFHOME_RELOAD("Staffhome Configuration Reloaded"),
    ON_COMMAND_STAFFHOME_TO_PLAYER_HOME_NO_HOME("No Temporary Home for This Player"),
    ON_COMMAND_STAFFHOME_TO_PLAYER_HOME("Teleported to Temporary Home of %s to: "),
    ON_COMMAND_STAFFHOME_LORE("&7==&a&lStaff&eHome&7==\n&6&o/staffhome set &7- &9Set a Temporary Home\n&6&o/staffhome return &7- &9Return to Temporary Home and Remove It\n&6&o/staffhome <name> &7- &9Teleport to a Temporary Home\n&6&o/staffhome reload &7- &9Reload the Configuration\n&7========================="),
    ON_ENTITY_HIT("Made Your Victim Glow And bleed"),
    ON_COMMAND_LOCK_SUCCESS("Chest been Locked"),
    ON_COMMAND_LOCK_WRONG_DISTANCE("Lock at Something Nearby"),
    ON_COMMAND_LOCK_WRONG_BLOCK("Block Cannot Be Locked"),
    ON_COMMAND_LOCK_OWNER_ALREADY_LOCKED("You already Own Chest"),
    ON_COMMAND_LOCK_THIEF_ALREADY_LOCKED("Chest is locked by "),
    TRIED_TO_OPEN_LOCKED_CHEST_OWNER("You Own Chest"),
    TRIED_TO_OPEN_LOCKED_CHEST_THIEF("Chest Locked by "),
    TRIED_TO_BREAK_LOCKED_CHEST_THIEF("Cannot be Broken Due to Locking"),
    ON_COMMAND_LOCK_DELETE("Lock Been Deleted"),
    ON_LOCK_DELETED("You Deleted the Lock"),
    ON_LOCK_VIEW_PLAYERS_NO_PLAYERS("You Didn't Add Players"),
    ON_LOCK_ADD_PLAYER_YOU_ADDED_TO_LOCK(" Was Added to the Lock"),
    ON_LOCK_ADD_PLAYER_YOU_WERE_ADDED_TO_LOCK(" Granted You Access to the Lock"),
    ON_LOCK_ADD_PLAYER_YOU_DELETED_FROM_LOCK(" Was Deleted from the Lock"),
    ON_LOCK_ADD_PLAYER_YOU_WERE_DELETED_FROM_LOCK(" Deleted You from the Lock"),
    ON_COMMAND_EXPLODE_PERFORMER_MSG_SCRATCH("You Exploded %s"),
    ON_COMMAND_EXPLODE_TARGET_MSG_SCRATCH("You Were Exploded by %s"),
    PLAYER_NAME_WRONG_OR_OFFLINE("Target Name Wrong or Offline: ");

    private final String message;

    ChatMessage(String message) { this.message = message; }

    public String getMessage() { return message; }
}
