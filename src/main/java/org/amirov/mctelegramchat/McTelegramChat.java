package org.amirov.mctelegramchat;

import org.amirov.mctelegramchat.commands.*;
import org.amirov.mctelegramchat.commands.files.ConfigManager;
import org.amirov.mctelegramchat.commands.properties.CommandName;
import org.amirov.mctelegramchat.listeners.*;
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
 * Registers the bot, commands and event listeners.
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
        ConfigManager.setupConfigFiles();

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

    /**
     * Initializes all the commands.
     */
    private void initCommands() {
        final PluginCommand menuCommand = getCommand(CommandName.MENU_COMMAND.getName());
        Objects.requireNonNull(menuCommand).setExecutor(new MenuInventoryCommand());
        final PluginCommand killCommand = getCommand(CommandName.KILL_COMMAND.getName());
        Objects.requireNonNull(killCommand).setExecutor(new KillCommand(this));
        final PluginCommand setSpawnCommand = getCommand(CommandName.SET_SPAWN_COMMAND.getName());
        Objects.requireNonNull(setSpawnCommand).setExecutor(new SetSpawnCommand(this));
        final PluginCommand spawnCommand = getCommand(CommandName.SPAWN_COMMAND.getName());
        Objects.requireNonNull(spawnCommand).setExecutor(new SpawnCommand(this));
        final PluginCommand saveLocCommand = getCommand(CommandName.SAVE_LOCATION_COMMAND.getName());
        Objects.requireNonNull(saveLocCommand).setExecutor(new SaveLocCommand());
        final PluginCommand spawnSignCommand = getCommand(CommandName.SPAWN_SIGN_COMMAND.getName());
        Objects.requireNonNull(spawnSignCommand).setExecutor(new SpawnSignCommand());
        final PluginCommand banCommand = getCommand(CommandName.BAN_COMMAND.getName());
        Objects.requireNonNull(banCommand).setExecutor(new BanInventoryCommand());
    }

    /**
     * Initializes all the event listeners.
     */
    private void initListeners(SPWBot telegramBot) {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(telegramBot), this);
        Bukkit.getPluginManager().registerEvents(new ArrowListener(this), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new SignListener(), this);
    }

    /**
     * Called when the program is disabled.
     */
    @Override
    public void onDisable() {}
}
