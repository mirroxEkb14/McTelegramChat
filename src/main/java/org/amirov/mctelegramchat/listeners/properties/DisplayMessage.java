package org.amirov.mctelegramchat.listeners.properties;

/**
 * Contains the texts for the titles that are showed to a player on the whole screen.
 */
public enum DisplayMessage {
    ON_JOIN_WELCOME("WELCOME!"),
    ON_JOIN_ENJOY("have a nice start"),
    ON_JOIN_PLAYED_BEFORE_WELCOME("WELCOME BACK!"),
    ON_JOIN_PLAYED_BEFORE_ENJOY("continue exploring this world"),
    ON_QUIT_FAREWELL(" has left the server");

    private final String title;

    DisplayMessage(String title) { this.title = title; }

    public String getTitle() { return title; }
}
