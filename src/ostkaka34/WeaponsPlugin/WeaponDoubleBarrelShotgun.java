package ostkaka34.WeaponsPlugin;

import org.bukkit.Sound;

public class WeaponDoubleBarrelShotgun extends WeaponShotgun
{
	@Override
	protected String getName()
	{
		return "Double Barrel Shotgun";
	}

	@Override
	protected int getMagazineAmmo()
	{
		return 12;
	}

	@Override
	protected float getSpreading()
	{
		return 0.1872f;
	}

	@Override
	protected float getDistance()
	{
		return 6;
	}

	protected int getBullets()
	{
		return 16;
	}

	protected Sound getShootSound()
	{
		return Sound.FIREWORK_BLAST;
	}

	protected Sound getReloadSound()
	{
		return Sound.FIREWORK_LAUNCH;
	}
}
