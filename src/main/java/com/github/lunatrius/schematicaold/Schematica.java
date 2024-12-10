package com.github.lunatrius.schematicaold;

import btw.BTWAddon;
import btw.BTWMod;
import com.github.lunatrius.schematicaold.client.renderer.RendererSchematicChunk;
import net.fabricmc.example.mixin.BTWModAccessor;
import net.fabricmc.example.mixin.RenderGlobalAccessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.src.*;

import java.io.*;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

public class Schematica extends BTWAddon {
	public static final String MOD_ID = "schematica";
	private static final FileFilterConfiguration FILE_FILTER_CONFIGURATION = new FileFilterConfiguration();
	private static final String DIR_ASSETS = "lunatrius/schematica/assets/";

	private final Settings settings = Settings.instance();
	private final Profiler profiler = this.settings.minecraft.mcProfiler;
	private final SchematicPrinter printer = new SchematicPrinter();
	private int ticks = -1;

	public static Schematica instance = new Schematica();

	private Field sortedWorldRenderers = null;
	private File configurationFolder = null;

	public static DebugItem debugItem;

	@Override
	public void preInitialize() {
		this.modID = MOD_ID;
		//todo config
//		File suggestedConfigurationFile = event.getSuggestedConfigurationFile();
//		Configuration config = new Configuration(suggestedConfigurationFile);
//		this.configurationFolder = suggestedConfigurationFile.getParentFile();
//
//		config.load();
//		this.settings.enableAlpha = Config.getBoolean(config, Configuration.CATEGORY_GENERAL, "alphaEnabled", this.settings.enableAlpha, "Enable transparent textures.");
//		this.settings.alpha = Config.getInt(config, Configuration.CATEGORY_GENERAL, "alpha", (int) (this.settings.alpha * 255), 0, 255, "Alpha value used when rendering the schematic.") / 255.0f;
//		this.settings.highlight = Config.getBoolean(config, Configuration.CATEGORY_GENERAL, "highlight", this.settings.highlight, "Highlight invalid placed blocks and to be placed blocks.");
//		this.settings.highlightAir = Config.getBoolean(config, Configuration.CATEGORY_GENERAL, "highlightAir", this.settings.highlightAir, "Highlight invalid placed blocks (where there should be no block).");
//		this.settings.blockDelta = (float) Config.getDouble(config, Configuration.CATEGORY_GENERAL, "blockDelta", this.settings.blockDelta, 0.0, 0.5, "Delta value used for highlighting (if you're having issue with overlapping textures try setting this value higher).");
//		this.settings.placeDelay = Config.getInt(config, Configuration.CATEGORY_GENERAL, "placeDelay", this.settings.placeDelay, 0, 20, "Delay in ticks between placement attempts.");
//		this.settings.placeInstantly = Config.getBoolean(config, Configuration.CATEGORY_GENERAL, "placeInstantly", this.settings.placeInstantly, "Place all blocks that can be placed in one tick.");
//		this.settings.placeAdjacent = Config.getBoolean(config, Configuration.CATEGORY_GENERAL, "placeAdjacent", this.settings.placeAdjacent, "Place blocks only if there is an adjacent block next to it.");
//		this.settings.drawQuads = Config.getBoolean(config, Configuration.CATEGORY_GENERAL, "drawQuads", this.settings.drawQuads, "Draw surface areas.");
//		this.settings.drawLines = Config.getBoolean(config, Configuration.CATEGORY_GENERAL, "drawLines", this.settings.drawLines, "Draw outlines.");
//		config.save();

		List<Integer> blockListIgnoreID = SchematicWorld.blockListIgnoreID;
		blockListIgnoreID.add(Block.pistonExtension.blockID);
		blockListIgnoreID.add(Block.pistonMoving.blockID);
		blockListIgnoreID.add(Block.portal.blockID);
		blockListIgnoreID.add(Block.endPortal.blockID);
		//BTW


		List<Integer> blockListIgnoreMetadata = SchematicWorld.blockListIgnoreMetadata;
		blockListIgnoreMetadata.add(Block.waterMoving.blockID);
		blockListIgnoreMetadata.add(Block.waterStill.blockID);
		blockListIgnoreMetadata.add(Block.lavaMoving.blockID);
		blockListIgnoreMetadata.add(Block.lavaStill.blockID);
		blockListIgnoreMetadata.add(Block.dispenser.blockID);
		blockListIgnoreMetadata.add(Block.bed.blockID);
		blockListIgnoreMetadata.add(Block.railPowered.blockID);
		blockListIgnoreMetadata.add(Block.railDetector.blockID);
		blockListIgnoreMetadata.add(Block.pistonStickyBase.blockID);
		blockListIgnoreMetadata.add(Block.pistonBase.blockID);
		blockListIgnoreMetadata.add(Block.stoneSingleSlab.blockID);
		blockListIgnoreMetadata.add(Block.torchWood.blockID);
		blockListIgnoreMetadata.add(Block.stairsWoodOak.blockID);
		blockListIgnoreMetadata.add(Block.chest.blockID);
		blockListIgnoreMetadata.add(Block.redstoneWire.blockID);
		blockListIgnoreMetadata.add(Block.crops.blockID);
		blockListIgnoreMetadata.add(Block.tilledField.blockID);
		blockListIgnoreMetadata.add(Block.furnaceIdle.blockID);
		blockListIgnoreMetadata.add(Block.furnaceBurning.blockID);
		blockListIgnoreMetadata.add(Block.signPost.blockID);
		blockListIgnoreMetadata.add(Block.doorWood.blockID);
		blockListIgnoreMetadata.add(Block.ladder.blockID);
		blockListIgnoreMetadata.add(Block.rail.blockID);
		blockListIgnoreMetadata.add(Block.stairsCobblestone.blockID);
		blockListIgnoreMetadata.add(Block.signWall.blockID);
		blockListIgnoreMetadata.add(Block.lever.blockID);
		blockListIgnoreMetadata.add(Block.pressurePlateStone.blockID);
		blockListIgnoreMetadata.add(Block.doorIron.blockID);
		blockListIgnoreMetadata.add(Block.pressurePlatePlanks.blockID);
		blockListIgnoreMetadata.add(Block.torchRedstoneIdle.blockID);
		blockListIgnoreMetadata.add(Block.torchRedstoneActive.blockID);
		blockListIgnoreMetadata.add(Block.stoneButton.blockID);
		blockListIgnoreMetadata.add(Block.pumpkin.blockID);
		blockListIgnoreMetadata.add(Block.portal.blockID);
		blockListIgnoreMetadata.add(Block.pumpkinLantern.blockID);
		blockListIgnoreMetadata.add(Block.cake.blockID);
		blockListIgnoreMetadata.add(Block.redstoneRepeaterIdle.blockID);
		blockListIgnoreMetadata.add(Block.redstoneRepeaterActive.blockID);
		blockListIgnoreMetadata.add(Block.trapdoor.blockID);
		blockListIgnoreMetadata.add(Block.vine.blockID);
		blockListIgnoreMetadata.add(Block.fenceGate.blockID);
		blockListIgnoreMetadata.add(Block.stairsBrick.blockID);
		blockListIgnoreMetadata.add(Block.stairsStoneBrick.blockID);
		blockListIgnoreMetadata.add(Block.waterlily.blockID);
		blockListIgnoreMetadata.add(Block.stairsNetherBrick.blockID);
		blockListIgnoreMetadata.add(Block.netherStalk.blockID);
		blockListIgnoreMetadata.add(Block.endPortalFrame.blockID);
		blockListIgnoreMetadata.add(Block.redstoneLampIdle.blockID);
		blockListIgnoreMetadata.add(Block.redstoneLampActive.blockID);
		blockListIgnoreMetadata.add(Block.woodSingleSlab.blockID);
		blockListIgnoreMetadata.add(Block.stairsSandStone.blockID);
		blockListIgnoreMetadata.add(Block.enderChest.blockID);
		blockListIgnoreMetadata.add(Block.tripWireSource.blockID);
		blockListIgnoreMetadata.add(Block.tripWire.blockID);
		blockListIgnoreMetadata.add(Block.stairsWoodSpruce.blockID);
		blockListIgnoreMetadata.add(Block.stairsWoodBirch.blockID);
		blockListIgnoreMetadata.add(Block.stairsWoodJungle.blockID);
		blockListIgnoreMetadata.add(Block.flowerPot.blockID);
		blockListIgnoreMetadata.add(Block.carrot.blockID);
		blockListIgnoreMetadata.add(Block.potato.blockID);
		blockListIgnoreMetadata.add(Block.woodenButton.blockID);
		blockListIgnoreMetadata.add(Block.anvil.blockID);
		//BTW

		Map<Integer, Integer> blockListMapping = SchematicWorld.blockListMapping;
		blockListMapping.put(Block.waterMoving.blockID, Item.bucketWater.itemID);
		blockListMapping.put(Block.waterStill.blockID, Item.bucketWater.itemID);
		blockListMapping.put(Block.lavaMoving.blockID, Item.bucketLava.itemID);
		blockListMapping.put(Block.lavaStill.blockID, Item.bucketLava.itemID);
		blockListMapping.put(Block.bed.blockID, Item.bed.itemID);
		blockListMapping.put(Block.redstoneWire.blockID, Item.redstone.itemID);
		blockListMapping.put(Block.crops.blockID, Item.seeds.itemID);
		blockListMapping.put(Block.furnaceBurning.blockID, Block.furnaceIdle.blockID);
		blockListMapping.put(Block.signPost.blockID, Item.sign.itemID);
		blockListMapping.put(Block.doorWood.blockID, Item.doorWood.itemID);
		blockListMapping.put(Block.doorIron.blockID, Item.doorIron.itemID);
		blockListMapping.put(Block.signWall.blockID, Item.sign.itemID);
		blockListMapping.put(Block.torchRedstoneIdle.blockID, Block.torchRedstoneActive.blockID);
		blockListMapping.put(Block.redstoneRepeaterIdle.blockID, Item.redstoneRepeater.itemID);
		blockListMapping.put(Block.redstoneRepeaterActive.blockID, Item.redstoneRepeater.itemID);
		blockListMapping.put(Block.pumpkinStem.blockID, Item.pumpkinSeeds.itemID);
		blockListMapping.put(Block.melonStem.blockID, Item.melonSeeds.itemID);
		blockListMapping.put(Block.netherStalk.blockID, Item.netherStalkSeeds.itemID);
		blockListMapping.put(Block.brewingStand.blockID, Item.brewingStand.itemID);
		blockListMapping.put(Block.cauldron.blockID, Item.cauldron.itemID);
		blockListMapping.put(Block.redstoneLampActive.blockID, Block.redstoneLampIdle.blockID);
		blockListMapping.put(Block.cocoaPlant.blockID, Item.dyePowder.itemID);
		blockListMapping.put(Block.tripWire.blockID, Item.silk.itemID);
		blockListMapping.put(Block.flowerPot.blockID, Item.flowerPot.itemID);
		blockListMapping.put(Block.carrot.blockID, Item.carrot.itemID);
		blockListMapping.put(Block.potato.blockID, Item.potato.itemID);
		blockListMapping.put(Block.skull.blockID, Item.skull.itemID);

		if (!Settings.schematicDirectory.exists()) {
			if (!Settings.schematicDirectory.mkdirs()) {
				Settings.logger.logInfo("Could not create schematic directory!");
			}
		}

		if (!Settings.textureDirectory.exists()) {
			if (!Settings.textureDirectory.mkdirs()) {
				Settings.logger.logInfo("Could not create texture directory!");
			}
		}
	}

	@Override
	public void initialize() {
		try {

//			KeyBindingRegistry.registerKeyBinding(new KeyBindingHandler(this.settings.keyBindings, new boolean[this.settings.keyBindings.length]));
//			TickRegistry.registerTickHandler(new Ticker(EnumSet.of(TickType.CLIENT)), EnvType.CLIENT);

//			this.sortedWorldRenderers = ReflectionHelper.findField(RenderGlobal.class, "n", "field_72768_k", "sortedWorldRenderers");
		} catch (Exception e) {
			Settings.logger.logSevereException("Could not initialize the mod!", e);
			throw new RuntimeException(e);
		}
		debugItem = new DebugItem(2121);

	}

	@Override
	public void postInitialize() {
//		saveNewConfigFile();
		getBtwBlocks();
		String[] files = new String[] {
				"aliasVanilla", "flipVanilla", "rotationVanilla"
		};
		String mappingDir = DIR_ASSETS + "mapping/";
		ClassLoader classLoader = getClass().getClassLoader();
//		FabricLoader.getInstance().getGameDir().toUri()

		for (String filename : files) {
			loadConfigurationFile(classLoader.getResource(mappingDir + filename + ".properties"), filename + ".properties");
		}
		File[] configurationFiles = FabricLoader.getInstance().getConfigDir().toFile().listFiles(FILE_FILTER_CONFIGURATION);
//		File[] configurationFiles = this.configurationFolder.listFiles(FILE_FILTER_CONFIGURATION);

		for (File configurationFile : configurationFiles) {
			try {
				loadConfigurationFile(configurationFile.toURI().toURL(), configurationFile.getName());
			} catch (MalformedURLException e) {
				Settings.logger.logSevereException("Could not load properties file.", e);
			}
		}
	}

	private void getBtwBlocks() {
		Map<String, String> props = ((BTWModAccessor) BTWMod.instance).getPropertyValues();

		for (Entry<String, String> entry : props.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (!key.startsWith("fc") || key.contains("Container") || key.contains("Item")) {
				continue;
			}
			BlockInfo.addMappingAlias(key, value);
		}
		BlockInfo.ALIAS.isEmpty();
	}

	private void loadConfigurationFile(URL configurationFile, String configurationFilename) {
		if (configurationFile == null) {
			Settings.logger.logInfo("Skipping " + configurationFilename + "...");
			return;
		}

		Properties properties = new Properties();
		InputStream inputStream = null;

		Settings.logger.logInfo("Reading " + configurationFilename + "...");

		try {
			inputStream = configurationFile.openStream();
			properties.load(inputStream);
		} catch (IOException e) {
			Settings.logger.logSevereException("Could not load properties file.", e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				Settings.logger.logSevereException("Could not close properties file.", e);
			}
		}

		String filename = configurationFilename.toLowerCase();
		Set<Entry<Object, Object>> entrySet = properties.entrySet();
		for (Entry<Object, Object> entry : entrySet) {
			if (!(entry.getKey() instanceof String) || !(entry.getValue() instanceof String)) {
				continue;
			}

			String key = (String) entry.getKey();
			String value = (String) entry.getValue();

			if (filename.startsWith("alias")) {
				if (!BlockInfo.addMappingAlias(key, value)) {
					Settings.logger.logWarning("Failed alias: " + key + " => " + value);
				}
			} else if (filename.startsWith("flip")) {
				if (!BlockInfo.addMappingFlip(key, value)) {
					Settings.logger.logWarning("Failed flip: " + key + " => " + value);
				}
			} else if (filename.startsWith("rotation")) {
				if (!BlockInfo.addMappingRotation(key, value)) {
					Settings.logger.logWarning("Failed rotation: " + key + " => " + value);
				}
			}
		}
	}

	public void keyboardEvent(KeyBinding keyBinding, boolean down) {
		if (down) {
			this.settings.keyboardEvent(keyBinding);
		}
	}

	public boolean onTick(boolean start) {
		if (start) {
			return true;
		}

		this.profiler.startSection("schematica");
		if (/*tick == TickType.CLIENT && */this.settings.minecraft.thePlayer != null && this.settings.isRenderingSchematic && this.settings.schematic != null) {
			this.profiler.startSection("printer");
			if (this.settings.isPrinterEnabled && this.settings.isPrinting && this.ticks-- < 0) {
				this.ticks = this.settings.placeDelay;

				this.printer.print();
			}

			this.profiler.endStartSection("checkDirty");
			checkDirty();

			this.profiler.endStartSection("canUpdate");
			RendererSchematicChunk.setCanUpdate(true);

			this.profiler.endSection();
		} else if (/*tick == TickType.CLIENT && */this.settings.minecraft.thePlayer == null) {
			this.settings.reset();
		}
		this.profiler.endSection();

		return true;
	}

	private void checkDirty() {
//		if (this.sortedWorldRenderers != null) {
			try {
//				WorldRenderer[] renderers = (WorldRenderer[]) this.sortedWorldRenderers.get(this.settings.minecraft.renderGlobal);
				WorldRenderer[] renderers = ((RenderGlobalAccessor)this.settings.minecraft.renderGlobal).getWorldRenderer();
				if (renderers != null) {
					int count = 0;
					for (WorldRenderer worldRenderer : renderers) {
						if (worldRenderer.needsUpdate && count++ < 125) {
							AxisAlignedBB worldRendererBoundingBox = worldRenderer.rendererBoundingBox.getOffsetBoundingBox(-this.settings.offset.x, -this.settings.offset.y, -this.settings.offset.z);
							for (RendererSchematicChunk renderer : this.settings.sortedRendererSchematicChunk) {
								if (!renderer.getDirty() && renderer.getBoundingBox().intersectsWith(worldRendererBoundingBox)) {
									renderer.setDirty();
								}
							}
						}
					}
				}
			} catch (Exception e) {
				Settings.logger.logSevereException("Dirty check failed!", e);
			}
//		}
	}


}
