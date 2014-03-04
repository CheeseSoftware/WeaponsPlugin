package ostkaka34.WeaponsPlugin;

import org.bukkit.Sound;

public class WeaponWeakShotgun extends WeaponShotgun {
	@Override
	protected String getName() {
		return "Weak Shotgun";
	}

	@Override
	protected float getSpreading() {
		return 0.1872f;
	}

	@Override
	protected int getCooldown() {
		return 15;
	}
	
	@Override
	protected int getDamage() {
		return 7;
	}
	
	@Override
	protected float getDistance() {
		return 5;
	}
	
	protected Sound getShootSound() {
		return Sound.FIREWORK_BLAST;
	}
	
	protected Sound getReloadSound() {
		return Sound.FIREWORK_LAUNCH;
	}
}
