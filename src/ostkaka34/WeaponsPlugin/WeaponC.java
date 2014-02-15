package ostkaka34.WeaponsPlugin;

import org.bukkit.Material;

public class WeaponC extends Weapon {
	@Override
	protected String getName() {
		return "Pickaxe Gun";
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
		return 3f;
	}

	@Override
	protected float getSpreading() {
		return 0.03125f;
	}

	@Override
	protected int getCooldown() {
		return 1;
	}

	@Override
	protected int getReloadCooldown() {
		return 30;
	}
	
	@Override
	protected int getDamage() {
		return 13;
	}
	
	@Override
	protected float getDistance() {
		return 256;
	}
	
}
