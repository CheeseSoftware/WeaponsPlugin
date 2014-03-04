package ostkaka34.WeaponsPlugin;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
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
		for(Entity e : this.projectile.getNearbyEntities(10, 10, 10))
		{
			if (e instanceof Creature && (this.shooter.equals(e) || !(e instanceof Player)))
			{
				Creature player = (Creature) e;
				player.damage(damage * 15 / hitLocation.distance(player.getLocation()));
			}
		}
		projectile.remove();
	}

}
