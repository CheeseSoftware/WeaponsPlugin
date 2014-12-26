package com.github.cheesesoftware.WeaponsPlugin;

import org.bukkit.Material;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;

public class WeaponMAC10 extends BulletWeapon
{
	@Override
	protected String getName()
	{
		return "MAC-10";
	}

	@Override
	protected int getMagazineAmmo()
	{
		return 50;
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
		return 2f;
	}

	@Override
	protected float getSpreading()
	{
		return 0.0375f;
	}

	@Override
	protected int getCooldown()
	{
		return 3;
	}

	@Override
	protected int getReloadCooldown()
	{
		return 30;
	}

	@Override
	protected int getDamage()
	{
		return 4;
	}

	@Override
	protected float getDistance()
	{
		return 128;
	}
}
