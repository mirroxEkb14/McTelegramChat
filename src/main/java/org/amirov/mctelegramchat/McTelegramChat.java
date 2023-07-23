package org.amirov.mctelegramchat;

import org.amirov.mctelegramchat.commands.*;
import org.amirov.mctelegramchat.commands.files.ConfigManager;
import org.amirov.mctelegramchat.commands.properties.CommandName;
import org.amirov.mctelegramchat.enchantments.GlowEnchantment;
import org.amirov.mctelegramchat.enchantments.HemorrhageEnchantment;
import org.amirov.mctelegramchat.listeners.*;
import org.amirov.mctelegramchat.logging.Loggers;
import org.amirov.mctelegramchat.logging.LoggingMessage;
import org.amirov.mctelegramchat.tasks.DayKeeperTask;
import org.amirov.mctelegramchat.telegrambot.SPWBot;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.PluginCommand;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Registers the bot, commands and event listeners.
 *
 * @author zea1ot 6/6/2023
 */
public final class McTelegramChat extends JavaPlugin {

//<editor-fold default-state="collapsed" desc="Private Constants">
    /**
     * This list contains players who triggered the {@code /vanish} command.
     */
    private final ArrayList<Player> INVISIBLE_LIST = new ArrayList<>();

    private static McTelegramChat plugin;
//</editor-fold>

    /**
     * Called when the program is enabled.
     */
    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();
        ConfigManager.setupConfigFiles();

        final SPWBot telegramBot = new SPWBot(this);
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBot);
            initListeners(telegramBot);
            initCommands();
            initTasks();
            initEnchantments();

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
        final PluginCommand randomTPCommand = getCommand(CommandName.RANDOM_TP_COMMAND.getName());
        Objects.requireNonNull(randomTPCommand).setExecutor(new RandomTPCommand(this));
        final PluginCommand vanishCommand = getCommand(CommandName.VANISH_COMMAND.getName());
        Objects.requireNonNull(vanishCommand).setExecutor(new VanishCommand(this));
        final PluginCommand staffHomeCommand = getCommand(CommandName.STAFF_HOME_COMMAND.getName());
        Objects.requireNonNull(staffHomeCommand).setExecutor(new StaffHomeCommand(this));
        final PluginCommand scoreBoardCommand = getCommand(CommandName.SCORE_BOARD_COMMAND.getName());
        Objects.requireNonNull(scoreBoardCommand).setExecutor(new ScoreBoardCommand());
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
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnerBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new GoodWeatherListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityHitListener(this), this);
    }

    /**
     * Initializes all the tasks.
     */
    private void initTasks() {
        long KEEP_DAY_TASK_DELAY = 0L;
        long KEEP_DAY_TASK_PERIOD = 100L; // 100 ticks = 5 seconds
        new DayKeeperTask(this).runTaskTimer(this, KEEP_DAY_TASK_DELAY, KEEP_DAY_TASK_PERIOD);
    }

    /**
     * Initializes all the custom enchantments.
     */
    private void initEnchantments() {
        registerEnchantment(GlowEnchantment.getInstance());
        registerEnchantment(HemorrhageEnchantment.getInstance());
    }

    /**
     * Registers the custom enchantment.
     *
     * @see <a href="https://www.spigotmc.org/threads/custom-enchantments-1-13.346538">Custom Enchantments 1.13+</a>
     */
    private static void registerEnchantment(Enchantment enchantment) {
        boolean registered = true;
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            registered = false;
            e.printStackTrace();
        }
        if(registered){
            // It's been registered!
        }
    }

    /**
     * Unregisters the custom enchantment.
     *
     * @see <a href="https://www.spigotmc.org/threads/custom-enchantments-1-13.346538">Custom Enchantments 1.13+</a>
     */
    private static void unregisterEnchantment(Enchantment enchant) {
        try {
            Field keyField = Enchantment.class.getDeclaredField("byKey");

            keyField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);

            if(byKey.containsKey(enchant.getKey())) {
                byKey.remove(enchant.getKey());
            }
            Field nameField = Enchantment.class.getDeclaredField("byName");

            nameField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);

            if(byName.containsKey(enchant.getName())) {
                byName.remove(enchant.getName());
            }
        } catch (Exception ignored) { }
    }

    /**
     * Called when the program is disabled.
     */
    @Override
    public void onDisable() {
        unregisterEnchantment(GlowEnchantment.getInstance());
        unregisterEnchantment(HemorrhageEnchantment.getInstance());
    }

//<editor-fold default-state="collapsed" desc="Getters">
    public ArrayList<Player> getInvisibleList() { return INVISIBLE_LIST; }

    public static McTelegramChat getPlugin() { return plugin; }
//</editor-fold>
}
