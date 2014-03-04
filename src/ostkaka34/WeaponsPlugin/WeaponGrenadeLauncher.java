package ostkaka34.WeaponsPlugin;

import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Projectile;

public class WeaponGrenadeLauncher extends Weapon
{

	@Override
	protected String getName()
	{
		return "Grenade Launcher";
	}

	@Override
	protected int getMagazineAmmo()
	{
		return 1;
	}

	@Override
	public Material getMagazineType()
	{
		return Material.TNT;
	}

	@Override
	protected Class<? extends Projectile> getProjectile()
	{
		return Fireball.class;
	}

	@Override
	protected float getProjectileSpeed()
	{
		return 1f;
	}

	@Override
	protected float getSpreading()
	{
		return 0.0625f;
	}

	@Override
	protected int getCooldown()
	{
		return 0;
	}

	@Override
	protected int getReloadCooldown()
	{
		return 100;
	}

	@Override
	protected int getDamage()
	{
		return 0;
	}

	@Override
	protected float getDistance()
	{
		return 0;
	}

}
