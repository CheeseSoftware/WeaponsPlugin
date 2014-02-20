package ostkaka34.WeaponsPlugin;

import org.bukkit.Material;

public class WeaponA extends Weapon {
	@Override
	protected String getName() {
		return "MAC-10";
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
		return 2f;
	}

	@Override
	protected float getSpreading() {
		return 0.09275f;
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
		return 4;
	}
	
	@Override
	protected float getDistance() {
		return 128;
	}

}
