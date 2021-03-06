package com.mojang.minecraft.level.tile;

public final class MetalBlock extends Block {

	public MetalBlock(int var1) {
		super(var1);
	}

	@Override
	protected final int getTextureId(int texture) {
		return texture == 1 ? textureId - 16 : texture == 0 ? textureId + 16 : textureId;
	}
}
