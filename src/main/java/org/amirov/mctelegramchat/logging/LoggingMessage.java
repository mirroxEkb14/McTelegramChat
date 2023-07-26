package org.amirov.mctelegramchat.logging;

/**
 * Contains error messages for logging.
 */
public enum LoggingMessage {
    REMOVING_OLD_WEBHOOK_ERROR("Error Removing Old Webhook"),
    REMOVING_OLD_WEBHOOK_ADVISE("Check Your Bot-token and Bot-username"),
    BOT_LAUNCH_FAILED("Bot Has Failed to Launch: %s"),
    BOT_REGISTRATION_SUCCESS("Bot Has Been Successfully Registered"),
    ENCHANTMENT_REGISTRATION_ERROR("Enchantment Registration Failed"),
    MONGODB_CONNECTION_STRING_EMPTY("MongoDB Connection String Needed to Be Specified [config.yml]"),
    SENDING_MESSAGE_ERROR("Error Sending a Message to Telegram: %s"),
    MENU_INVENTORY_CMD_WARNING("The 'menu' Command Was Run Through the Command Line"),
    MENU_INVENTORY_COMMAND_BLOCK_WARNING("The 'menu' Command Was Run Through the Command Block"),
    COMMAND_MENU_WRONG_ITEM_SELECTED("Item That Is Not In GUI Command"),
    CREATING_CONFIG_FILE_ERROR("Error While Creating a Config File"),
    SAVING_CONFIG_FILE_ERROR("Config Files Were Not Setup Before Saving"),
    BAN_CONFIRMATION_INVENTORY_WRONG_ITEM("This Item Should Not Be Moved"),
    LOCK_CONFORMATION_INVENTORY_WRONG_ITEM("This Item Should Not Be Moved"),
    LOCK_ID_NOT_FOUND("Lock id Not Found"),
    LOCK_ACCESS_MANAGER_INFO_BUTTON_CLICKED("Info Button inside the Access Manager Was Clicked"),
    LOCK_ACCESS_MANAGER_OTHER_BUTTON_CLICKED("This Button Should Not Be Clicked inside the Access Manager");

    private final String message;

    LoggingMessage(String message) { this.message = message; }

    /**
     * This getter returns a full error message with a reason of this error.
     *
     * @param errorLog A reason of this error using {@code e.getMessage()}.
     * @return A full error message with its reason.
     */
    public String getMessage(String errorLog) {
        return String.format(message, errorLog);
    }

    public String getMessage() { return message; }
}
