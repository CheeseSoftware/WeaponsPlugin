package ostkaka34.WeaponsPlugin;

import java.lang.String;
import java.util.Map;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Weapon
{
	protected int ammo = 0;
	protected int cooldown = 0;
	protected boolean reloading = false;
	protected boolean outOfAmmo = false;
	protected int shootTicks = 0;
	protected Map<Projectile, Location> projectiles = new HashMap<Projectile, Location>();

	protected abstract String getName();

	protected abstract int getMagazineAmmo();

	public abstract Material getMagazineType();

	protected abstract Class<? extends Projectile> getProjectile();

	protected abstract float getProjectileSpeed();

	protected abstract float getSpreading();

	protected abstract int getCooldown();

	protected abstract int getReloadCooldown();

	protected abstract int getDamage();

	protected abstract float getDistance();

	protected abstract void onProjectileHitEntity(EntityDamageByEntityEvent event, Projectile projectile);
	
	protected abstract void onProjectileHitGround(ProjectileHitEvent event, Projectile projectile);
	
	protected int getBullets()
	{
		return 1;
	}

	protected Sound getShootSound()
	{
		return Sound.FIREWORK_BLAST2;
	}

	protected Sound getReloadSound()
	{
		return Sound.FIREWORK_LARGE_BLAST;
	}

	protected void UpdateGui(Player player)
	{
		ItemStack item = player.getItemInHand();
		ItemMeta i = item.getItemMeta();

		String text;

		if (reloading)
			text = this.getName() + " - Reloading...";
		else if (outOfAmmo)
			text = this.getName() + " - Out of ammo. Try /buyammo";
		else
			text = this.getName() + " - " + Integer.toString(ammo) + "/" + getMagazineAmmo() * CountItems(getMagazineType(), player);

		i.setDisplayName(text);
		item.setItemMeta(i);
	}

	private int CountItems(Material material, Player player)
	{
		int items = 0;

		Map<Integer, ? extends ItemStack> stack = player.getInventory().all(material);

		for (ItemStack s : stack.values())
		{
			if (s.getType() == material)
				items += s.getAmount();
		}

		return items;
	}

	public void Shoot(WeaponsPlugin plugin, WPlayer player)
	{
		if (ammo > 0)
		{
			shootTicks = 5;
		}
		else if (cooldown <= 0 && !reloading)
		{
			if (player.RemoveFromInventory(this.getMagazineType()))
			{
				reloading = true;
				outOfAmmo = false;
				cooldown = this.getReloadCooldown();

				player.getPlayer().getWorld().playSound(player.getPlayer().getEyeLocation(), this.getReloadSound(), 1, 1);
			}
			else
				outOfAmmo = true;
			UpdateGui(player.getPlayer());
		}
	}

	public void Tick(WeaponsPlugin plugin, WPlayer player)
	{
		cooldown--;
		shootTicks--;

		if (cooldown <= 0 && reloading)
		{
			reloading = false;
			ammo = this.getMagazineAmmo();
			UpdateGui(player.getPlayer());
		}

		if (shootTicks > 0 && (getCooldown() < 5 || shootTicks >= 4))
		{
			if (ammo > 0)
			{
				if (cooldown <= 0)
				{
					ammo--;
					cooldown = this.getCooldown();

					for (int i = 0; i < this.getBullets(); i++)
						projectiles.put(plugin.LaunchProjectile(player, this.getProjectile(), this.getProjectileSpeed(), this.getSpreading()), player.getPlayer().getEyeLocation());

					player.getPlayer().getWorld().playSound(player.getPlayer().getEyeLocation(), this.getShootSound(), 1, 1);
					UpdateGui(player.getPlayer());
				}
			}
		}
	}

	public void HandleProjectileHitEntity(EntityDamageByEntityEvent event, Projectile projectile)
	{
		if (projectiles.containsKey(projectile))
		{
			this.onProjectileHitEntity(event, projectile);
			projectiles.remove(projectile);
		}
	}
	
	public void HandleProjectileHitGround(ProjectileHitEvent event, Projectile projectile)
	{
		if (projectiles.containsKey(projectile))
		{
			this.onProjectileHitGround(event, projectile);
			projectiles.remove(projectile);
		}
	}
}
