package ostkaka34.WeaponsPlugin;

import org.bukkit.Location;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public abstract class BulletWeapon
  extends Weapon
{
  protected void onProjectileHitEntity(EntityDamageByEntityEvent event, Projectile projectile)
  {
    double distance = projectile.getLocation().distance((Location)this.projectiles.get(projectile));
    distance -= 2.0D;
    if ((projectile instanceof Snowball)) {
      if (distance <= 0.0D)
      {
        event.setDamage(getDamage());
      }
      else
      {
        double damageFactor = getDamage() / getDistance();
        int damage = getDamage() - (int)(damageFactor * distance);
        event.setDamage(damage);
      }
    }
  }
  
  protected void onProjectileHitGround(ProjectileHitEvent event, Projectile projectile) {}
}
