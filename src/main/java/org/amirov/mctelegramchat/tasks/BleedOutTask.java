package org.amirov.mctelegramchat.tasks;

import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Sets the health of an entity equal to the current health - 1 every 5 seconds.
 */
public final class BleedOutTask extends BukkitRunnable {

//<editor-fold default-state="collapsed" desc="Private Static Constants">
    private static final int HEALTH_LOWER_BOUND = 1;
    private static final int HEALTH_SUBTRAHEND = 1;
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Public Static Constants">
    public static final long TASK_DELAY = 0L;
    public static final long TASK_PERIOD = 100L;
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Instance Variables">
    final LivingEntity victim;
//</editor-fold>

//<editor-fold default-state="collapsed" desc="Constructor">
    public BleedOutTask(LivingEntity victim) { this.victim = victim; }
//</editor-fold>

    @Override
    public void run() {
        final double victimHealth = victim.getHealth();
        if (victimHealth > HEALTH_LOWER_BOUND) {
            victim.setHealth(victimHealth - HEALTH_SUBTRAHEND);
        } else if (victimHealth == HEALTH_LOWER_BOUND) {
            victim.setHealth(victimHealth - 1);
            this.cancel();
        }
    }
}
