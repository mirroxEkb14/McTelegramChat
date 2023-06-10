package org.amirov.mctelegramchat.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.TextComponent;
import org.amirov.mctelegramchat.logging.Loggers;
import org.amirov.mctelegramchat.logging.LoggingMessage;
import org.amirov.mctelegramchat.properties.Symbol;
import org.amirov.mctelegramchat.telegrambot.SPWBot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Monitors the game chat for players' messages.
 */
public final class ChatListener implements Listener {

    private final SPWBot telegramBot;

    public ChatListener(SPWBot telegramBot) { this.telegramBot = telegramBot; }

    /**
     * Called when a player sends a message to the game chat.
     * <p>
     * Sends player's message to the bot.
     *
     * @see <a href="https://core.telegram.org/bots/api#sendmessage">Sending messages</a>
     */
    @EventHandler
    public void handleChatMessage(@NotNull AsyncChatEvent event) {
        final String messageText = ((TextComponent) event.message()).content();
        final String formattedMessageText = getFormattedChatMessageText(messageText);
        final String playerName = event.getPlayer().getName();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramBot.getAdminId());
        sendMessage.setParseMode(ParseMode.HTML);
        sendMessage.setText(getFullChatMessage(formattedMessageText, playerName));
        try {
            telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            Loggers.printSevereLog(LoggingMessage.SENDING_MESSAGE_ERROR.getMessage(e.getMessage()));
        }
    }

    /**
     * Formats the text from Minecraft chat because there are some tags that are not a part of an
     * HTML entity, so that they should be replaced with the corresponding HTML entities.
     *
     * @param text The text that a player sent to Minecraft chat.
     *
     * @return The same text but with replaced HTML tags.
     *
     * @see <a href="https://core.telegram.org/bots/api#formatting-options">Formatting options</a>
     */
    private @NotNull String getFormattedChatMessageText(@NotNull String text) {
        return text
                .replace(Symbol.LEFT_ANGLE_BRACKET.getSymbol(), Symbol.LEFT_ANGLE_TAG_ENTITY.getSymbol())
                .replace(Symbol.RIGHT_ANGLE_BRACKET.getSymbol(), Symbol.RIGHT_ANGLE_TAG_ENTITY.getSymbol())
                .replace(Symbol.AMPERSAND.getSymbol(), Symbol.AMPERSAND_TAG_ENTITY.getSymbol());
    }

    /**
     * @return MONOSPACED_PLAYER_NAME: PLAYER_MESSAGE.
     */
    private @NotNull String getFullChatMessage(String messageText, String playerName) {
        return Symbol.MONOSPACE_OPENING_TAG.getSymbol() +
                playerName +
                Symbol.MONOSPACE_CLOSING_TAG.getSymbol() +
                Symbol.COLON_WITH_WHITE_SPACE.getSymbol() +
                messageText;
    }
}
