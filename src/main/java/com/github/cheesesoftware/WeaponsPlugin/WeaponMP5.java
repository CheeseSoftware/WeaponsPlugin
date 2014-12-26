package com.github.cheesesoftware.WeaponsPlugin;

import org.bukkit.Material;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;

public class WeaponMP5
  extends BulletWeapon
{
  protected String getName()
  {
    return "MP5";
  }
  
  protected int getMagazineAmmo()
  {
    return 50;
  }
  
  public Material getMagazineType()
  {
    return Material.STICK;
  }
  
  protected Class<? extends Projectile> getProjectile()
  {
    return Snowball.class;
  }
  
  protected float getProjectileSpeed()
  {
    return 2.5F;
  }
  
  protected float getSpreading()
  {
    return 0.035375F;
  }
  
  protected int getCooldown()
  {
    return 2;
  }
  
  protected int getReloadCooldown()
  {
    return 30;
  }
  
  protected int getDamage()
  {
    return 6;
  }
  
  protected float getDistance()
  {
    return 192.0F;
  }
}
