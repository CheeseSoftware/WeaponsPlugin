package ostkaka34.WeaponsPlugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class WeaponGrenadeLauncher
  extends Weapon
{
  protected String getName()
  {
    return "Grenade Launcher";
  }
  
  protected int getMagazineAmmo()
  {
    return 8;
  }
  
  protected int getBullets()
  {
    return 1;
  }
  
  public Material getMagazineType()
  {
    return Material.SLIME_BALL;
  }
  
  protected Class<? extends Projectile> getProjectile()
  {
    return Arrow.class;
  }
  
  protected float getProjectileSpeed()
  {
    return 1.6F;
  }
  
  protected float getSpreading()
  {
    return 0.0625F;
  }
  
  protected int getCooldown()
  {
    return 15;
  }
  
  protected int getReloadCooldown()
  {
    return 100;
  }
  
  protected int getDamage()
  {
    return 20;
  }
  
  protected float getDistance()
  {
    return 0.0F;
  }
  
  protected Sound getShootSound()
  {
    return Sound.FIREWORK_TWINKLE;
  }
  
  protected Sound getReloadSound()
  {
    return Sound.FIREWORK_LAUNCH;
  }
  
  protected void onProjectileHitEntity(EntityDamageByEntityEvent event, Projectile projectile)
  {
    if ((event.getEntity() instanceof LivingEntity))
    {
      LivingEntity entity = (LivingEntity)event.getEntity();
      entity.damage(getDamage());
    }
    Explode(projectile, getDamage());
  }
  
  protected void onProjectileHitGround(ProjectileHitEvent event, Projectile projectile)
  {
    Explode(projectile, getDamage());
  }
  
  public static void Explode(Projectile projectile, int damage)
  {
    Location hitLocation = projectile.getLocation();
    projectile.getWorld().createExplosion(hitLocation.getX(), hitLocation.getY(), hitLocation.getZ(), damage, false, false);
    for (Entity e : projectile.getNearbyEntities(10.0D, 10.0D, 10.0D))
    {
      double distance = e.getLocation().distance(projectile.getLocation());
      if (distance < 10.0D) {
        if (((e instanceof Creature)) && ((projectile.getShooter().equals(e)) || (!(e instanceof Player))))
        {
          Creature player = (Creature)e;
          double damage2 = damage / Math.pow(hitLocation.distance(player.getLocation()) / 16.0D, 2.0D);
          
          damage2 = damage2 > damage ? damage : damage2;
          
          player.damage(damage2);
        }
      }
    }
    projectile.remove();
  }
}
