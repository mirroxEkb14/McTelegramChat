package org.amirov.mctelegramchat.listeners.properties;

/**
 * Contains the text for reasons for players' bans.
 */
public enum BanReason {
    BECAUSE_I_AM_ADMIN_REASON("Because I am an admin");

    private final String reason;

    BanReason(String reason) { this.reason = reason; }

    public String getReason() { return reason; }
}
