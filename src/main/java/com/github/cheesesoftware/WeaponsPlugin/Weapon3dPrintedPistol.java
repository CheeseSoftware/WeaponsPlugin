package com.github.cheesesoftware.WeaponsPlugin;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;

public class Weapon3dPrintedPistol
  extends BulletWeapon
{
  protected String getName()
  {
    return "3D printed pistol";
  }
  
  protected int getMagazineAmmo()
  {
    return 10;
  }
  
  public Material getMagazineType()
  {
    return Material.AIR;
  }
  
  protected Class<? extends Projectile> getProjectile()
  {
    return Snowball.class;
  }
  
  protected float getProjectileSpeed()
  {
    return 2.0F;
  }
  
  protected float getSpreading()
  {
    return 0.03125F;
  }
  
  protected int getCooldown()
  {
    return 10;
  }
  
  protected int getReloadCooldown()
  {
    return 40;
  }
  
  protected int getDamage()
  {
    return 6;
  }
  
  protected float getDistance()
  {
    return 256.0F;
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
