package org.amirov.mctelegramchat.utility;

/**
 * This enumeration holds methods and constants that are used for creating custom items in this package only.
 */
public enum CustomItemConstants {

    CUSTOM_ITEM_AMOUNT(1);

    private final int value;

    CustomItemConstants(int value) { this.value = value; }

    public int getValue() { return value; }
}
