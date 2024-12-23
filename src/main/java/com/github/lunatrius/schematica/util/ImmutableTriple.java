package com.github.lunatrius.schematica.util;

/**
 * @param left   Left object
 * @param middle Middle object
 * @param right  Right object
 */
public record ImmutableTriple<L, M, R>(L left, M middle, R right) {

    @Override
    public String toString() {
        return "Triple[l:" + left + ", m:" + middle + ", r:" + right + "]";
    }
}
