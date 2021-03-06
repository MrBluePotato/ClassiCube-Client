package com.mojang.minecraft.particle;

import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.render.ShapeRenderer;

public class WaterDropParticle extends Particle {

	private static final long serialVersionUID = 1L;

	public WaterDropParticle(Level var1, float var2, float var3, float var4) {
		super(var1, var2, var3, var4, 0.0F, 0.0F, 0.0F);
		xd *= 0.3F;
		yd = (float) Math.random() * 0.2F + 0.1F;
		zd *= 0.3F;
		rCol = 1.0F;
		gCol = 1.0F;
		bCol = 1.0F;
		tex = 16;
		setSize(0.01F, 0.01F);
		lifetime = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
	}

	@Override
	public void render(ShapeRenderer var1, float var2, float var3, float var4, float var5,
			float var6, float var7) {
		super.render(var1, var2, var3, var4, var5, var6, var7);
	}

	@Override
	public void tick() {
		xo = x;
		yo = y;
		zo = z;
		yd = (float) (yd - 0.06D);
		move(xd, yd, zd);
		xd *= 0.98F;
		yd *= 0.98F;
		zd *= 0.98F;
		if (lifetime-- <= 0) {
			remove();
		}

		if (onGround) {
			if (Math.random() < 0.5D) {
				remove();
			}

			xd *= 0.7F;
			zd *= 0.7F;
		}

	}
}
