package com.github.lunatrius.schematica.client.printer.registry;

public class ExtendedPlacementData extends PlacementData {
    public final int[] validMetas;

    public static record Data(Class<?> type, int... metas) {
        public boolean matches(int meta) {
            for (int meta2 : metas) {
                if (meta2 == meta) {
                    return true;
                }
            }
            return false;
        }
    }
    public ExtendedPlacementData(PlacementType type, int[] meta1, int... validMetas) {
        super(type, meta1);
        this.validMetas = validMetas;
    }
}
