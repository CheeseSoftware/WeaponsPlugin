package ostkaka34.WeaponsPlugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import ostkaka34.OstEconomyPlugin.IOstEconomy;

public class WeaponsPlugin extends JavaPlugin implements Listener
{

	public Map<Projectile, WPlayer> projectiles = new HashMap<Projectile, WPlayer>();
	public Map<Player, WPlayer> players = new HashMap<Player, WPlayer>();

	protected final WeaponsPlugin This = this;

	public Random random = new Random();
	public int tickId;

	public IOstEconomy economyPlugin = null;

	protected void LoadPlayer(Player player)
	{
		if (players.containsKey(player))
			players.remove(player);

		Map<Material, Weapon> weapons = new HashMap<Material, Weapon>();
		weapons.put(Material.WOOD_SPADE, new WeaponA()); // MAC-10
		weapons.put(Material.WOOD_HOE, new WeaponB()); // MP5
		weapons.put(Material.WOOD_PICKAXE, new WeaponC()); // P-90
		weapons.put(Material.GOLD_BARDING, new WeaponWeakShotgun());
		weapons.put(Material.IRON_BARDING, new WeaponShotgun());
		weapons.put(Material.DIAMOND_BARDING, new WeaponDoubleBarrelShotgun());
		weapons.put(Material.SHEARS, new WeaponMagnum());
		weapons.put(Material.DIAMOND_SWORD, new WeaponGrenadeLauncher());
		players.put(player, new WPlayer(player, weapons));
	}

	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);

		tickId = getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{
			public void run()
			{
				Iterator<Map.Entry<Player, WPlayer>> it = players.entrySet().iterator();
				while (it.hasNext())
				{
					Map.Entry<Player, WPlayer> pairs = (Map.Entry<Player, WPlayer>) it.next();
					pairs.getValue().Tick(This);
				}
			}
		}, 0, 1);

		Player[] players = getServer().getOnlinePlayers();

		for (int i = 0; i < players.length; i++)
		{
			LoadPlayer(players[i]);
		}

		Plugin[] plugins = getServer().getPluginManager().getPlugins();

		for (int i = 0; i < plugins.length; i++)
		{
			if (plugins[i] instanceof IOstEconomy)
			{
				economyPlugin = (IOstEconomy) plugins[i];
				break;
			}
		}

		if (economyPlugin != null)
		{
			economyPlugin.RegisterShopItem("MP5", Material.WOOD_HOE, 0, 200, true, 1);
			economyPlugin.RegisterShopItem("P-90", Material.WOOD_PICKAXE, 0, 16000, true, 1);
			economyPlugin.RegisterShopItem("Magnum", Material.SHEARS, 0, 10000, true, 1);
			economyPlugin.RegisterShopItem("Weak Shotgun", Material.GOLD_BARDING, 0, 4000, true, 1);
			economyPlugin.RegisterShopItem("Shotgun", Material.IRON_BARDING, 0, 36000, true, 1);
			economyPlugin.RegisterShopItem("Double Barrel Shotgun", Material.DIAMOND_BARDING, 0, 68000, true, 1);
			economyPlugin.RegisterShopItem("Grenade Launcher", Material.DIAMOND_SWORD, 0, 100000, true, 1);

			economyPlugin.RegisterShopItem("wool", Material.WOOL, 0, 100000, false, 1);

			economyPlugin.RegisterShopItem("Automatic rifle ammo", Material.STICK, 50, 0, false, 16);
			economyPlugin.RegisterShopItem("Magnum ammo", Material.COAL, 20, 0, false, 8);
			economyPlugin.RegisterShopItem("Shotgun ammo", Material.GOLD_NUGGET, 60, 0, false, 12);
		}
	}

	@Override
	public void onDisable()
	{
		getLogger().info("onDisable has been infoked!");

		getServer().getScheduler().cancelTask(tickId);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onWorldInitEvent(WorldInitEvent event)
	{
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();

		LoadPlayer(player);

		if (economyPlugin != null)
			player.sendMessage("Money: " + economyPlugin.getMoney(player));
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();

		if (players.containsKey(player))
			players.remove(player);
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (players.containsKey(sender))
		{
			WPlayer player = players.get(sender);

			if (cmd.getName().equalsIgnoreCase("buyammo"))
			{
				if (economyPlugin != null)
				{
					Weapon weapon = player.getCurrentWeapon();
					if (weapon != null)
					{
						economyPlugin.BuyShopItem(player.getPlayer(), ((IOstEconomy) economyPlugin).MaterialToName(weapon.getMagazineType()), 16);
					}
				}
				return true;
			}
		}
		return false;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
			{
				Material m = event.getClickedBlock().getType();
				if (m == Material.LEVER || m == Material.IRON_DOOR || m == Material.WOODEN_DOOR || m == Material.TRAP_DOOR || m == Material.CHEST || m == Material.WOOD_BUTTON || m == Material.STONE_BUTTON)
					return;
			}

			Player player = event.getPlayer();
			if (players.containsKey(player))
			{
				WPlayer wplayer = players.get(player);
				wplayer.Shoot(this);
			}
		}
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event)
	{
		if (event.getDamager() instanceof Projectile)
		{
			Entity damaged = event.getEntity();
			if (damaged instanceof ItemFrame || damaged instanceof Painting || damaged instanceof Snowman)
			{
				event.setCancelled(true);
				return;
			}

			Projectile projectile = (Projectile) event.getDamager();
			if (projectiles.containsKey(projectile))
			{
				projectiles.get(projectile).HandleProjectileHitEntity(event, projectile);
				projectiles.remove(projectile);
			}
			else
				event.setDamage(8);
		}
	}

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event)
	{
		Projectile projectile = event.getEntity();
		if (projectiles.containsKey(projectile))
		{
			projectiles.get(projectile).HandleProjectileHitGround(event, projectile);
			projectiles.remove(projectile);
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	void onEntityDeathEvent(EntityDeathEvent event)
	{
		LivingEntity entity = event.getEntity();
		Player killer = entity.getKiller();

		if (economyPlugin != null)
		{
			economyPlugin.GiveMoney(killer, (long) entity.getMaxHealth());
		}
	}

	public Projectile LaunchProjectile(WPlayer source, Class<? extends Projectile> projectileType, double speed, double randomness)
	{
		Vector dir = source.getPlayer().getEyeLocation().getDirection();
		dir = new Vector(dir.getX() + randomness * (2 * random.nextDouble() - 1.0), dir.getY() + randomness * (2 * random.nextDouble() - 1.0), dir.getZ() + randomness * (2 * random.nextDouble() - 1.0));
		dir = dir.multiply(speed);
		Projectile projectile = source.getPlayer().launchProjectile(projectileType);
		projectile.setVelocity(dir);
		projectiles.put(projectile, source);
		return projectile;
	}

}