package ostkaka34.WeaponsPlugin;

import org.bukkit.Material;
import org.bukkit.Sound;

public class WeaponMagnum extends Weapon{

	@Override
	protected String getName() {
		return "Magnum";
	}

	@Override
	protected int getMagazineAmmo() {
		return 6;
	}

	@Override
	public Material getMagazineType() {
		return Material.FIREWORK_CHARGE;
	}

	@Override
	protected float getSnowballSpeed() {
		return 4;
	}

	@Override
	protected float getSpreading() {
		return 0;
	}

	@Override
	protected int getCooldown() {
		return 10;
	}

	@Override
	protected int getReloadCooldown() {
		// TODO Auto-generated method stub
		return 40;
	}

	@Override
	protected int getDamage() {
		// TODO Auto-generated method stub
		return 25;
	}

	@Override
	protected float getDistance() {
		// TODO Auto-generated method stub
		return 256;
	}
	
	protected int getBullets() {
		return 1;
	}
	
	protected Sound getShootSound() {
		return Sound.FIREWORK_LARGE_BLAST2;
	}
	
	protected Sound getReloadSound() {
		return Sound.FIREWORK_TWINKLE2;
	}

}
