package com.mojang.minecraft.level.tile;

import com.mojang.minecraft.level.Level;

public class LeavesBaseBlock extends Block {

	private boolean showNeighborSides = true;

	protected LeavesBaseBlock(int var1) {
		super(var1);
	}

	@Override
	public final boolean canRenderSide(Level level, int x, int y, int z, int side) {
		int var6 = level.getTile(x, y, z);
		return !showNeighborSides && var6 == id ? false : super.canRenderSide(level, x, y, z, side);
	}

	@Override
	public final boolean isOpaque() {
		return false;
	}

	@Override
	public final boolean isSolid() {
		return false;
	}
}
