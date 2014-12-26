package com.github.cheesesoftware.WeaponsPlugin;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;

public class WeaponMagnum extends BulletWeapon
{

	@Override
	protected String getName()
	{
		return "Magnum";
	}

	@Override
	protected int getMagazineAmmo()
	{
		return 6;
	}

	@Override
	public Material getMagazineType()
	{
		return Material.COAL;
	}

	@Override
	protected Class<? extends Projectile> getProjectile()
	{
		return Snowball.class;
	}

	@Override
	protected float getProjectileSpeed()
	{
		return 4;
	}

	@Override
	protected float getSpreading()
	{
		return 0;
	}

	@Override
	protected int getCooldown()
	{
		return 10;
	}

	@Override
	protected int getReloadCooldown()
	{
		// TODO Auto-generated method stub
		return 24;
	}

	@Override
	protected int getDamage()
	{
		// TODO Auto-generated method stub
		return 30;
	}

	@Override
	protected float getDistance()
	{
		// TODO Auto-generated method stub
		return 256;
	}

	protected int getBullets()
	{
		return 1;
	}

	protected Sound getShootSound()
	{
		return Sound.FIREWORK_LARGE_BLAST2;
	}

	protected Sound getReloadSound()
	{
		return Sound.FIREWORK_TWINKLE2;
	}

}
