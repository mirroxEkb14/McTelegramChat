package org.amirov.mctelegramchat.logging;

/**
 * Contains error messages for logging.
 */
public enum LoggingMessage {
    REMOVING_OLD_WEBHOOK_ERROR("Error removing old webhook"),
    REMOVING_OLD_WEBHOOK_ADVISE("Check your bot-token and bot-username"),
    BOT_LAUNCH_FAILED("Bot has failed to launch: %s"),
    BOT_REGISTRATION_SUCCESS("Bot has been successfully registered"),
    SENDING_MESSAGE_ERROR("Error sending a message to Telegram: %s");

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
