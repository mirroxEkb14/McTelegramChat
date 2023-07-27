package org.amirov.mctelegramchat.gui.enums;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * This enumeration holds some common values for creating a confirmation menu used several times in this project.
 */
public enum ConfirmationGUIConstants {
    INVENTORY_SIZE(9),
    BUTTON_AMOUNT(1),
    YES_ITEM_SLOT_INDEX(3),
    NO_ITEM_SLOT_INDEX(5);

    private final int value;

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final Material CLOSE_BUTTON_MATERIAL = Material.BARRIER;
    private static final TextComponent CLOSE_BUTTON_NAME = Component.text(
            "Close", NamedTextColor.DARK_RED);

    private static final String TEXT_COMPONENT_DELIMITER = ",";
    private static final int FIRST_PART_EXTRACTION_INDEX = 0;
    private static final String FIND_INDEX_BY = "\"";
    private static final int START_INDEX_ENLARGER = 1;
//</editor-fold>

    ConfirmationGUIConstants(int value) { this.value = value; }

    /**
     * @return Close Button with no lore.
     */
    public static @NotNull ItemStack getCloseButton() {
        final ItemStack closeButton = new ItemStack(
                CLOSE_BUTTON_MATERIAL,
                ConfirmationGUIConstants.BUTTON_AMOUNT.getValue());
        final ItemMeta itemMeta = closeButton.getItemMeta();
        itemMeta.displayName(CLOSE_BUTTON_NAME);
        closeButton.setItemMeta(itemMeta);
        return closeButton;
    }

    /**
     * Fills all the empty slots in this inventory for this conformation gui to look more filled in.
     *
     * @param gui Inventory which slots should be filled in.
     */
    public static void fillEmptySlots(@NotNull Inventory gui) {
        for (int i = 0; i < gui.getSize(); i++) {
            if (gui.getItem(i) == null)
                gui.setItem(i, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
        }
    }

    /**
     * Extracts the {@link Player} object from the item that represents the one's head with a player's name.
     *
     * @param playerHead {@link ItemStack} representing player's head with his name on it.
     *
     * @return {@link Player} object extracted from its head item.
     */
    public static @NotNull Player getPlayerFromHead(@NotNull ItemStack playerHead) {
        final Component playerHeadName = playerHead.getItemMeta().displayName();
        Objects.requireNonNull(playerHeadName);
        final String textComponentImpl = playerHeadName.toString();
        final Player player = Bukkit.getPlayer(getPlayerName(textComponentImpl));

        Objects.requireNonNull(player);
        return player;
    }


    /**
     * Extract the player name from the {@link TextComponent} string.
     * <p>
     * TextComponentImpl{content="zea1ot19", style=StyleImpl{obfuscated=not_set, bold=not_set,
     * strikethrough=not_set, underlined=not_set, italic=not_set, color=null,
     * clickEvent=null, hoverEvent=null, insertion=null, font=null}, children=[]}
     *
     * @param textComponentImpl String from which it is needed to extract.
     *
     * @return Player's name as a {@link String}.
     */
    @Contract(pure = true)
    private static @NotNull String getPlayerName(@NotNull String textComponentImpl) {
        final String firstPart = textComponentImpl
                .split(TEXT_COMPONENT_DELIMITER)[FIRST_PART_EXTRACTION_INDEX]; // TextComponentImpl{content="zea1ot19"
        final int startIndex = firstPart.indexOf(FIND_INDEX_BY) + START_INDEX_ENLARGER;
        final int endIndex = firstPart.lastIndexOf(FIND_INDEX_BY);

        return firstPart.substring(startIndex, endIndex);
    }

    /**
     * Determines either this item is a "player head" button or not.
     *
     * @param item Item itself.
     *
     * @return {@code true} if it is a "player head" button, {@code false} otherwise.
     */
    public static boolean isPlayerHead(@NotNull ItemStack item) {
        return item.getType().equals(Material.PLAYER_HEAD);
    }

    /**
     * Determines either this item is a "close" button or not.
     *
     * @param item Item itself.
     *
     * @return {@code true} if it is a "close" button, {@code false} otherwise.
     */
    public static boolean isCloseButton(@NotNull ItemStack item) {
        return item.getType().equals(Material.BARRIER);
    }

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    public int getValue() { return value; }
//</editor-fold>
}
