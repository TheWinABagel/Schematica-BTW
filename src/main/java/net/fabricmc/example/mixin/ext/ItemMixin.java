package net.fabricmc.example.mixin.ext;

import btw.block.blocks.SidingAndCornerBlock;
import btw.item.BTWItems;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(method = "onItemUse", at = @At("HEAD"))
    private void addGearUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int facing, float clickX, float clickY, float clickZ, CallbackInfoReturnable<Boolean> cir) {
        if (((Item) (Object) this).itemID == BTWItems.gear.itemID && world.isRemote) {
            if (Block.blocksList[world.getBlockId(x,y,z)] instanceof SidingAndCornerBlock siding) {
                int metadata = world.getBlockMetadata(x, y, z);
                boolean xOf =  siding.isCornerFacingXOffset(metadata);
                boolean yOff = siding.isCornerFacingYOffset(metadata);
                boolean zOff = siding.isCornerFacingZOffset(metadata);
                player.addChatMessage("");
                player.addChatMessage("--------------------------");
                player.addChatMessage("X: "+xOf + ", Y: " + yOff + ", Z:" + zOff);
                player.addChatMessage("X: "+clickX + ", Y: " + clickY + ", Z:" + clickZ);
                player.addChatMessage("Current metadata:" + world.getBlockMetadata(x, y, z));
//                return placeBlock(world, player, x, y, z, direction, siding.isCornerFacingXOffset(metadata) ? 0f : 1f, siding.isCornerFacingYOffset(metadata) ? 0f : 1f, siding.isCornerFacingZOffset(metadata) ? 1f : 0f, extraClicks);
            }
//            if (player.isSneaking()) {
//                ClientProxy.pointA.x = x;
//                ClientProxy.pointA.y = y;
//                ClientProxy.pointA.z = z;
//                ClientProxy.updatePoints();
//            }
//            else {
//                ClientProxy.pointB.x = x;
//                ClientProxy.pointB.y = y;
//                ClientProxy.pointB.z = z;
//                ClientProxy.updatePoints();
//            }
        }
    }
}
