package org.amirov.mctelegramchat.strings;

/**
 * Contains symbols that are:
 * <ol>
 * <li>typical for Minecraft chat;
 * <li>not supported by HTML entity.
 * </ol>
 */
public enum Symbol {
    LEFT_ANGLE_BRACKET("<"),
    LEFT_ANGLE_TAG_ENTITY("&lt"),
    RIGHT_ANGLE_BRACKET(">"),
    RIGHT_ANGLE_TAG_ENTITY("&gt"),
    AMPERSAND("&"),
    AMPERSAND_TAG_ENTITY("&amp"),
    MONOSPACE_OPENING_TAG("<code>"),
    MONOSPACE_CLOSING_TAG("</code>"),
    COLON_WITH_WHITE_SPACE(": "), // punctuation mark
    SPACE(" "),
    QUOTATION_MARKS("");

    private final String symbol;

    Symbol(String symbol) { this.symbol = symbol; }

    public String getSymbol() { return symbol; }
}
