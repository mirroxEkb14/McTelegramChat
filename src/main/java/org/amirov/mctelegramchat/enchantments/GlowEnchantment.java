package org.amirov.mctelegramchat.enchantments;

import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.McTelegramChat;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * This enchantment makes glow an entity that was hit by a player wearing a piece of armor with this enchantment.
 *
 * Singleton class.
 */
public final class GlowEnchantment extends Enchantment {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final String KEY_IDENTIFIER = "glow";

    private static final String ENCHANTMENT_NAME = "Glow";
    private static final int ENCHANTMENT_MAX_LEVEL = 2;
    private static final int ENCHANTMENT_START_LEVEL = 1;
    private static final EnchantmentTarget ITEM_STACK_TYPE = EnchantmentTarget.ARMOR;
    private static final TextComponent DISPLAY_NAME = Component.text("Glow", NamedTextColor.YELLOW);
    private static final EnchantmentRarity ENCHANTMENT_RARITY = EnchantmentRarity.RARE;
//</editor-fold>

    private static GlowEnchantment instance;

    public static GlowEnchantment getInstance() {
        if (instance == null) {
            return new GlowEnchantment();
        }
        return instance;
    }

//<editor-fold default-state="collapsed" desc="Constructor">
    public GlowEnchantment() {
        super(new NamespacedKey(McTelegramChat.getPlugin(), KEY_IDENTIFIER));
    }
//</editor-fold>

    /**
     * Determines if a player has a chestplate with this enchantment or not.
     *
     * @param player Player who triggered the event.
     *
     * @return {@code true} if this player wears a chestplayer with the glow enchantment, {@code false} otherwise.
     */
    public static boolean entityHasGlowEnchantment(@NotNull Player player) {
        final ItemStack chestplate = player
                .getEquipment()
                .getChestplate();
        if (chestplate != null) {
            return player
                    .getEquipment()
                    .getChestplate()
                    .getEnchantments()
                    .containsKey(Enchantment.getByKey(getInstance().getKey()));
        }
        return false;
    }

    @Override
    public @NotNull String getName() { return ENCHANTMENT_NAME; }

    @Override
    public int getMaxLevel() { return ENCHANTMENT_MAX_LEVEL; }

    @Override
    public int getStartLevel() { return ENCHANTMENT_START_LEVEL; }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() { return ITEM_STACK_TYPE; }

    @Override
    public boolean isTreasure() { return false; }

    @Override
    public boolean isCursed() { return false; }

    @Override
    public boolean conflictsWith(@NotNull Enchantment other) { return false; }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) { return true; }

    @Override
    public @NotNull Component displayName(int level) { return DISPLAY_NAME; }

    @Override
    public boolean isTradeable() { return false; }

    @Override
    public boolean isDiscoverable() { return false; }

    @Override
    public @NotNull EnchantmentRarity getRarity() { return ENCHANTMENT_RARITY; }

    @Override
    public float getDamageIncrease(int level, @NotNull EntityCategory entityCategory) { return 0; }

    @Override
    public @NotNull Set<EquipmentSlot> getActiveSlots() { return null; }

    @Override
    public @NotNull String translationKey() { return null; }
}
