package org.amirov.mctelegramchat.telegrambot;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.properties.ConfigProperty;
import org.amirov.mctelegramchat.properties.Symbol;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

/**
 * Implements the necessary methods to interact with Telegram Servers and gets the bot's updates to handle them.
 */
public final class SPWBot extends TelegramLongPollingBot {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private final Plugin mcTelegramChat;
    private final long adminId;
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Constructor">
    public SPWBot(Plugin mcTelegramChat) {
        super(getBotApiKeyName(mcTelegramChat));

        final String adminId = ConfigProperty.TELEGRAM_BOT_ADMIN_ID.getKeyName();
        this.mcTelegramChat = mcTelegramChat;
        this.adminId = mcTelegramChat.getConfig().getLong(adminId);
    }
//</editor-fold>

    /**
     * Gets the bot-api-key value from the "config.yml" file that is used to pass it as an argument to the parent class.
     *
     * @param myCustomPlugin An instance of the main class of this plugin.
     * @return The api token of this bot.
     */
    private static String getBotApiKeyName(@NotNull Plugin myCustomPlugin) {
        final String keyName = ConfigProperty.TELEGRAM_BOT_API_KEY.getKeyName();
        return myCustomPlugin.getConfig().getString(keyName);
    }

    /**
     * Called when receiving an incoming update.
     *
     * @param update An update that comes from the bot's chat.
     *
     * @see <a href="https://core.telegram.org/bots/api#update">Getting updates</a>
     */
    @Override
    public void onUpdateReceived(@NotNull Update update) {
        final Message msg = update.getMessage();
        sendFromTelegramToServer(msg);
    }

    /**
     * Sends a message from a telegram chat to the Minecraft server.
     *
     * @param msg An incoming message from a telegram chat.
     */
    private void sendFromTelegramToServer(Message msg) {
        if (msg != null && isAdminChat(msg) && msg.hasText()) {
            final User msgSender = msg.getFrom();
            final TextComponent userName = getUserNameForServer(msgSender);
            final String userText = msg.getText();
            final Component fullMessage = userName.append(Component.text(" " + userText));
            Bukkit.getServer().broadcast(fullMessage);
        }
    }

    /**
     * Gets user's full name, wraps it in angle brackets and changes its color.
     *
     * @param msgSender An instance of the user who sent a message.
     * @return User's full name in angle brackets with the color changed.
     */
    private @NotNull TextComponent getUserNameForServer(User msgSender) {
        final String senderFullName = getUserTelegramFullName(msgSender);
        return Component.text(Symbol.LEFT_ANGLE_BRACKET.getSymbol())
                        .append(Component.text(senderFullName, NamedTextColor.AQUA))
                        .append(Component.text(Symbol.RIGHT_ANGLE_BRACKET.getSymbol()));
    }

    /**
     * Verifies if the passed message comes from the admin's chat.
     * <p>
     * Nicknames in the Minecraft chat are showed inside the angle brackets <...>.
     *
     * @param message An incoming message from a user.
     * @return {@code true} if it's the admin's chat, otherwise {@code false}.
     */
    private boolean isAdminChat(@NotNull Message message) {
        return message.getChat().getId().equals(adminId);
    }

    /**
     * Checks if the passed user has his last name, according to which returns either user's first name
     * or user's first name + his last name.
     * <p>
     * While first names are required in Telegram, last names are optional.
     *
     * @param user User who writes to the bot.
     * @return User's first name or user's first name + his last name if it presents.
     */
    private @NotNull String getUserTelegramFullName(@NotNull User user) {
        return user.getFirstName() + (user.getLastName() != null ? " " + user.getLastName() : "");
    }

    /**
     * Reads the "config.yml" file, from which gets the bot-api-username.
     */
    @Override
    public String getBotUsername() {
        final String configKeyName = ConfigProperty.TELEGRAM_BOT_USERNAME.getKeyName();
        return mcTelegramChat.getConfig().getString(configKeyName);
    }

//<editor-fold default-state="collapsed" desc="Getters">
    public long getAdminId() { return adminId; }
//</editor-fold>
}
