package com.github.lunatrius.core.client.gui;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.RenderItem;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class GuiScreenBase extends GuiScreen {
    protected final GuiScreen parentScreen;

    protected List<GuiButton> buttonList = super.buttonList; // I feel dirty
    protected List<GuiTextField> textFields = new ArrayList<GuiTextField>();
    protected static RenderItem itemRenderer = new RenderItem();
    public GuiScreenBase() {
        this(null);
    }

    public GuiScreenBase(final GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.textFields.clear();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseEvent) {
        for (GuiButton button : this.buttonList) {
            if (button instanceof GuiNumericField numericField) {
                numericField.mouseClicked(mouseX, mouseY, mouseEvent);
            }
        }

        for (GuiTextField textField : this.textFields) {
            textField.mouseClicked(mouseX, mouseY, mouseEvent);
        }

        super.mouseClicked(mouseX, mouseY, mouseEvent);
    }

    @Override
    protected void keyTyped(char character, int code) {
        if (code == Keyboard.KEY_ESCAPE) {
            this.mc.displayGuiScreen(this.parentScreen);
            return;
        }

        for (GuiButton button : this.buttonList) {
            if (button instanceof GuiNumericField numericField) {
                numericField.keyTyped(character, code);

                if (numericField.isFocused()) {
                    actionPerformed(numericField);
                }
            }
        }

        for (GuiTextField textField : this.textFields) {
            textField.textboxKeyTyped(character, code);
        }

        super.keyTyped(character, code);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        for (GuiButton button : this.buttonList) {
            if (button instanceof GuiNumericField numericField) {
                numericField.updateCursorCounter();
            }
        }

        for (GuiTextField textField : this.textFields) {
            textField.updateCursorCounter();
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        for (GuiTextField textField : this.textFields) {
            textField.drawTextBox();
        }
    }
}