package ostkaka34.WeaponsPlugin;

import org.bukkit.Material;

public class WeaponB extends Weapon {
	@Override
	protected String getName() {
		return "MP5";
	}
	
	@Override
	protected int getMagazineAmmo() {
		return 50;
	}

	@Override
	public Material getMagazineType() {
		return Material.STICK;
	}

	@Override
	protected float getSnowballSpeed() {
		return 2.5f;
	}

	@Override
	protected float getSpreading() {
		return 0.0625f;
	}

	@Override
	protected int getCooldown() {
		return 2;
	}

	@Override
	protected int getReloadCooldown() {
		return 30;
	}
	
	@Override
	protected int getDamage() {
		return 6;
	}
	
	@Override
	protected float getDistance() {
		return 192;
	}
}
