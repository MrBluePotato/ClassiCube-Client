package com.mojang.minecraft.gui;

import java.awt.Color;

import com.mojang.minecraft.ColorCache;
import com.mojang.minecraft.GameSettings;
import com.mojang.minecraft.gui.inputscreens.CloudColorInputScreen;
import com.mojang.minecraft.gui.inputscreens.FogColorInputScreen;
import com.mojang.minecraft.gui.inputscreens.LightColorInputScreen;
import com.mojang.minecraft.gui.inputscreens.ShadowColorInputScreen;
import com.mojang.minecraft.gui.inputscreens.SkyColorInputScreen;
import com.mojang.minecraft.gui.inputscreens.WaterLevelInputScreen;

public final class AdvancedOptionsScreen extends GuiScreen {

	public static String decToHex(int dec) {
		int sizeOfIntInHalfBytes = 8;
		int numberOfBitsInAHalfByte = 4;
		int halfByte = 0x0F;
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
				'E', 'F' };
		StringBuilder hexBuilder = new StringBuilder(sizeOfIntInHalfBytes);
		hexBuilder.setLength(sizeOfIntInHalfBytes);
		for (int i = sizeOfIntInHalfBytes - 1; i >= 0; --i) {
			int j = dec & halfByte;
			hexBuilder.setCharAt(i, hexDigits[j]);
			dec >>= numberOfBitsInAHalfByte;
		}
		return hexBuilder.toString();
	}

	private GuiScreen parent;
	private String title = "Advanced Options";
	private GameSettings settings;

	public AdvancedOptionsScreen(GuiScreen var1, GameSettings var2) {
		parent = var1;
		settings = var2;
	}

	@Override
	protected final void onButtonClick(Button var1) {
		if (var1.active) {
			if (var1.id < 100) {
				settings.toggleSetting(var1.id, 1);
				var1.text = settings.getSetting(var1.id);

			}

			if (var1.id == 100) {
				WaterLevelInputScreen screen = new WaterLevelInputScreen(parent, ""
						+ minecraft.level.waterLevel, height, "Enter new value for water level...");
				screen.numbersOnly = true;
				minecraft.setCurrentScreen(screen);
			}
			if (var1.id == 200) {
				SkyColorInputScreen screen = new SkyColorInputScreen(parent, ""
						+ Integer.toHexString(minecraft.level.skyColor), height,
						"Enter new value for sky color...");
				screen.allowedChars = "ABCDEFabcdef1234567890";
				screen.stringLimit = 6;
				minecraft.setCurrentScreen(screen);
			}
			if (var1.id == 300) {
				CloudColorInputScreen screen = new CloudColorInputScreen(parent, ""
						+ Integer.toHexString(minecraft.level.cloudColor), height,
						"Enter new value for cloud color...");
				screen.allowedChars = "ABCDEFabcdef1234567890";
				screen.stringLimit = 6;
				minecraft.setCurrentScreen(screen);
			}
			if (var1.id == 400) {
				FogColorInputScreen screen = new FogColorInputScreen(parent, ""
						+ Integer.toHexString(minecraft.level.fogColor), height,
						"Enter new value for fog color...");
				screen.allowedChars = "ABCDEFabcdef1234567890";
				screen.stringLimit = 6;
				minecraft.setCurrentScreen(screen);
			}
			if (var1.id == 500) {
				ColorCache c = minecraft.level.customLightColour;
				Color color = new Color(255, 255, 255);
				String colorString = "";
				if (c != null) {
					colorString = String.format("%02x%02x%02x", (int) (c.R * 255),
							(int) (c.G * 255), (int) (c.B * 255));
				} else {
					colorString = String.format("%02x%02x%02x", color.getRed(), color.getGreen(),
							color.getBlue());
				}
				LightColorInputScreen screen = new LightColorInputScreen(parent, "" + colorString,
						height, "Enter new value for light color...");
				screen.allowedChars = "ABCDEFabcdef1234567890";
				screen.stringLimit = 6;
				minecraft.setCurrentScreen(screen);
			}
			if (var1.id == 600) {
				ColorCache c = minecraft.level.customShadowColour;
				Color color = new Color(155, 155, 155);
				String colorString = "";
				if (c != null) {
					colorString = String.format("%02x%02x%02x", (int) (c.R * 255),
							(int) (c.G * 255), (int) (c.B * 255));
				} else {
					colorString = String.format("%02x%02x%02x", color.getRed(), color.getGreen(),
							color.getBlue());
				}
				ShadowColorInputScreen screen = new ShadowColorInputScreen(parent,
						"" + colorString, height, "Enter new value for shadow color...");
				screen.allowedChars = "ABCDEFabcdef1234567890";
				screen.stringLimit = 6;
				minecraft.setCurrentScreen(screen);
			}

			if (var1.id == 700) {
				minecraft.setCurrentScreen(new OptionsScreen(this, settings));
			}
		}
	}

	@Override
	public final void onOpen() {
		int heightSeperator = 0;
		for (int var1 = 10; var1 < settings.settingCount; ++var1) {
			buttons.add(new OptionButton(var1, width / 2 - 155 + heightSeperator % 2 * 160, height
					/ 6 + 24 * (heightSeperator >> 1), settings.getSetting(var1)));
			heightSeperator++;
		}
		buttons.add(new OptionButton(100, width / 2 - 155 + heightSeperator % 2 * 160, height / 6
				+ 24 * (heightSeperator >> 1), "Water Level"));
		heightSeperator++;
		buttons.add(new OptionButton(200, width / 2 - 155 + heightSeperator % 2 * 160, height / 6
				+ 24 * (heightSeperator >> 1), "Sky Color"));
		heightSeperator++;
		buttons.add(new OptionButton(300, width / 2 - 155 + heightSeperator % 2 * 160, height / 6
				+ 24 * (heightSeperator >> 1), "Cloud Color"));
		heightSeperator++;
		buttons.add(new OptionButton(400, width / 2 - 155 + heightSeperator % 2 * 160, height / 6
				+ 24 * (heightSeperator >> 1), "Fog Color"));
		heightSeperator++;
		buttons.add(new OptionButton(500, width / 2 - 155 + heightSeperator % 2 * 160, height / 6
				+ 24 * (heightSeperator >> 1), "Sunlight Color"));
		heightSeperator++;
		buttons.add(new OptionButton(600, width / 2 - 155 + heightSeperator % 2 * 160, height / 6
				+ 24 * (heightSeperator >> 1), "Shadow Color"));

		buttons.add(new Button(700, width / 2 - 100, height / 6 + 168, "Done"));

		buttons.get(0).active = minecraft.session != null;
		buttons.get(4).active = minecraft.player.userType >= 100;
	}

	@Override
	public final void render(int var1, int var2) {
		drawFadingBox(0, 0, width, height, 1610941696, -1607454624);
		drawCenteredString(fontRenderer, title, width / 2, 20, 16777215);

		super.render(var1, var2);
	}
}
