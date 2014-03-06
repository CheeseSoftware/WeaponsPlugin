package ostkaka34.WeaponsPlugin;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class RunnableExplodeProjectileLater implements Runnable
{
	private Item projectile;
	private Player shooter;
	private int damage;

	public RunnableExplodeProjectileLater(Item projectile, Player shooter, int damage)
	{
		this.projectile = projectile;
		this.shooter = shooter;
		this.damage = damage;
	}

	@Override
	public void run()
	{
		Location hitLocation = this.projectile.getLocation();
		this.projectile.getWorld().createExplosion(hitLocation.getX(), hitLocation.getY(), hitLocation.getZ(), this.damage, false, false);
		this.projectile.getWorld().playEffect(hitLocation, Effect.SMOKE, 10);
		for (Entity e : this.projectile.getNearbyEntities(10, 10, 10))
		{
			if (e instanceof LivingEntity && (this.shooter.equals(e) || !(e instanceof Player)))
			{
				LivingEntity entity = (LivingEntity) e;
				entity.damage(damage * 15 / hitLocation.distance(entity.getLocation()));
			}
		}
		projectile.remove();
	}

}
