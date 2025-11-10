package net.fabricmc.example.mixin.ext;

import btw.item.BTWItems;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(method = "onItemUse", at = @At("HEAD"))
    private void addGearUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int facing, float clickX, float clickY, float clickZ, CallbackInfoReturnable<Boolean> cir) {
        if (((Item) (Object) this).itemID == BTWItems.gear.itemID && world.isRemote) {
/*            StringBuilder sb = new StringBuilder();
            sb.append("--------------------------\n");
            int linesAmount = 2;
            if (Block.blocksList[world.getBlockId(x,y,z)] instanceof SidingAndCornerBlock siding) {
                int metadata = world.getBlockMetadata(x, y, z);
                boolean xOf =  siding.isCornerFacingXOffset(metadata);
                boolean yOff = siding.isCornerFacingYOffset(metadata);
                boolean zOff = siding.isCornerFacingZOffset(metadata);
                sb.append("X: ").append(xOf).append(", Y: ").append(yOff).append(", Z:").append(zOff).append("\n");
                linesAmount++;
            }
            sb.append("CX: ").append(clickX).append(", CY: ").append(clickY).append(", CZ:").append(clickZ).append("\n");
            sb.append("Current metadata: ").append(world.getBlockMetadata(x, y, z)).append("\n");
            linesAmount += 2;
            ChatUtils.printToChatWhileRemovingLast(sb.toString(), "Current metadata:");*/

            if (player.isSneaking()) {
                ClientProxy.pointA.x = x;
                ClientProxy.pointA.y = y;
                ClientProxy.pointA.z = z;
                ClientProxy.updatePoints();
            }
            else {
                ClientProxy.pointB.x = x;
                ClientProxy.pointB.y = y;
                ClientProxy.pointB.z = z;
                ClientProxy.updatePoints();
            }
        }
    }
}
