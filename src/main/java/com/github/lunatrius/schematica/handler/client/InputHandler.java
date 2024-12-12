package com.github.lunatrius.schematica.handler.client;

import com.github.lunatrius.schematica.client.gui.control.GuiSchematicControl;
import com.github.lunatrius.schematica.client.gui.load.GuiSchematicLoad;
import com.github.lunatrius.schematica.client.gui.save.GuiSchematicSave;
import com.github.lunatrius.schematica.client.printer.SchematicPrinter;
import com.github.lunatrius.schematica.client.renderer.RendererSchematicGlobal;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import com.github.lunatrius.schematica.reference.Names;
import com.github.lunatrius.schematica.reference.Reference;
import net.minecraft.src.*;
import org.lwjgl.input.Keyboard;

public class InputHandler {
    public static final InputHandler INSTANCE = new InputHandler();

    private static final KeyBinding KEY_BINDING_LOAD = new KeyBinding(Names.Keys.LOAD, Keyboard.KEY_DIVIDE/*, Names.Keys.CATEGORY*/);
    private static final KeyBinding KEY_BINDING_SAVE = new KeyBinding(Names.Keys.SAVE, Keyboard.KEY_MULTIPLY/*, Names.Keys.CATEGORY*/);
    private static final KeyBinding KEY_BINDING_CONTROL = new KeyBinding(Names.Keys.CONTROL, Keyboard.KEY_SUBTRACT/*, Names.Keys.CATEGORY*/);
    private static final KeyBinding KEY_BINDING_LAYER_INC = new KeyBinding(Names.Keys.LAYER_INC, Keyboard.KEY_NONE/*, Names.Keys.CATEGORY*/);
    private static final KeyBinding KEY_BINDING_LAYER_DEC = new KeyBinding(Names.Keys.LAYER_DEC, Keyboard.KEY_NONE/*, Names.Keys.CATEGORY*/);

    public static int keyBindStartIndex;
    public static final KeyBinding[] KEY_BINDINGS = new KeyBinding[] {
            KEY_BINDING_LOAD, KEY_BINDING_SAVE, KEY_BINDING_CONTROL, KEY_BINDING_LAYER_INC, KEY_BINDING_LAYER_DEC
    };

    private final Minecraft minecraft = Minecraft.getMinecraft();

    private InputHandler() {}
    
    public void onKeyInput() {
        if (this.minecraft.currentScreen == null) {
            if (KEY_BINDING_LOAD.isPressed()) {
                this.minecraft.displayGuiScreen(new GuiSchematicLoad(this.minecraft.currentScreen));
            }

            if (KEY_BINDING_SAVE.isPressed()) {
                this.minecraft.displayGuiScreen(new GuiSchematicSave(this.minecraft.currentScreen));
            }

            if (KEY_BINDING_CONTROL.isPressed()) {
                this.minecraft.displayGuiScreen(new GuiSchematicControl(this.minecraft.currentScreen));
            }

            if (KEY_BINDING_LAYER_INC.isPressed()) {
                final SchematicWorld schematic = ClientProxy.schematic;
                if (schematic != null && schematic.isRenderingLayer) {
                    schematic.renderingLayer = MathHelper.clamp_int(schematic.renderingLayer + 1, 0, schematic.getHeight() - 1);
                    RendererSchematicGlobal.INSTANCE.refresh();
                }
            }

            if (KEY_BINDING_LAYER_DEC.isPressed()) {
                final SchematicWorld schematic = ClientProxy.schematic;
                if (schematic != null && schematic.isRenderingLayer) {
                    schematic.renderingLayer = MathHelper.clamp_int(schematic.renderingLayer - 1, 0, schematic.getHeight() - 1);
                    RendererSchematicGlobal.INSTANCE.refresh();
                }
            }
        }
    }

    public boolean handlePickBlock() {
            try {
                final SchematicWorld schematic = ClientProxy.schematic;
                boolean pass = true;
                if (schematic != null && schematic.isRendering) {
                    pass = pickBlock(schematic, ClientProxy.movingObjectPosition);
                }

                return pass;
            } catch (Exception e) {
                Reference.logger.error("Could not pick block!", e);
                return true;
            }
    }

    /**
     * @return if the vanilla pick block should happen
     * */
    private boolean pickBlock(final SchematicWorld schematic, final MovingObjectPosition objectMouseOver) {
        if (objectMouseOver != null) {
            final EntityClientPlayerMP player = this.minecraft.thePlayer;

            //revert if it should be picking a vanilla block
            final MovingObjectPosition mcObjectMouseOver = this.minecraft.objectMouseOver;
            if (mcObjectMouseOver != null && mcObjectMouseOver.typeOfHit == EnumMovingObjectType.TILE) {
                final int x = mcObjectMouseOver.blockX - schematic.position.x;
                final int y = mcObjectMouseOver.blockY - schematic.position.y;
                final int z = mcObjectMouseOver.blockZ - schematic.position.z;
                if (x == objectMouseOver.blockX && y == objectMouseOver.blockY && z == objectMouseOver.blockZ) {
                    return true;
                }
            }

            boolean creative = player.capabilities.isCreativeMode;
            int x = objectMouseOver.blockX, y = objectMouseOver.blockY, z = objectMouseOver.blockZ;
            final Block block = schematic.getBlock(x, y, z);

            int idPicked = block.idPicked(schematic, x, y, z);
            if (idPicked == 0) {
                return true;
            }
            int meta = block.getDamageValue(schematic, x, y, z);
            Item item = Item.itemsList[idPicked];
            player.inventory.setCurrentItem(idPicked, meta, item != null && item.getHasSubtypes(), creative);

            if (creative) {
                final int slot = player.inventoryContainer.inventorySlots.size() - 9 + player.inventory.currentItem;
                this.minecraft.playerController.sendSlotPacket(player.inventory.getStackInSlot(player.inventory.currentItem), slot);
            }
            return false;
        }

        return true;
    }
}
