package com.github.lunatrius.schematica.util;

import com.github.lunatrius.schematica.client.util.BlockList;
import com.github.lunatrius.schematica.handler.ConfigurationHandler;
import com.github.lunatrius.schematica.reference.Reference;

import java.util.Comparator;
import java.util.List;

public enum ItemStackSortType {
    NAME_ASC("name", "↑", BlockList.WrappedItemStack::compareNames),
    NAME_DESC("name", "↓", (wrappedItemStackA, wrappedItemStackB) -> {
        return wrappedItemStackB.compareNames(wrappedItemStackA);
    }),
    NAME_NONE("name", "", null),
    SIZE_ASC("amount", "↑", Comparator.comparingInt(wrappedItemStackA -> wrappedItemStackA.total)),
    SIZE_DESC("amount", "↓", (wrappedItemStackA, wrappedItemStackB) -> {
        return wrappedItemStackB.total - wrappedItemStackA.total;
    }),
    SIZE_NONE("amount", "", null),
    MISSING_ASC("missing", "↑", Comparator.comparingInt(wrappedA -> wrappedA.total - wrappedA.placed)),
    MISSING_DESC("missing", "↓", (wrappedA, wrappedB) -> {
        return (wrappedB.total - wrappedB.placed) - (wrappedA.total - wrappedA.placed);
    }),
    MISSING_NONE("missing", "", null),
    AVAILABLE_ASC("available", "↑", Comparator.comparingInt(wrappedA -> wrappedA.inventory)),
    AVAILABLE_DESC("available", "↓", (wrappedA, wrappedB) -> {
        return wrappedB.inventory - wrappedA.inventory;
    }),
    AVAILABLE_NONE("available", "", null),
    NONE("error", "", null);
    //AVAILABLE

    private final Comparator<BlockList.WrappedItemStack> comparator;

    public final String label;
    public final String glyph;

    private ItemStackSortType(final String label, final String glyph, final Comparator<BlockList.WrappedItemStack> comparator) {
        this.label = label;
        this.glyph = glyph;
        this.comparator = comparator;

    }

    public boolean sort(final List<BlockList.WrappedItemStack> blockList) {
        if (this.comparator == null) {
            return false;
        }
        try {
            blockList.sort(this.comparator);
            return true;
        } catch (final Exception e) {
            Reference.logger.error("Could not sort the block list!", e);
            return false;
        }
    }

    public ItemStackSortType next() {
        final ItemStackSortType[] values = values();
        return values[(ordinal() + 1) % values.length];
    }

    public ItemStackSortType cycle() {
        return switch (this) {
            case NAME_DESC -> NAME_ASC;
            case NAME_ASC -> NAME_NONE;
            case NAME_NONE -> NAME_DESC;
            case SIZE_DESC -> SIZE_ASC;
            case SIZE_ASC -> SIZE_NONE;
            case SIZE_NONE -> SIZE_DESC;
            case MISSING_DESC -> MISSING_ASC;
            case MISSING_ASC -> MISSING_NONE;
            case MISSING_NONE -> MISSING_DESC;
            case AVAILABLE_DESC -> AVAILABLE_ASC;
            case AVAILABLE_ASC -> AVAILABLE_NONE;
            case AVAILABLE_NONE -> AVAILABLE_DESC;
            case NONE -> NONE;
        };
    }

    public ItemStackSortType reset() {
        return switch (this) {
            case NAME_ASC, NAME_DESC, NAME_NONE -> NAME_NONE;
            case SIZE_ASC, SIZE_DESC, SIZE_NONE -> SIZE_NONE;
            case MISSING_ASC, MISSING_DESC, MISSING_NONE -> MISSING_NONE;
            case AVAILABLE_ASC, AVAILABLE_DESC, AVAILABLE_NONE -> AVAILABLE_NONE;
            case NONE -> NONE;
        };
    }

    public static String defaultGlyph() {
        return "↑";
    }

    public static ItemStackSortType fromString(final String name) {
        try {
            return valueOf(name);
        } catch (Exception e) {
            return SIZE_DESC;
        }
    }

    public static ItemStackSortType configValue() {
        return fromString(ConfigurationHandler.sortType);
    }
}
