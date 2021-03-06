package com.github.cheesesoftware.WeaponsPlugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
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
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import com.github.cheesesoftware.OstEconomyPlugin.IOstEconomy;
import com.github.cheesesoftware.OstEconomyPlugin.ShopItem;

public class WeaponsPlugin extends JavaPlugin implements Listener {
    public Map<Projectile, WPlayer> projectiles = new HashMap<Projectile, WPlayer>();
    public Map<Player, WPlayer> players = new HashMap<Player, WPlayer>();
    protected final WeaponsPlugin This = this;
    public Random random = new Random();
    public int tickId;
    public IOstEconomy economyPlugin = null;

    protected void LoadPlayer(Player player) {
	if (this.players.containsKey(player)) {
	    this.players.remove(player);
	}
	Map<Material, Weapon> weapons = new HashMap<Material, Weapon>();
	weapons.put(Material.STONE_SPADE, new Weapon3dPrintedPistol());
	weapons.put(Material.WOOD_SPADE, new WeaponMAC10());
	weapons.put(Material.WOOD_HOE, new WeaponMP5());
	weapons.put(Material.WOOD_PICKAXE, new WeaponP90());
	weapons.put(Material.GOLD_BARDING, new WeaponWeakShotgun());
	weapons.put(Material.IRON_BARDING, new WeaponShotgun());
	weapons.put(Material.DIAMOND_BARDING, new WeaponDoubleBarrelShotgun());
	weapons.put(Material.SHEARS, new WeaponMagnum());
	weapons.put(Material.IRON_HOE, new WeaponGrenadeLauncher());
	this.players.put(player, new WPlayer(player, weapons));
    }

    public void onEnable() {
	getServer().getPluginManager().registerEvents(this, this);

	this.tickId = getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
	    public void run() {
		Iterator<Map.Entry<Player, WPlayer>> it = WeaponsPlugin.this.players.entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry<Player, WPlayer> pairs = (Entry<Player, WPlayer>) it.next();
		    ((WPlayer) pairs.getValue()).Tick(WeaponsPlugin.this.This);
		}
	    }
	}, 0L, 1L);

	Collection<? extends Player> players = getServer().getOnlinePlayers();
	for (Player p : players) {
	    LoadPlayer(p);
	}
	Plugin[] plugins = getServer().getPluginManager().getPlugins();
	for (int i = 0; i < plugins.length; i++) {
	    if ((plugins[i] instanceof IOstEconomy)) {
		this.economyPlugin = ((IOstEconomy) plugins[i]);
		break;
	    }
	}
	if (this.economyPlugin != null) {
	    this.economyPlugin.RegisterShopItem(new ShopItem("MAC-10", Material.WOOD_SPADE, 0, 5, true, 1));
	    this.economyPlugin.RegisterShopItem(new ShopItem("MP5", Material.WOOD_HOE, 0, 80, true, 1));
	    this.economyPlugin.RegisterShopItem(new ShopItem("P-90", Material.WOOD_PICKAXE, 0, 480, true, 1));
	    this.economyPlugin.RegisterShopItem(new ShopItem("Magnum", Material.SHEARS, 0, 100, true, 1));
	    this.economyPlugin.RegisterShopItem(new ShopItem("Weak Shotgun", Material.GOLD_BARDING, 0, 80, true, 1));
	    this.economyPlugin.RegisterShopItem(new ShopItem("Shotgun", Material.IRON_BARDING, 0, 480, true, 1));
	    this.economyPlugin.RegisterShopItem(new ShopItem("Double Barrel Shotgun", Material.DIAMOND_BARDING, 0, 860, true, 1));
	    this.economyPlugin.RegisterShopItem(new ShopItem("Grenade Launcher", Material.IRON_HOE, 0, 1000, true, 1));

	    this.economyPlugin.RegisterShopItem(new ShopItem("Building Block", Material.WOOL, 0, 20, false, 1));
	    this.economyPlugin.RegisterShopItem(new ShopItem("Web", Material.WEB, 0, 30, false, 1));
	    this.economyPlugin.RegisterShopItem(new ShopItem("Ladder", Material.LADDER, 0, 20, false, 1));
	    this.economyPlugin.RegisterShopItem(new ShopItem("Wood", Material.WOOD, 0, 10, false, 1));
	    this.economyPlugin.RegisterShopItem(new ShopItem("Snow", Material.SNOW_BLOCK, 0, 20, false, 1));
	    this.economyPlugin.RegisterShopItem(new ShopItem("Pumpkin", Material.PUMPKIN, 0, 60, false, 1));

	    this.economyPlugin.RegisterShopItem(new ShopItem("Automatic rifle ammo", Material.STICK, 200, 0, false, 4));
	    this.economyPlugin.RegisterShopItem(new ShopItem("Magnum ammo", Material.COAL, 50, 0, false, 1));
	    this.economyPlugin.RegisterShopItem(new ShopItem("Shotgun ammo", Material.BLAZE_ROD, 150, 0, false, 1));
	    this.economyPlugin.RegisterShopItem(new ShopItem("Grenade Launcher Grenade", Material.SLIME_BALL, 300, 0, false, 1));
	    this.economyPlugin.RegisterShopItem(new ShopItem("Hand Grenade", Material.EGG, 200, 0, false, 1));
	}
    }

    public void onDisable() {
	getLogger().info("onDisable has been infoked!");

	getServer().getScheduler().cancelTask(this.tickId);
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onWorldInitEvent(WorldInitEvent event) {
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onPlayerJoin(PlayerJoinEvent event) {
	Player player = event.getPlayer();

	LoadPlayer(player);
	if (this.economyPlugin != null) {
	    player.sendMessage("Money: " + this.economyPlugin.getMoney(player));
	}
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onPlayerQuit(PlayerQuitEvent event) {
	Player player = event.getPlayer();
	if (this.players.containsKey(player)) {
	    this.players.remove(player);
	}
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	if (this.players.containsKey(sender)) {
	    WPlayer player = (WPlayer) this.players.get(sender);
	    if (cmd.getName().equalsIgnoreCase("buyammo")) {
		if (this.economyPlugin != null) {
		    Weapon weapon = player.getCurrentWeapon();
		    if (weapon != null) {
			this.economyPlugin.BuyShopItem(player.getPlayer(), this.economyPlugin.MaterialToShopItem(weapon.getMagazineType()), 16);
		    }
		}
		return true;
	    }
	}
	return false;
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
	if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
	    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
		Material m = event.getClickedBlock().getType();
		if ((m == Material.LEVER) || (m == Material.IRON_DOOR) || (m == Material.WOODEN_DOOR) || (m == Material.TRAP_DOOR) || (m == Material.CHEST) || (m == Material.WOOD_BUTTON)
			|| (m == Material.STONE_BUTTON)) {
		    return;
		}
	    }
	    Player player = event.getPlayer();
	    if (this.players.containsKey(player)) {
		WPlayer wplayer = (WPlayer) this.players.get(player);
		wplayer.Shoot(this);
		if (wplayer.getCurrentWeapon() != null) {
		    Weapon w = wplayer.getCurrentWeapon();
		    if (w.outOfAmmo) {
			w.UpdateGui(player);
		    }
		}
	    }
	    if (player.getItemInHand() != null) {
		ItemStack item = player.getItemInHand();
		if (item.getType() == Material.EGG) {
		    if (item.getAmount() <= 1) {
			player.getInventory().remove(item);
		    } else {
			item.setAmount(item.getAmount() - 1);
		    }
		    event.setCancelled(true);
		    Item grenade = player.getWorld().dropItem(player.getEyeLocation(), new ItemStack(Material.EGG));
		    grenade.setVelocity(player.getLocation().getDirection().multiply(1.2D));
		    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new RunnableExplodeProjectileLater(grenade, player, 3), 100L);
		}
	    }
	}
    }

    @EventHandler
    private void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
	if ((event.getDamager() instanceof Projectile)) {
	    Entity damaged = event.getEntity();
	    if (((damaged instanceof ItemFrame)) || ((damaged instanceof Painting)) || ((damaged instanceof Snowman))) {
		event.setCancelled(true);
		return;
	    }
	    Projectile projectile = (Projectile) event.getDamager();
	    if (this.projectiles.containsKey(projectile)) {
		((WPlayer) this.projectiles.get(projectile)).HandleProjectileHitEntity(event, projectile);
		this.projectiles.remove(projectile);
	    } else {
		event.setDamage(8);
	    }
	}
    }

    @EventHandler
    private void onProjectileHit(ProjectileHitEvent event) {
	Projectile projectile = event.getEntity();
	if (this.projectiles.containsKey(projectile)) {
	    ((WPlayer) this.projectiles.get(projectile)).HandleProjectileHitGround(event, projectile);
	    this.projectiles.remove(projectile);
	}
    }

    @EventHandler
    private void onPlayerPickupItem(PlayerPickupItemEvent event) {
	if (event.getItem().getItemStack().getType() == Material.EGG) {
	    event.setCancelled(true);
	}
    }

    @EventHandler
    private void onPlayerThrowEgg(PlayerEggThrowEvent event) {
	event.getEgg().remove();
    }

    @EventHandler
    private void onEntityDamage(EntityDamageEvent event) {
	if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
	    event.setCancelled(true);
	}
    }

    @EventHandler(priority = EventPriority.LOW)
    private void onEntityDeathEvent(EntityDeathEvent event) {
	LivingEntity entity = event.getEntity();
	Player killer = entity.getKiller();
	if (this.economyPlugin != null) {
	    this.economyPlugin.GiveMoney(killer, (long) (double) entity.getMaxHealth());
	}
    }

    @EventHandler
    private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
	if (event.getRightClicked().getType() == EntityType.VILLAGER) {
	    event.setCancelled(true);
	    if (this.players.containsKey(event.getPlayer())) {
		((WPlayer) this.players.get(event.getPlayer())).Shoot(this);
	    }
	}
    }

    public Projectile LaunchProjectile(WPlayer source, Class<? extends Projectile> projectileType, double speed, double randomness) {
	Vector dir = source.getPlayer().getEyeLocation().getDirection();
	dir = new Vector(dir.getX() + randomness * (2.0D * this.random.nextDouble() - 1.0D), dir.getY() + randomness * (2.0D * this.random.nextDouble() - 1.0D), dir.getZ() + randomness
		* (2.0D * this.random.nextDouble() - 1.0D));
	dir = dir.multiply(speed);
	Projectile projectile = source.getPlayer().launchProjectile(projectileType);
	projectile.setVelocity(dir);
	this.projectiles.put(projectile, source);
	return projectile;
    }
}
