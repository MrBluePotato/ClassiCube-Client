package com.mojang.minecraft.render.texture;

import com.mojang.minecraft.level.tile.Block;
import com.mojang.util.MathHelper;

public final class TextureLavaFX extends TextureFX {

	private float[] red = new float[256];
	private float[] green = new float[256];
	private float[] blue = new float[256];
	private float[] alpha = new float[256];

	public TextureLavaFX() {
		super(Block.LAVA.textureId);
	}

	@Override
	public final void animate() {
		int var1;
		int var2;
		float var3;
		int var5;
		int var6;
		int var7;
		int var8;
		int var9;
		for (var1 = 0; var1 < 16; ++var1) {
			for (var2 = 0; var2 < 16; ++var2) {
				var3 = 0.0F;
				int var4 = (int) (MathHelper.sin(var2 * 3.1415927F * 2.0F / 16.0F) * 1.2F);
				var5 = (int) (MathHelper.sin(var1 * 3.1415927F * 2.0F / 16.0F) * 1.2F);

				for (var6 = var1 - 1; var6 <= var1 + 1; ++var6) {
					for (var7 = var2 - 1; var7 <= var2 + 1; ++var7) {
						var8 = var6 + var4 & 15;
						var9 = var7 + var5 & 15;
						var3 += red[var8 + (var9 << 4)];
					}
				}

				green[var1 + (var2 << 4)] = var3
						/ 10.0F
						+ (blue[(var1 & 15) + ((var2 & 15) << 4)]
								+ blue[(var1 + 1 & 15) + ((var2 & 15) << 4)]
								+ blue[(var1 + 1 & 15) + ((var2 + 1 & 15) << 4)] + blue[(var1 & 15)
								+ ((var2 + 1 & 15) << 4)]) / 4.0F * 0.8F;
				blue[var1 + (var2 << 4)] += alpha[var1 + (var2 << 4)] * 0.01F;
				if (blue[var1 + (var2 << 4)] < 0.0F) {
					blue[var1 + (var2 << 4)] = 0.0F;
				}

				alpha[var1 + (var2 << 4)] -= 0.06F;
				if (Math.random() < 0.005D) {
					alpha[var1 + (var2 << 4)] = 1.5F;
				}
			}
		}

		float[] var10 = green;
		green = red;
		red = var10;

		for (var2 = 0; var2 < 256; ++var2) {
			if ((var3 = red[var2] * 2.0F) > 1.0F) {
				var3 = 1.0F;
			}

			if (var3 < 0.0F) {
				var3 = 0.0F;
			}

			var5 = (int) (var3 * 100.0F + 155.0F);
			var6 = (int) (var3 * var3 * 255.0F);
			var7 = (int) (var3 * var3 * var3 * var3 * 128.0F);
			if (anaglyph) {
				var8 = (var5 * 30 + var6 * 59 + var7 * 11) / 100;
				var9 = (var5 * 30 + var6 * 70) / 100;
				var1 = (var5 * 30 + var7 * 70) / 100;
				var5 = var8;
				var6 = var9;
				var7 = var1;
			}

			textureData[var2 << 2] = (byte) var5;
			textureData[(var2 << 2) + 1] = (byte) var6;
			textureData[(var2 << 2) + 2] = (byte) var7;
			textureData[(var2 << 2) + 3] = -1;
		}

	}
}
