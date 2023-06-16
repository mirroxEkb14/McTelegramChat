package org.amirov.mctelegramchat.commands.properties;

/**
 * Contains the names of holograms (invisible armor stands).
 */
public enum HologramLine {
    HOLOGRAM_LINE_FIRST("1. Line One"),
    HOLOGRAM_LINE_SECOND("2. Line Second");

    private final String line;

    HologramLine(String line) { this.line = line; }

    public String getLine() { return line; }
}
