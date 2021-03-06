package com.mojang.minecraft.level.tile;

import java.util.Random;

import com.mojang.minecraft.level.Level;

public final class MushroomBlock extends FlowerBlock {

	protected MushroomBlock(int var1) {
		super(var1);
		float var3 = 0.2F;
		setBounds(0.5F - var3, 0.0F, 0.5F - var3, var3 + 0.5F, var3 * 2.0F, var3 + 0.5F);
	}

	@Override
	public final void update(Level level, int x, int y, int z, Random rand) {
		int var6 = level.getTile(x, y - 1, z);
		if (level.isLit(x, y, z) || var6 != STONE.id && var6 != GRAVEL.id && var6 != COBLESTONE.id) {
			level.setTile(x, y, z, 0);
		}

	}
}
