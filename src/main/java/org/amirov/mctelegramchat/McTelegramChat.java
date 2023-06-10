package org.amirov.mctelegramchat;

import org.amirov.mctelegramchat.commands.*;
import org.amirov.mctelegramchat.listeners.ChatListener;
import org.amirov.mctelegramchat.listeners.PlayerJoinListener;
import org.amirov.mctelegramchat.listeners.PlayerQuitListener;
import org.amirov.mctelegramchat.logging.Loggers;
import org.amirov.mctelegramchat.logging.LoggingMessage;
import org.amirov.mctelegramchat.telegrambot.SPWBot;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Objects;

/**
 * Registers the bot and event listeners.
 *
 * @author zea1ot 6/6/2023
 */
public final class McTelegramChat extends JavaPlugin {

    /**
     * Called when the program is enabled.
     */
    @Override
    public void onEnable() {
        saveDefaultConfig();

        final SPWBot telegramBot = new SPWBot(this);
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBot);
            initListeners(telegramBot);
            initCommands();

            Loggers.printInfoLog(LoggingMessage.BOT_REGISTRATION_SUCCESS.getMessage());
        } catch (TelegramApiException e) {
            final String potentialError = LoggingMessage.REMOVING_OLD_WEBHOOK_ERROR.getMessage();
            final String potentialAdvice = LoggingMessage.REMOVING_OLD_WEBHOOK_ADVISE.getMessage();
            final String errorReason = e.getMessage().equals(potentialError) ? potentialAdvice : e.getMessage();
            Loggers.printSevereLog(LoggingMessage.BOT_LAUNCH_FAILED.getMessage(errorReason));
        }
    }

    private void initCommands() {
        final PluginCommand dieCommand = getCommand(CommandName.DIE_COMMAND.getName());
        final PluginCommand godCommand = getCommand(CommandName.GOD_COMMAND.getName());
        final PluginCommand feedCommand = getCommand(CommandName.FEED_COMMAND.getName());
        final PluginCommand killCommand = getCommand(CommandName.KILL_COMMAND.getName());
        final PluginCommand setspawnCommand = getCommand(CommandName.SETSPAWN_COMMAND.getName());
        final PluginCommand spawnCommand = getCommand(CommandName.SPAWN_COMMAND.getName());

        Objects.requireNonNull(dieCommand);
        Objects.requireNonNull(godCommand);
        Objects.requireNonNull(feedCommand);
        Objects.requireNonNull(killCommand);
        Objects.requireNonNull(setspawnCommand);
        Objects.requireNonNull(spawnCommand);

        dieCommand.setExecutor(new DieCommand());
        godCommand.setExecutor(new GodCommand());
        feedCommand.setExecutor(new FeedCommand());
        killCommand.setExecutor(new KillCommand());
        setspawnCommand.setExecutor(new SetSpawnCommand(this));
        spawnCommand.setExecutor(new SpawnCommand(this));
    }

    /**
     * Initializes all the event listeners.
     */
    private void initListeners(SPWBot telegramBot) {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(telegramBot), this);
    }

    /**
     * Called when the program is disabled.
     */
    @Override
    public void onDisable() {}
}
