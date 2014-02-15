package ostkaka34.WeaponsPlugin;

import org.bukkit.Material;

public class WeaponA extends Weapon {
	@Override
	protected String getName() {
		return "Shovel Gun";
	}
	
	@Override
	protected int getMagazineAmmo() {
		return 50;
	}

	@Override
	protected Material getMagazineType() {
		return Material.STICK;
	}

	@Override
	protected float getSnowballSpeed() {
		return 2f;
	}

	@Override
	protected float getSpreading() {
		return 0.125f;
	}

	@Override
	protected int getCooldown() {
		return 3;
	}

	@Override
	protected int getReloadCooldown() {
		return 40;
	}
	
	@Override
	protected int getDamage() {
		return 5;
	}
	
	@Override
	protected float getDistance() {
		return 128;
	}

}
