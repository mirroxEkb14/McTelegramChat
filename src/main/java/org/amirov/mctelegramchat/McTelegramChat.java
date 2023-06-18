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

    private void initCommands() {
        final PluginCommand guiCommand = getCommand(CommandName.GUI_COMMAND.getName());
        Objects.requireNonNull(guiCommand).setExecutor(new GUICommand());
        final PluginCommand godCommand = getCommand(CommandName.GOD_COMMAND.getName());
        Objects.requireNonNull(godCommand).setExecutor(new GodCommand());
        final PluginCommand feedCommand = getCommand(CommandName.FEED_COMMAND.getName());
        Objects.requireNonNull(feedCommand).setExecutor(new FeedCommand());
        final PluginCommand killCommand = getCommand(CommandName.KILL_COMMAND.getName());
        Objects.requireNonNull(killCommand).setExecutor(new KillCommand());
        final PluginCommand setSpawnCommand = getCommand(CommandName.SET_SPAWN_COMMAND.getName());
        Objects.requireNonNull(setSpawnCommand).setExecutor(new SetSpawnCommand(this));
        final PluginCommand spawnCommand = getCommand(CommandName.SPAWN_COMMAND.getName());
        Objects.requireNonNull(spawnCommand).setExecutor(new SpawnCommand(this));
        final PluginCommand menuCommand = getCommand(CommandName.MENU_COMMAND.getName());
        Objects.requireNonNull(menuCommand).setExecutor(new MenuCommand());
        final PluginCommand flyCommand = getCommand(CommandName.FLY_COMMAND.getName());
        Objects.requireNonNull(flyCommand).setExecutor(new FlyCommand());
        final PluginCommand giveBowCommand = getCommand(CommandName.GIVE_BOW_COMMAND.getName());
        Objects.requireNonNull(giveBowCommand).setExecutor(new GiveBowCommand(this));
        final PluginCommand armorStandCommand = getCommand(CommandName.ARMOR_STAND_COMMAND.getName());
        Objects.requireNonNull(armorStandCommand).setExecutor(new ArmorStandCommand());
        final PluginCommand hologramCommand = getCommand(CommandName.HOLOGRAM_COMMAND.getName());
        Objects.requireNonNull(hologramCommand).setExecutor(new HologramCommand());
        final PluginCommand saveLocCommand = getCommand(CommandName.SAVE_LOCATION_COMMAND.getName());
        Objects.requireNonNull(saveLocCommand).setExecutor(new SaveLocCommand());
        final PluginCommand spawnSignCommand = getCommand(CommandName.SPAWN_SIGN_COMMAND.getName());
        Objects.requireNonNull(spawnSignCommand).setExecutor(new SpawnSignCommand());
        final PluginCommand banGUICommand = getCommand(CommandName.BAN_GUI_COMMAND.getName());
        Objects.requireNonNull(banGUICommand).setExecutor(new BanGUICommand());
    }

    /**
     * Initializes all the event listeners.
     */
    private void initListeners(SPWBot telegramBot) {
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(telegramBot), this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new ArrowListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GUIListener(), this);
        Bukkit.getPluginManager().registerEvents(new SignListener(), this);
    }

    /**
     * Called when the program is disabled.
     */
    @Override
    public void onDisable() {}
}
