package org.amirov.mctelegramchat.logging;

import org.bukkit.Bukkit;

import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Allows calling static methods that print error messages to the console.
 */
public final class Loggers {

    private static final Logger logger = Bukkit.getLogger();

    private static final Consumer<String> SEVERE_LOG = t -> logger.log(Level.SEVERE, t);
    private static final Consumer<String> INFO_LOG = t -> logger.log(Level.INFO, t);
    private static final Consumer<String> WARNING_LOG = t -> logger.log(Level.WARNING, t);

    public static void printSevereLog(String errorMessage) { SEVERE_LOG.accept(errorMessage); }

    public static void printInfoLog(String errorMessage) { INFO_LOG.accept(errorMessage); }

    public static void printWarningLog(String errorMessage) { WARNING_LOG.accept(errorMessage); }
}
