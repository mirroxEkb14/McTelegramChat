package org.amirov.mctelegramchat.handlers;

import net.kyori.adventure.text.Component;
import org.amirov.mctelegramchat.gui.ArmoryGUI;
import org.amirov.mctelegramchat.handlers.performers.HologramCreatingPerformer;
import org.amirov.mctelegramchat.handlers.performers.SpawnArmorStandPerformer;
import org.amirov.mctelegramchat.logging.Loggers;
import org.amirov.mctelegramchat.logging.LoggingMessage;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Performs the logic for the clicks on items inside the main menu inventory.
 */
public final class MainMenuHandler {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static boolean isCurrentPlayerFlies = false;

    private static final int MAX_FOOD_LEVEL = 20;
//</editor-fold>

    /**
     * Checks what item was selected and according to it calls the appropriate methods that perform logic for this item.
     *
     * @param event Event of a player clicking an inventory item.
     * @param currentItem Item that was clicked on.
     * @param player Player who performed the click.
     */
    public static void performMenuInventoryClick(@NotNull InventoryClickEvent event,
                                                 @NotNull ItemStack currentItem,
                                                 @NotNull Player player) {
        event.setCancelled(true);
        switch (currentItem.getType()) {
            case TNT -> performPlayerSuicide(player);
            case ARMOR_STAND -> SpawnArmorStandPerformer.performSpawnArmorStand(player);
            case BREAD -> feedPlayer(player);
            case ELYTRA -> makeHimFly(player);
            case SHIELD -> {
                ArmoryGUI.openArmoryInventory(player);
                return;
            }
            case TOTEM_OF_UNDYING -> makeHimGod(player);
            case AMETHYST_SHARD -> HologramCreatingPerformer.performHologramCreating(player);
            default -> Loggers.printInfoLog(LoggingMessage.COMMAND_MENU_WRONG_ITEM_SELECTED.getMessage());
        }
        player.closeInventory();
    }


    /**
     * Restores player's food level to its maximum and sends him a message.
     *
     * @param player Player who clicked on the item and will be fed.
     */
    public static void feedPlayer(@NotNull Player player) {
        player.setFoodLevel(MAX_FOOD_LEVEL);
        player.sendMessage(Component.text(ChatMessage.ON_FEED_ITEM.getMessage()));
    }

    /**
     * The player kills himself after he selected a tnt inside the main menu inventory.
     *
     * @param playerWhoClicked Player who performed the click and who will be killed (himself).
     */
    private static void performPlayerSuicide(@NotNull Player playerWhoClicked) {
        playerWhoClicked.setHealth(Double.MIN_VALUE);
        playerWhoClicked.sendMessage(Component.text(ChatMessage.ON_SUICIDE_ITEM.getMessage()));
    }

    /**
     * Allows/forbids a player to fly and resets the appropriate {@code boolean} value, sends a message.
     *
     * @param player Player triggered the event.
     */
    public static void makeHimFly(@NotNull Player player) {
        if (isCurrentPlayerFlies) {
            isCurrentPlayerFlies = false;
            player.setAllowFlight(false);
            player.sendMessage(Component.text(ChatMessage.ON_COMMAND_FLY_OOF.getMessage()));
            return;
        }
        isCurrentPlayerFlies = true;
        player.setAllowFlight(true);
        player.sendMessage(Component.text(ChatMessage.ON_COMMAND_FLY_ON.getMessage()));
    }

    /**
     * Makes a player invincible.
     *
     * @param player Player who triggered the event.
     */
    private static void makeHimGod(@NotNull Player player) {
        if (player.isInvulnerable()) {
            player.setInvulnerable(false);
            player.sendMessage(Component.text(ChatMessage.ON_GOD_ITEM_OFF.getMessage()));
        } else {
            player.setInvulnerable(true);
            player.sendMessage(Component.text(ChatMessage.ON_GOD_ITEM_ON.getMessage()));
        }
    }
}
