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
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Whenever a player hits an entity, then it starts slowing bleeding out. With the help of the task, for every 5
 * seconds it loses half of the heart.
 * <p>
 * Singleton class.
 */
public final class HemorrhageEnchantment extends Enchantment {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final String KEY_IDENTIFIER = "hemorrhage";

    private static final String ENCHANTMENT_NAME = "Hemorrhage";
    private static final int ENCHANTMENT_MAX_LEVEL = 2;
    private static final int ENCHANTMENT_START_LEVEL = 1;
    private static final EnchantmentTarget ITEM_STACK_TYPE = EnchantmentTarget.WEAPON;
    private static final TextComponent DISPLAY_NAME = Component.text("Hemorrhage", NamedTextColor.YELLOW);
    private static final EnchantmentRarity ENCHANTMENT_RARITY = EnchantmentRarity.RARE;
//</editor-fold>

    private static HemorrhageEnchantment instance;

    public static HemorrhageEnchantment getInstance() {
        if (instance == null) {
            return new HemorrhageEnchantment();
        }
        return instance;
    }

    /**
     * Determines if a player has a weapon with this enchantment or not.
     *
     * @param item ItemStack object that the player holds in his main hand.
     *
     * @return {@code true} if this player wears a weapon with the hemorrhage enchantment, {@code false} otherwise.
     */
    public static boolean entityHasBleedingEnchantment(@NotNull ItemStack item) {
        return item
                .getEnchantments()
                .containsKey(Enchantment.getByKey(getInstance().getKey()));
    }

//<editor-fold default-state="collapsed" desc="Constructor">
    public HemorrhageEnchantment() {
        super(new NamespacedKey(McTelegramChat.getPlugin(), KEY_IDENTIFIER));
    }
//</editor-fold>

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
