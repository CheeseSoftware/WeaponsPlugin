package ostkaka34.WeaponsPlugin;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;

public class WeaponShotgun extends Weapon
{
	@Override
	protected String getName()
	{
		return "Shotgun";
	}

	@Override
	protected int getMagazineAmmo()
	{
		return 12;
	}

	@Override
	public Material getMagazineType()
	{
		return Material.GOLD_NUGGET;
	}

	@Override
	protected Class<? extends Projectile> getProjectile()
	{
		return Snowball.class;
	}

	@Override
	protected float getProjectileSpeed()
	{
		return 2f;
	}

	@Override
	protected float getSpreading()
	{
		return 0.125f;
	}

	@Override
	protected int getCooldown()
	{
		return 10;
	}

	@Override
	protected int getReloadCooldown()
	{
		return 80;
	}

	@Override
	protected int getDamage()
	{
		return 10;
	}

	@Override
	protected float getDistance()
	{
		return 8;
	}

	protected int getBullets()
	{
		return 8;
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
