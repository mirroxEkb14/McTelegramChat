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
 */
public class BanGUICommand implements CommandExecutor {

//<editor-fold default-state="collapsed" desc="Private Constants">
    private static final int INVENTORY_SIZE = 45;
    private static final TextComponent INVENTORY_NAME = Component.text("Player list", NamedTextColor.BLUE);
//</editor-fold>

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player player) {
            final ArrayList<Player> playerList = new ArrayList<>(player.getServer().getOnlinePlayers());
            final Inventory banGui = Bukkit.createInventory(player, INVENTORY_SIZE, INVENTORY_NAME);

            for (Player currentPlayer : playerList) {
                final ItemStack playerHead = PlayerHeadUtils.getPlayerHead(currentPlayer);
                banGui.addItem(playerHead);
            }
            player.openInventory(banGui);
        }
        return true;
    }

    public static TextComponent getInventoryName() { return INVENTORY_NAME; }
}
