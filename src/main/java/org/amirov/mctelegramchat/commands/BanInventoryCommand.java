package org.amirov.mctelegramchat.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.amirov.mctelegramchat.utility.PlayerHeadUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Opens an inventory with the heads of the players on the server currently online.
 * <p>
 * Singleton class.
 */
public final class BanInventoryCommand implements CommandExecutor {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int INVENTORY_SIZE = 45;
    private static final TextComponent INVENTORY_NAME = Component.text("Player list", NamedTextColor.BLUE);
//</editor-fold>

    private static BanInventoryCommand instance;

//<editor-fold default-state="collapsed" desc="Constructor">
    public static BanInventoryCommand getInstance() {
        if (instance == null)
            return new BanInventoryCommand();

        return instance;
    }
//</editor-fold>

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        onCommand(sender);
        return true;
    }

    /**
     * Performs the command of opening the ban list of players who are currently online.
     * <p>
     * The logic was taken out of the main method {@code onCommand()} to be able to call this command from other parts
     * of the program.
     *
     * @param sender Player who performed the click.
     */
    public void onCommand(@NotNull CommandSender sender) {
        if (sender instanceof Player player) {
            final ArrayList<Player> playerList = new ArrayList<>(player.getServer().getOnlinePlayers());
            final Inventory banInventory = Bukkit.createInventory(player, INVENTORY_SIZE, INVENTORY_NAME);

            for (Player currentPlayer : playerList) {
                final ItemStack playerHead = PlayerHeadUtils.getPlayerHead(currentPlayer);
                banInventory.addItem(playerHead);
            }
            player.openInventory(banInventory);
        }
    }

//<editor-fold default-state="collapsed" desc="Getters">
    public static TextComponent getBanInventoryName() { return INVENTORY_NAME; }
//</editor-fold>
}
