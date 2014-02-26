package ostkaka34.WeaponsPlugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import ostkaka34.OstEconomyPlugin.IOstEconomy;

public class WeaponsPlugin extends JavaPlugin implements Listener
{

	public Map<Snowball, WPlayer> snowballs = new HashMap<Snowball, WPlayer>();
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
			economyPlugin.RegisterXPShopItem(Material.WOOD_HOE, 200, "MP5", true);
			economyPlugin.RegisterXPShopItem(Material.WOOD_PICKAXE, 16000, "P-90", true);
			economyPlugin.RegisterXPShopItem(Material.SHEARS, 10000, "Magnum", true);
			economyPlugin.RegisterXPShopItem(Material.GOLD_BARDING, 4000, "Weak_Shotgun", true);
			economyPlugin.RegisterXPShopItem(Material.IRON_BARDING, 36000, "Shotgun", true);
			economyPlugin.RegisterXPShopItem(Material.DIAMOND_BARDING, 68000, "Double_Barrel_Shotgun", true);

			economyPlugin.RegisterXPShopItem(Material.WOOL, 100000, "wool", false);

			economyPlugin.RegisterShopItem(Material.STICK, 50, "stickammo", false);
			economyPlugin.RegisterShopItem(Material.COAL, 20, "magnumammo", false);
			economyPlugin.RegisterShopItem(Material.GOLD_NUGGET, 60, "shotgunammo", false);
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

	@EventHandler(priority = EventPriority.LOW)
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
						economyPlugin.BuyShopItem(player.getPlayer(), weapon.getMagazineType(), 16);
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
			if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
			{
				Material m = event.getClickedBlock().getType();
				if(m == Material.LEVER || m == Material.IRON_DOOR || m == Material.WOODEN_DOOR || m == Material.TRAP_DOOR || m == Material.CHEST)
				{
					event.setCancelled(true);
					return;
				}
			}
			Player player = event.getPlayer();

			if (!players.containsKey(player))
				return;

			WPlayer wplayer = players.get(player);

			wplayer.Shoot(this);
		}
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e)
	{
		if (e.isCancelled())
			return;
		
		if(e.getEntity() instanceof ItemFrame || e.getEntity() instanceof Painting)
		{
			e.setCancelled(true);
			return;
		}

		if (e.getDamager() instanceof Player)
		{
			Player player = (Player) e.getDamager();

			ItemStack hand = player.getItemInHand();

			if (hand.getType() == Material.SIGN)
			{
				e.setDamage(24);
				if (hand.getAmount() == 1)
					player.setItemInHand(null);
				else
					hand.setAmount(hand.getAmount() - 1);
			}
			else if (hand.getType() == Material.PAPER)
			{
				e.setDamage(6);
			}
		}
		else if (e.getDamager() instanceof Snowball)
		{
			Snowball snowball = (Snowball) e.getDamager();

			if (e.getEntity() instanceof Snowman)
			{
				e.setCancelled(true);
				return;
			}

			if (snowballs.containsKey(snowball))
			{
				snowballs.get(snowball).HandleSnowball(e, snowball);
				snowballs.remove(snowball);
			}
			else
			{
				e.setDamage(8);
			}
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

	public Snowball LaunchSnowball(WPlayer source, double speed, double randomness)
	{
		Vector dir = source.getPlayer().getEyeLocation().getDirection();
		dir = new Vector(dir.getX() + randomness * (2 * random.nextDouble() - 1.0), dir.getY() + randomness * (2 * random.nextDouble() - 1.0), dir.getZ() + randomness
				* (2 * random.nextDouble() - 1.0));
		dir = dir.multiply(speed);
		Snowball snowball = source.getPlayer().launchProjectile(Snowball.class);
		snowball.setVelocity(dir);
		snowballs.put(snowball, source);
		return snowball;
	}

}