package com.mojang.minecraft.level;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mojang.minecraft.Entity;
import com.mojang.minecraft.model.Vec3D;
import com.mojang.minecraft.phys.AABB;
import com.mojang.minecraft.render.Frustrum;
import com.mojang.minecraft.render.TextureManager;

public class BlockMap implements Serializable {

	public static final long serialVersionUID = 0L;

	// $FF: synthetic method
	static int getDepth(BlockMap var0) {
		return var0.depth;
	}

	// $FF: synthetic method
	static int getHeight(BlockMap var0) {
		return var0.height;
	}

	// $FF: synthetic method
	static int getWidth(BlockMap var0) {
		return var0.width;
	}

	private int width;
	private int depth;
	private int height;
	private BlockMap$Slot slot = new BlockMap$Slot(this);
	private BlockMap$Slot slot2 = new BlockMap$Slot(this);

	public List<Entity>[] entityGrid;

	public List<Entity> all = new ArrayList<Entity>();

	private List<Entity> tmp = new ArrayList<Entity>();

	@SuppressWarnings("unchecked")
	public BlockMap(int var1, int var2, int var3) {
		width = var1 / 16;
		depth = var2 / 16;
		height = var3 / 16;
		if (width == 0) {
			width = 1;
		}

		if (depth == 0) {
			depth = 1;
		}

		if (height == 0) {
			height = 1;
		}

		entityGrid = new ArrayList[width * depth * height];

		for (var1 = 0; var1 < width; ++var1) {
			for (var2 = 0; var2 < depth; ++var2) {
				for (var3 = 0; var3 < height; ++var3) {
					entityGrid[(var3 * depth + var2) * width + var1] = new ArrayList<Entity>();
				}
			}
		}

	}

	public void clear() {
		for (int var1 = 0; var1 < width; ++var1) {
			for (int var2 = 0; var2 < depth; ++var2) {
				for (int var3 = 0; var3 < height; ++var3) {
					entityGrid[(var3 * depth + var2) * width + var1].clear();
				}
			}
		}

	}

	public List<Entity> getEntities(Entity var1, AABB var2) {
		tmp.clear();
		return this.getEntities(var1, var2.x0, var2.y0, var2.z0, var2.x1, var2.y1, var2.z1, tmp);
	}

	public List<Entity> getEntities(Entity var1, AABB var2, List<Entity> var3) {
		return this.getEntities(var1, var2.x0, var2.y0, var2.z0, var2.x1, var2.y1, var2.z1, var3);
	}

	public List<Entity> getEntities(Entity var1, float var2, float var3, float var4, float var5,
			float var6, float var7) {
		tmp.clear();
		return this.getEntities(var1, var2, var3, var4, var5, var6, var7, tmp);
	}

	public List<Entity> getEntities(Entity var1, float var2, float var3, float var4, float var5,
			float var6, float var7, List<Entity> var8) {
		BlockMap$Slot var9 = slot.init(var2, var3, var4);
		BlockMap$Slot var10 = slot2.init(var5, var6, var7);

		for (int var11 = BlockMap$Slot.getXSlot(var9) - 1; var11 <= BlockMap$Slot.getXSlot(var10) + 1; ++var11) {
			for (int var12 = BlockMap$Slot.getYSlot(var9) - 1; var12 <= BlockMap$Slot
					.getYSlot(var10) + 1; ++var12) {
				for (int var13 = BlockMap$Slot.getZSlot(var9) - 1; var13 <= BlockMap$Slot
						.getZSlot(var10) + 1; ++var13) {
					if (var11 >= 0 && var12 >= 0 && var13 >= 0 && var11 < width && var12 < depth
							&& var13 < height) {
						List<?> var14 = entityGrid[(var13 * depth + var12) * width + var11];

						for (int var15 = 0; var15 < var14.size(); ++var15) {
							Entity var16;
							if ((var16 = (Entity) var14.get(var15)) != var1
									&& var16.intersects(var2, var3, var4, var5, var6, var7)) {
								var8.add(var16);
							}
						}
					}
				}
			}
		}

		return var8;
	}

	public void insert(Entity var1) {
		all.add(var1);
		slot.init(var1.x, var1.y, var1.z).add(var1);
		var1.xOld = var1.x;
		var1.yOld = var1.y;
		var1.zOld = var1.z;
		var1.blockMap = this;
	}

	public void moved(Entity var1) {
		BlockMap$Slot var2 = slot.init(var1.xOld, var1.yOld, var1.zOld);
		BlockMap$Slot var3 = slot2.init(var1.x, var1.y, var1.z);
		if (!var2.equals(var3)) {
			var2.remove(var1);
			var3.add(var1);
			var1.xOld = var1.x;
			var1.yOld = var1.y;
			var1.zOld = var1.z;
		}
	}

	public void remove(Entity var1) {
		slot.init(var1.xOld, var1.yOld, var1.zOld).remove(var1);
		all.remove(var1);
	}

	public void removeAllNonCreativeModeEntities() {
		for (int var1 = 0; var1 < width; ++var1) {
			for (int var2 = 0; var2 < depth; ++var2) {
				for (int var3 = 0; var3 < height; ++var3) {
					List<?> var4 = entityGrid[(var3 * depth + var2) * width + var1];

					for (int var5 = 0; var5 < var4.size(); ++var5) {
						if (!((Entity) var4.get(var5)).isCreativeModeAllowed()) {
							var4.remove(var5--);
						}
					}
				}
			}
		}

	}

	public void render(Vec3D var1, Frustrum var2, TextureManager var3, float var4) {
		for (int var5 = 0; var5 < width; ++var5) {
			float var6 = (var5 << 4) - 2;
			float var7 = (var5 + 1 << 4) + 2;

			for (int var8 = 0; var8 < depth; ++var8) {
				float var9 = (var8 << 4) - 2;
				float var10 = (var8 + 1 << 4) + 2;

				for (int var11 = 0; var11 < height; ++var11) {
					List<?> var12;
					if ((var12 = entityGrid[(var11 * depth + var8) * width + var5]).size() != 0) {
						float var13 = (var11 << 4) - 2;
						float var14 = (var11 + 1 << 4) + 2;
						if (var2.isBoxInFrustum(var6, var9, var13, var7, var10, var14)) {
							float var16 = var14;
							float var17 = var10;
							float var15 = var7;
							var14 = var13;
							var13 = var9;
							float var18 = var6;
							Frustrum var19 = var2;
							int var20 = 0;

							boolean var10000;
							while (true) {
								if (var20 >= 6) {
									var10000 = true;
									break;
								}

								if (var19.frustum[var20][0] * var18 + var19.frustum[var20][1]
										* var13 + var19.frustum[var20][2] * var14
										+ var19.frustum[var20][3] <= 0.0F) {
									var10000 = false;
									break;
								}

								if (var19.frustum[var20][0] * var15 + var19.frustum[var20][1]
										* var13 + var19.frustum[var20][2] * var14
										+ var19.frustum[var20][3] <= 0.0F) {
									var10000 = false;
									break;
								}

								if (var19.frustum[var20][0] * var18 + var19.frustum[var20][1]
										* var17 + var19.frustum[var20][2] * var14
										+ var19.frustum[var20][3] <= 0.0F) {
									var10000 = false;
									break;
								}

								if (var19.frustum[var20][0] * var15 + var19.frustum[var20][1]
										* var17 + var19.frustum[var20][2] * var14
										+ var19.frustum[var20][3] <= 0.0F) {
									var10000 = false;
									break;
								}

								if (var19.frustum[var20][0] * var18 + var19.frustum[var20][1]
										* var13 + var19.frustum[var20][2] * var16
										+ var19.frustum[var20][3] <= 0.0F) {
									var10000 = false;
									break;
								}

								if (var19.frustum[var20][0] * var15 + var19.frustum[var20][1]
										* var13 + var19.frustum[var20][2] * var16
										+ var19.frustum[var20][3] <= 0.0F) {
									var10000 = false;
									break;
								}

								if (var19.frustum[var20][0] * var18 + var19.frustum[var20][1]
										* var17 + var19.frustum[var20][2] * var16
										+ var19.frustum[var20][3] <= 0.0F) {
									var10000 = false;
									break;
								}

								if (var19.frustum[var20][0] * var15 + var19.frustum[var20][1]
										* var17 + var19.frustum[var20][2] * var16
										+ var19.frustum[var20][3] <= 0.0F) {
									var10000 = false;
									break;
								}

								++var20;
							}

							boolean var21 = var10000;

							for (int var23 = 0; var23 < var12.size(); ++var23) {
								Entity var22;
								if ((var22 = (Entity) var12.get(var23)).shouldRender(var1)) {
									if (!var21) {
										AABB var24 = var22.bb;
										if (!var2.isBoxInFrustum(var24.x0, var24.y0, var24.z0,
												var24.x1, var24.y1, var24.z1)) {
											continue;
										}
									}

									var22.render(var3, var4);
								}
							}
						}
					}
				}
			}
		}

	}

	public void tickAll() {
		for (int var1 = 0; var1 < all.size(); ++var1) {
			Entity var2;
			(var2 = all.get(var1)).tick();
			if (var2.removed) {
				all.remove(var1--);
				slot.init(var2.xOld, var2.yOld, var2.zOld).remove(var2);
			} else {
				int var3 = (int) (var2.xOld / 16.0F);
				int var4 = (int) (var2.yOld / 16.0F);
				int var5 = (int) (var2.zOld / 16.0F);
				int var6 = (int) (var2.x / 16.0F);
				int var7 = (int) (var2.y / 16.0F);
				int var8 = (int) (var2.z / 16.0F);
				if (var3 != var6 || var4 != var7 || var5 != var8) {
					moved(var2);
				}
			}
		}

	}
}
