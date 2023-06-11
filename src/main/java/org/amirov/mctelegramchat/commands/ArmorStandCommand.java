package org.amirov.mctelegramchat.commands;

import org.amirov.mctelegramchat.utility.ChestplateUtils;
import org.amirov.mctelegramchat.utility.NetheriteSword;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

/**
 * Creates an armor stand with the set armor on it.
 */
public final class ArmorStandCommand implements CommandExecutor {

//<editor-fold default-state="collapsed" desc="Private Constants">
    /**
     * The angles for creating an {@link EulerAngle} object for the head pose of this armor stand.
     *
     * @see <a href="https://haselkern.com/Minecraft-ArmorStand">Create poses for Minecraft armor stands</a>
     */
    private static final double HEAD_POSE_X_AXIS = 5D;
    private static final double HEAD_POSE_Y_AXIS = 0D;
    private static final double HEAD_POSE_Z_AXIS = 0D;
    /**
     * The angles for the right leg pose.
     */
    private static final double RIGHT_LEG_POSE_X_AXIS = 325D;
    private static final double RIGHT_LEG_POSE_Y_AXIS = 0D;
    private static final double RIGHT_LEG_POSE_Z_AXIS = 0D;
    /**
     * The angles for the left leg pose.
     */
    private static final double LEFT_LEG_POSE_X_AXIS = 38D;
    private static final double LEFT_LEG_POSE_Y_AXIS = 0D;
    private static final double LEFT_LEG_POSE_Z_AXIS = 0D;
//</editor-fold>

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {
        if (sender instanceof Player player) {
            final ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(
                    player.getLocation(),
                    EntityType.ARMOR_STAND);
            final ItemStack chestplate = ChestplateUtils.getChestplate();
            final ItemStack silverSword = NetheriteSword.getNetheriteSword();

            armorStand.getEquipment().setChestplate(chestplate);
            armorStand.getEquipment().setItemInMainHand(silverSword);
            armorStand.setArms(true);
            armorStand.setGlowing(true);
            setArmorStandPose(armorStand);
        }
        return true;
    }

    private void setArmorStandPose(@NotNull ArmorStand armorStand) {
        armorStand.setHeadPose(new EulerAngle(
                Math.toRadians(HEAD_POSE_X_AXIS),
                Math.toRadians(HEAD_POSE_Y_AXIS),
                Math.toRadians(HEAD_POSE_Z_AXIS)));
        armorStand.setRightLegPose(new EulerAngle(
                Math.toRadians(RIGHT_LEG_POSE_X_AXIS),
                Math.toRadians(RIGHT_LEG_POSE_Y_AXIS) ,
                Math.toRadians(RIGHT_LEG_POSE_Z_AXIS)));
        armorStand.setLeftLegPose(new EulerAngle(
                Math.toRadians(LEFT_LEG_POSE_X_AXIS),
                Math.toRadians(LEFT_LEG_POSE_Y_AXIS),
                Math.toRadians(LEFT_LEG_POSE_Z_AXIS)));
    }
}
