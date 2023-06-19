package org.amirov.mctelegramchat.listeners.performers;

import net.kyori.adventure.text.Component;
import org.amirov.mctelegramchat.properties.ChatMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Feeds the player who triggered the event.
 */
public class FeedBreadPerformer {

    private static final int MAX_FOOD_LEVEL = 20;

    /**
     * Restores player's food level to its maximum and sends him a message.
     *
     * @param player Player who clicked on the item and will be fed.
     */
    public static void feedPlayer(@NotNull Player player) {
        player.setFoodLevel(MAX_FOOD_LEVEL);
        player.sendMessage(Component.text(ChatMessage.ON_FEED_ITEM.getMessage()));
    }
}
