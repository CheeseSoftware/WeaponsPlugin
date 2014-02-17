package ostkaka34.WeaponsPlugin;

import org.bukkit.Material;
import org.bukkit.Sound;

public class WeaponsDoubleBarrelShotgun extends Weapon {
	@Override
	protected String getName() {
		return "Double Barrel Shotgun";
	}
	
	@Override
	protected int getMagazineAmmo() {
		return 6;
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
		return 0.325f;
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
		return 21;
	}
	
	@Override
	protected float getDistance() {
		return 6;
	}
	
	protected int getBullets() {
		return 16;
	}
	
	protected Sound getShootSound() {
		return Sound.FIREWORK_BLAST;
	}
	
	protected Sound getReloadSound() {
		return Sound.ANVIL_USE;
	}
}
