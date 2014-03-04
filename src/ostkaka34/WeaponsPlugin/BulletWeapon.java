package ostkaka34.WeaponsPlugin;

import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public abstract class BulletWeapon extends Weapon
{
	@Override
	protected void onProjectileHitEntity(EntityDamageByEntityEvent event, Projectile projectile)
	{
		double distance = projectile.getLocation().distance(projectiles.get(projectile));
		distance -= 2;

		if (projectile instanceof Snowball)
		{
			if (distance <= 0)
				event.setDamage(this.getDamage());
			else
			{
				double damageFactor = (double) this.getDamage() / (double) this.getDistance();
				int damage = this.getDamage() - (int) (damageFactor * distance);
				event.setDamage(damage);
			}
		}
	}

	@Override
	protected void onProjectileHitGround(ProjectileHitEvent event, Projectile projectile)
	{
		
	}
}
