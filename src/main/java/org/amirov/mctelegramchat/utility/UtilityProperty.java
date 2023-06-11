package org.amirov.mctelegramchat.utility;

/**
 * Contains the names and descriptions of the items.
 */
public enum UtilityProperty {
    INVENTORY_NAME("Witcher gear"),
    STEEL_SWORD_NAME("Teigr"),
    STEEL_SWORD_DESC_TIER("Tier - Witcher Item"),
    STEEL_SWORD_DESC_TYPE("Type - Steel sword"),
    STEEL_SWORD_DESC_WEIGHT("Weight - 1.61"),
    STEEL_SWORD_DESC_ID("Item ID - mq1058_cat_school_sword"),
    BOW_NAME("Milva's Bow"),
    BOW_DESCRIPTION("The very unique bow which was owned by Maria Barring (known as Milva). Made in the Far North, " +
            "it was put up for sale on the Cidarian Seaside Bazaar");

    private final String value;

    UtilityProperty(String value) { this.value = value; }

    public String getValue() { return value; }
}
