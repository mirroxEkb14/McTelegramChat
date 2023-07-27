package org.amirov.mctelegramchat.commands.files;

/**
 * This exception is thrown when there was an attemp to save data to the config files but these files from the
 * {@link ConfigManager} class were not setup.
 */
public final class ConfigFilesNotSetupException extends Exception {

    public ConfigFilesNotSetupException(String message) {
        super(message);
    }
}
