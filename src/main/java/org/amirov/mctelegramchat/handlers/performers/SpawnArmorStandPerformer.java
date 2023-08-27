package org.amirov.mctelegramchat.handlers.performers;

import org.amirov.mctelegramchat.utility.SilverSwordUtils;
import org.amirov.mctelegramchat.utility.UrsineChestplateUtils;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

/**
 * This performer class processes the creation of the hologram.
 */
public final class SpawnArmorStandPerformer {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    /**
     * Contains the angles for creating an {@link EulerAngle} object for the head pose of this armor stand.
     *
     * @see <a href="https://haselkern.com/Minecraft-ArmorStand">Create poses for Minecraft armor stands</a>
     */
    private static final double HEAD_POSE_X_AXIS = 5D;
    private static final double HEAD_POSE_Y_AXIS = 0D;
    private static final double HEAD_POSE_Z_AXIS = 0D;
    private static final double RIGHT_LEG_POSE_X_AXIS = 325D;
    private static final double RIGHT_LEG_POSE_Y_AXIS = 0D;
    private static final double RIGHT_LEG_POSE_Z_AXIS = 0D;
    private static final double LEFT_LEG_POSE_X_AXIS = 38D;
    private static final double LEFT_LEG_POSE_Y_AXIS = 0D;
    private static final double LEFT_LEG_POSE_Z_AXIS = 0D;
//</editor-fold>

    /**
     * Spawns a custom armor stand on the block where the player is standing.
     *
     * @param player Player who clicked on the armor stand.
     *
     * @see #setArmorStandPose(ArmorStand)
     */
    public static void performSpawnArmorStand(@NotNull Player player) {
        final ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(
                player.getLocation(),
                EntityType.ARMOR_STAND);
        final ItemStack chestplate = UrsineChestplateUtils.getUrsineChestplate();
        final ItemStack silverSword = SilverSwordUtils.getNetheriteSword();

        armorStand.getEquipment().setChestplate(chestplate);
        armorStand.getEquipment().setItemInMainHand(silverSword);
        armorStand.setArms(true);
        armorStand.setGlowing(true);
        setArmorStandPose(armorStand);
    }

    /**
     * Sets the pose of an armor stand.
     * <p>
     * This method is a helper for {@code spawnCustomArmorStand()}.
     *
     * @param armorStand Armor stand object itself.
     */
    private static void setArmorStandPose(@NotNull ArmorStand armorStand) {
        armorStand.setHeadPose(new EulerAngle(
                Math.toRadians(HEAD_POSE_X_AXIS),
                Math.toRadians(HEAD_POSE_Y_AXIS),
                Math.toRadians(HEAD_POSE_Z_AXIS)));
        armorStand.setRightLegPose(new EulerAngle(
                Math.toRadians(RIGHT_LEG_POSE_X_AXIS),
                Math.toRadians(RIGHT_LEG_POSE_Y_AXIS),
                Math.toRadians(RIGHT_LEG_POSE_Z_AXIS)));
        armorStand.setLeftLegPose(new EulerAngle(
                Math.toRadians(LEFT_LEG_POSE_X_AXIS),
                Math.toRadians(LEFT_LEG_POSE_Y_AXIS),
                Math.toRadians(LEFT_LEG_POSE_Z_AXIS)));
    }
}
