package org.amirov.mctelegramchat.listeners.performers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.listeners.InventoryClickListener;
import org.amirov.mctelegramchat.utility.buttons.BanBarrierButton;
import org.amirov.mctelegramchat.utility.buttons.BanWoodenAxeButton;
import org.amirov.mctelegramchat.utility.PlayerHeadUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * The methods of this performer class process an inventory click caught by {@link InventoryClickListener}.
 */
public final class BanInventoryPerformer {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int INVENTORY_SIZE = 9;
    private static final int BAN_ITEM_INDEX = 0;
    private static final int PLAYER_HEAD_INDEX = 4;
    private static final int BACK_ITEM_INDEX = 8;
    private static final TextComponent INVENTORY_TITLE = Component.text(
            "Ban Confirm", NamedTextColor.RED);
//</editor-fold>

    /**
     * Creates a ban confirmation inventory and opens it.
     *
     * @param event Event of the item click in the inventory.
     * @param player Player who made the click.
     * @param currentItem Item the player clicked on.
     */
    public static void performBanInventoryClick(InventoryClickEvent event, Player player, @NotNull ItemStack currentItem) {
        if (currentItem.getType() == Material.PLAYER_HEAD) {
            final Player playerToBan = getPlayerToBan(event, player);
            final Inventory emptyConfirmBanMenu = Bukkit.createInventory(
                    player,
                    INVENTORY_SIZE,
                    INVENTORY_TITLE);
            final Inventory confirmBanMenu = getConfirmBanMenu(emptyConfirmBanMenu, playerToBan);
            player.openInventory(confirmBanMenu);
        }
    }

    /**
     * Sets the empty inventory and returns it.
     *
     * @param confirmBanMenu Empty ban confirmation inventory.
     * @param playerToBan Player who will be banned.
     *
     * @return Set ban confirmation menu.
     */
    private static @NotNull Inventory getConfirmBanMenu(@NotNull Inventory confirmBanMenu, Player playerToBan) {
        final ItemStack banItem = BanWoodenAxeButton.getBanWoodenAxe();
        confirmBanMenu.setItem(BAN_ITEM_INDEX, banItem);

        final ItemStack playerHead = PlayerHeadUtils.getPlayerHead(playerToBan);
        confirmBanMenu.setItem(PLAYER_HEAD_INDEX, playerHead);

        final ItemStack backItem = BanBarrierButton.getBanBarrier();
        confirmBanMenu.setItem(BACK_ITEM_INDEX, backItem);

        return confirmBanMenu;
    }

    /**
     * Works as a getter. Finds a player by his name on the head and returns him.
     *
     * @param event Event of the click on the inventory.
     * @param player Player who triggered the event.
     *
     * @return Player who will be banned.
     */
    private static Player getPlayerToBan(@NotNull InventoryClickEvent event, @NotNull Player player) {
        final ItemStack currentItem = event.getCurrentItem();
        Objects.requireNonNull(currentItem);
        final Component playerToBanName = currentItem.getItemMeta().displayName();
        Objects.requireNonNull(playerToBanName);
        return player.getServer().getPlayerExact(playerToBanName.toString());
    }
}
