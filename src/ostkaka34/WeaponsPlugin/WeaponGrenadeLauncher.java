package ostkaka34.WeaponsPlugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class WeaponGrenadeLauncher extends Weapon
{

	@Override
	protected String getName()
	{
		return "Grenade Launcher";
	}

	@Override
	protected int getMagazineAmmo()
	{
		return 8;
	}

	@Override
	public Material getMagazineType()
	{
		return Material.SLIME_BALL;
	}

	@Override
	protected Class<? extends Projectile> getProjectile()
	{
		return Arrow.class;
	}

	@Override
	protected float getProjectileSpeed()
	{
		return 1.6f;
	}

	@Override
	protected float getSpreading()
	{
		return 0.0625f;
	}

	@Override
	protected int getCooldown()
	{
		return 15;
	}

	@Override
	protected int getReloadCooldown()
	{
		return 100;
	}

	@Override
	protected int getDamage()
	{
		return 3;
	}

	@Override
	protected float getDistance()
	{
		return 0;
	}

	@Override
	protected Sound getShootSound()
	{
		return Sound.FIREWORK_TWINKLE;
	}

	@Override
	protected Sound getReloadSound()
	{
		return Sound.FIREWORK_LAUNCH;
	}

	@Override
	protected void onProjectileHitEntity(EntityDamageByEntityEvent event, Projectile projectile)
	{
		if (event.getEntity() instanceof LivingEntity)
		{
			LivingEntity entity = (LivingEntity) event.getEntity();
			entity.setHealth(0);
		}
		
		// FY!!!
		WeaponGrenadeLauncher.Explode(projectile, this.getDamage());
	}

	@Override
	protected void onProjectileHitGround(ProjectileHitEvent event, Projectile projectile)
	{
		// FY!!!
		WeaponGrenadeLauncher.Explode(projectile, this.getDamage());
	}

	public static void Explode(Projectile projectile, int damage)
	{
		// Konstigt ställe ett ha statisk metod på. Om det används utanför så har det inget med vapnet att göra.
		Location hitLocation = projectile.getLocation();
		projectile.getWorld().createExplosion(hitLocation.getX(), hitLocation.getY(), hitLocation.getZ(), damage, false, false);
		for (Entity e : projectile.getNearbyEntities(10, 10, 10))
		{
			if (e instanceof Creature && (projectile.getShooter().equals(e) || !(e instanceof Player)))
			{
				Creature player = (Creature) e;
				player.damage(damage * 15 / hitLocation.distance(player.getLocation()));
			}
		}
		projectile.remove();
	}

}
