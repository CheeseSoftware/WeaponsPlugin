package ostkaka34.WeaponsPlugin;

import org.bukkit.Material;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;

public class WeaponP90 extends BulletWeapon
{
	@Override
	protected String getName()
	{
		return "P-90";
	}

	@Override
	protected int getMagazineAmmo()
	{
		return 100;
	}

	@Override
	public Material getMagazineType()
	{
		return Material.STICK;
	}

	@Override
	protected Class<? extends Projectile> getProjectile()
	{
		return Snowball.class;
	}

	@Override
	protected float getProjectileSpeed()
	{
		return 3f;
	}

	@Override
	protected float getSpreading()
	{
		return 0.03125f;
	}

	@Override
	protected int getCooldown()
	{
		return 2;
	}

	@Override
	protected int getReloadCooldown()
	{
		return 30;
	}

	@Override
	protected int getDamage()
	{
		return 8;
	}

	@Override
	protected float getDistance()
	{
		return 256;
	}

}
