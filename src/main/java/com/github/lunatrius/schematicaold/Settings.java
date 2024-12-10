package com.github.lunatrius.schematicaold;

import com.github.lunatrius.schematicaold.client.gui.GuiSchematicControl;
import com.github.lunatrius.schematicaold.client.gui.GuiSchematicLoad;
import com.github.lunatrius.schematicaold.client.gui.GuiSchematicSave;
import com.github.lunatrius.schematicaold.client.renderer.RendererSchematicChunk;
import net.minecraft.src.Minecraft;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderItem;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ILogAgent;
import net.minecraft.src.LogAgent;
import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.ChunkCache;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Settings {
	private static final Settings instance = new Settings();

	// loaded from config
	public boolean enableAlpha = false;
	public float alpha = 1.0f;
	public boolean highlight = true;
	public boolean highlightAir = true;
	public float blockDelta = 0.005f;
	public int placeDelay = 1;
	public boolean placeInstantly = false;
	public boolean placeAdjacent = true;
	public boolean drawQuads = true;
	public boolean drawLines = true;

	public KeyBinding[] keyBindings = new KeyBinding[] {
			new KeyBinding("schematica.key.load", Keyboard.KEY_DIVIDE),
			new KeyBinding("schematica.key.save", Keyboard.KEY_MULTIPLY),
			new KeyBinding("schematica.key.control", Keyboard.KEY_SUBTRACT)
	};

	public static final String sbcDisablePrinter = "§0§2§0§0§e§f";
	public static final String sbcDisableSave = "§0§2§1§0§e§f";
	public static final String sbcDisableLoad = "§0§2§1§1§e§f";

	public static final File schematicDirectory = new File(Minecraft.getMinecraft().mcDataDir, "/schematics/");
	public static final File textureDirectory = new File(Minecraft.getMinecraft().mcDataDir, "/resources/mod/schematica/");
	public static final ILogAgent logger = new LogAgent(Schematica.class.getSimpleName(), "", (new File(Minecraft.getMinecraft().mcDataDir, "output-schematica.log")).getAbsolutePath());
	public static final RenderItem renderItem = new RenderItem();
	public static final ItemStack defaultIcon = new ItemStack(2, 1, 0);

	private final Vector3f translationVector = new Vector3f();
	public Minecraft minecraft = Minecraft.getMinecraft();
	public ChunkCache mcWorldCache = null;
	public SchematicWorld schematic = null;
	public Vector3f playerPosition = new Vector3f();
	public RendererSchematicChunk[][][] rendererSchematicChunk = null;
	public final List<RendererSchematicChunk> sortedRendererSchematicChunk = new ArrayList<RendererSchematicChunk>();
	public RenderBlocks renderBlocks = null;
	public Vector3f pointA = new Vector3f();
	public Vector3f pointB = new Vector3f();
	public Vector3f pointMin = new Vector3f();
	public Vector3f pointMax = new Vector3f();
	public int rotationRender = 0;
	public int orientation = 0;
	public Vector3f offset = new Vector3f();
	public boolean isRenderingSchematic = false;
	public int renderingLayer = -1;
	public boolean isRenderingGuide = false;
	public int chatLines = 0;
	public boolean isPrinterEnabled = true;
	public boolean isSaveEnabled = true;
	public boolean isLoadEnabled = true;
	public boolean isPrinting = false;
	public int[] increments = {
			1, 5, 15, 50, 250
	};

	private Settings() {
	}

	public static Settings instance() {
		return instance;
	}

	public void reset() {
		this.chatLines = 0;
		this.isPrinterEnabled = true;
		this.isSaveEnabled = true;
		this.isLoadEnabled = true;
		this.isRenderingSchematic = false;
		this.isRenderingGuide = false;
		this.schematic = null;
		this.renderBlocks = null;
		this.rendererSchematicChunk = null;
		this.mcWorldCache = null;
	}

	public void keyboardEvent(KeyBinding keybinding) {
		if (this.minecraft.currentScreen == null) {
			for (int i = 0; i < this.keyBindings.length; i++) {
				if (keybinding == this.keyBindings[i]) {
					keyboardEvent(i);
					break;
				}
			}
		}
	}

	public void keyboardEvent(int key) {
		switch (key) {
		case 0:
			this.minecraft.displayGuiScreen(new GuiSchematicLoad(this.minecraft.currentScreen));
			break;

		case 1:
			this.minecraft.displayGuiScreen(new GuiSchematicSave(this.minecraft.currentScreen));
			break;

		case 2:
			this.minecraft.displayGuiScreen(new GuiSchematicControl(this.minecraft.currentScreen));
			break;
		}
	}

	public void createRendererSchematicChunk() {
		int width = (this.schematic.width() - 1) / RendererSchematicChunk.CHUNK_WIDTH + 1;
		int height = (this.schematic.height() - 1) / RendererSchematicChunk.CHUNK_HEIGHT + 1;
		int length = (this.schematic.length() - 1) / RendererSchematicChunk.CHUNK_LENGTH + 1;

		this.rendererSchematicChunk = new RendererSchematicChunk[width][height][length];

		while (this.sortedRendererSchematicChunk.size() > 0) {
			this.sortedRendererSchematicChunk.remove(0).delete();
		}

		int x, y, z;
		for (x = 0; x < width; x++) {
			for (y = 0; y < height; y++) {
				for (z = 0; z < length; z++) {
					this.sortedRendererSchematicChunk.add(this.rendererSchematicChunk[x][y][z] = new RendererSchematicChunk(this.schematic, x, y, z));
				}
			}
		}

	}

	public boolean loadSchematic(String filename) {
		try {
			InputStream stream = new FileInputStream(filename);
			NBTTagCompound tagCompound = CompressedStreamTools.readCompressed(stream);

			if (tagCompound != null) {
				this.schematic = new SchematicWorld();
				this.schematic.readFromNBT(tagCompound);

				logger.logInfo(String.format("Loaded %s [w:%d,h:%d,l:%d]", new Object[] {
						filename, this.schematic.width(), this.schematic.height(), this.schematic.length()
				}));

				this.renderBlocks = new RenderBlocks(this.schematic);

				createRendererSchematicChunk();

				this.isRenderingSchematic = true;
			}
		} catch (Exception e) {
			logger.logSevereException("Failed to load schematic!", e);
			reset();
			return false;
		}

		return true;
	}

	public boolean saveSchematic(File directory, String filename, Vector3f from, Vector3f to) {
		try {
			NBTTagCompound tagCompound = new NBTTagCompound("Schematic");

			int minX = (int) Math.min(from.x, to.x);
			int maxX = (int) Math.max(from.x, to.x);
			int minY = (int) Math.min(from.y, to.y);
			int maxY = (int) Math.max(from.y, to.y);
			int minZ = (int) Math.min(from.z, to.z);
			int maxZ = (int) Math.max(from.z, to.z);
			short width = (short) (Math.abs(maxX - minX) + 1);
			short height = (short) (Math.abs(maxY - minY) + 1);
			short length = (short) (Math.abs(maxZ - minZ) + 1);

			int[][][] blocks = new int[width][height][length];
			int[][][] metadata = new int[width][height][length];
			List<TileEntity> tileEntities = new ArrayList<TileEntity>();
			TileEntity tileEntity = null;
			NBTTagCompound tileEntityNBT = null;

			for (int x = minX; x <= maxX; x++) {
				for (int y = minY; y <= maxY; y++) {
					for (int z = minZ; z <= maxZ; z++) {
						blocks[x - minX][y - minY][z - minZ] = this.minecraft.theWorld.getBlockId(x, y, z);
						metadata[x - minX][y - minY][z - minZ] = this.minecraft.theWorld.getBlockMetadata(x, y, z);
						tileEntity = this.minecraft.theWorld.getBlockTileEntity(x, y, z);
						if (tileEntity != null) {
							tileEntityNBT = new NBTTagCompound();
							tileEntity.writeToNBT(tileEntityNBT);

							tileEntity = TileEntity.createAndLoadEntity(tileEntityNBT);
							tileEntity.xCoord -= minX;
							tileEntity.yCoord -= minY;
							tileEntity.zCoord -= minZ;
							tileEntities.add(tileEntity);
						}
					}
				}
			}

			String icon = Integer.toString(defaultIcon.copy().itemID);

			try {
				String[] parts = filename.split(";");
				if (parts.length == 2) {
					icon = parts[0];
					filename = parts[1];
				}
			} catch (Exception e) {
				logger.logSevereException("Failed to parse icon data!", e);
			}

			SchematicWorld schematicOut = new SchematicWorld(icon, blocks, metadata, tileEntities, width, height, length);
			schematicOut.writeToNBT(tagCompound);

			OutputStream stream = new FileOutputStream(new File(directory, filename));
			CompressedStreamTools.writeCompressed(tagCompound, stream);
		} catch (Exception e) {
			logger.logSevereException("Failed to save schematic!", e);
			return false;
		}
		return true;
	}

	public Vector3f getTranslationVector() {
		Vector3f.sub(this.playerPosition, this.offset, this.translationVector);
		return this.translationVector;
	}

	public float getTranslationX() {
		return this.playerPosition.x - this.offset.x;
	}

	public float getTranslationY() {
		return this.playerPosition.y - this.offset.y;
	}

	public float getTranslationZ() {
		return this.playerPosition.z - this.offset.z;
	}

	public void refreshSchematic() {
		for (RendererSchematicChunk renderer : this.sortedRendererSchematicChunk) {
			renderer.setDirty();
		}
	}

	public void updatePoints() {
		this.pointMin.x = Math.min(this.pointA.x, this.pointB.x);
		this.pointMin.y = Math.min(this.pointA.y, this.pointB.y);
		this.pointMin.z = Math.min(this.pointA.z, this.pointB.z);

		this.pointMax.x = Math.max(this.pointA.x, this.pointB.x);
		this.pointMax.y = Math.max(this.pointA.y, this.pointB.y);
		this.pointMax.z = Math.max(this.pointA.z, this.pointB.z);
	}

	public void moveHere(Vector3f point) {
		point.x = (int) Math.floor(this.playerPosition.x);
		point.y = (int) Math.floor(this.playerPosition.y - 1);
		point.z = (int) Math.floor(this.playerPosition.z);

		switch (this.rotationRender) {
		case 0:
			point.x -= 1;
			point.z += 1;
			break;
		case 1:
			point.x -= 1;
			point.z -= 1;
			break;
		case 2:
			point.x += 1;
			point.z -= 1;
			break;
		case 3:
			point.x += 1;
			point.z += 1;
			break;
		}
	}

	public void moveHere() {
		this.offset.x = (int) Math.floor(this.playerPosition.x);
		this.offset.y = (int) Math.floor(this.playerPosition.y) - 1;
		this.offset.z = (int) Math.floor(this.playerPosition.z);

		if (this.schematic != null) {
			switch (this.rotationRender) {
			case 0:
				this.offset.x -= this.schematic.width();
				this.offset.z += 1;
				break;
			case 1:
				this.offset.x -= this.schematic.width();
				this.offset.z -= this.schematic.length();
				break;
			case 2:
				this.offset.x += 1;
				this.offset.z -= this.schematic.length();
				break;
			case 3:
				this.offset.x += 1;
				this.offset.z += 1;
				break;
			}

			reloadChunkCache();
		}
	}

	public void toggleRendering() {
		this.isRenderingSchematic = !this.isRenderingSchematic && (this.schematic != null);
	}

	public void reloadChunkCache() {
		if (this.schematic != null) {
			this.mcWorldCache = new ChunkCache(this.minecraft.theWorld, (int) this.offset.x - 1, (int) this.offset.y - 1, (int) this.offset.z - 1, (int) this.offset.x + this.schematic.width() + 1, (int) this.offset.y + this.schematic.height() + 1, (int) this.offset.z + this.schematic.length() + 1, 0);
			refreshSchematic();
		}
	}

	public void flipWorld() {
		if (this.schematic != null) {
			this.schematic.flip();
			createRendererSchematicChunk();
		}
	}

	public void rotateWorld() {
		if (this.schematic != null) {
			this.schematic.rotate();
			createRendererSchematicChunk();
		}
	}
}
