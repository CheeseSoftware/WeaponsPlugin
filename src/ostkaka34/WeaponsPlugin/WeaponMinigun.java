package ostkaka34.WeaponsPlugin;

import org.bukkit.Material;

public class WeaponMinigun extends Weapon {

	@Override
	protected String getName() {

		return "minigun";
	}

	@Override
	protected int getMagazineAmmo() {

		return 200;
	}

	@Override
	public Material getMagazineType() {

		return Material.BRICK;
	}

	@Override
	protected float getSnowballSpeed() {

		return 3;
	}

	@Override
	protected float getSpreading() {

		return 0.0625F;
	}

	@Override
	protected int getCooldown() {

		return 1;
	}

	@Override
	protected int getReloadCooldown() {

		return 160;
	}

	@Override
	protected int getDamage() {

		return 8;
	}

	@Override
	protected float getDistance() {

		return 256;
	}

}
