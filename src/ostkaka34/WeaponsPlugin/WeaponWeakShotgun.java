package ostkaka34.WeaponsPlugin;

import org.bukkit.Material;
import org.bukkit.Sound;

public class WeaponWeakShotgun extends Weapon {
	@Override
	protected String getName() {
		return "Shotgun";
	}

	@Override
	protected int getMagazineAmmo() {
		return 8;
	}

	@Override
	public Material getMagazineType() {
		return Material.WOOD_BUTTON;
	}

	@Override
	protected float getSnowballSpeed() {
		return 2f;
	}

	@Override
	protected float getSpreading() {
		return 0.25f;
	}

	@Override
	protected int getCooldown() {
		return 10;
	}

	@Override
	protected int getReloadCooldown() {
		return 80;
	}

	@Override
	protected int getDamage() {
		return 13;
	}

	@Override
	protected float getDistance() {
		return 6;
	}

	protected int getBullets() {
		return 10;
	}

	protected Sound getShootSound() {
		return Sound.FIREWORK_BLAST;
	}

	protected Sound getReloadSound() {
		return Sound.ANVIL_USE;
	}
}
