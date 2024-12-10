package com.github.lunatrius.core.entity;

import com.github.lunatrius.core.util.vector.Vector3f;
import com.github.lunatrius.core.util.vector.Vector3i;
import net.minecraft.src.Entity;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class EntityHelper {
    public final static int WILDMARK = -1;

    public static int getItemCountInInventory(IInventory inventory, Item item) {
        return getItemCountInInventory(inventory, item, WILDMARK);
    }

    public static int getItemCountInInventory(IInventory inventory, Item item, int itemDamage) {
        final int inventorySize = inventory.getSizeInventory();
        int count = 0;

        for (int slot = 0; slot < inventorySize; slot++) {
            final ItemStack itemStack = inventory.getStackInSlot(slot);

            if (itemStack != null && itemStack.getItem() == item && (itemDamage == WILDMARK || itemDamage == itemStack.getItemDamage())) {
                count += itemStack.stackSize;
            }
        }

        return count;
    }

    public static Vector3f getVector3fFromEntity(Entity entity) {
        return new Vector3f((float) entity.posX, (float) entity.posY, (float) entity.posZ);
    }

    public static Vector3f getVector3fFromEntity(Entity entity, Vector3f vec) {
        return vec.set((float) entity.posX, (float) entity.posY, (float) entity.posZ);
    }

    public static Vector3i getVector3iFromEntity(Entity entity) {
        return new Vector3i((int) Math.floor(entity.posX), (int) Math.floor(entity.posY), (int) Math.floor(entity.posZ));
    }

    public static Vector3i getVector3iFromEntity(Entity entity, Vector3i vec) {
        return vec.set((int) Math.floor(entity.posX), (int) Math.floor(entity.posY), (int) Math.floor(entity.posZ));
    }
}