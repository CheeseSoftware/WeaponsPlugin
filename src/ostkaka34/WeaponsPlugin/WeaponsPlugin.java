package ostkaka34.WeaponsPlugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Egg;
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
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
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
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;
import ostkaka34.OstEconomyPlugin.IOstEconomy;

public class WeaponsPlugin
  extends JavaPlugin
  implements Listener
{
  public Map<Projectile, WPlayer> projectiles = new HashMap();
  public Map<Player, WPlayer> players = new HashMap();
  protected final WeaponsPlugin This = this;
  public Random random = new Random();
  public int tickId;
  public IOstEconomy economyPlugin = null;
  
  protected void LoadPlayer(Player player)
  {
    if (this.players.containsKey(player)) {
      this.players.remove(player);
    }
    Map<Material, Weapon> weapons = new HashMap();
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
  
  public void onEnable()
  {
    getServer().getPluginManager().registerEvents(this, this);
    
    this.tickId = getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
    {
      public void run()
      {
        Iterator<Map.Entry<Player, WPlayer>> it = WeaponsPlugin.this.players.entrySet().iterator();
        while (it.hasNext())
        {
          Map.Entry<Player, WPlayer> pairs = (Map.Entry)it.next();
          ((WPlayer)pairs.getValue()).Tick(WeaponsPlugin.this.This);
        }
      }
    }, 0L, 1L);
    
    Player[] players = getServer().getOnlinePlayers();
    for (int i = 0; i < players.length; i++) {
      LoadPlayer(players[i]);
    }
    Plugin[] plugins = getServer().getPluginManager().getPlugins();
    for (int i = 0; i < plugins.length; i++) {
      if ((plugins[i] instanceof IOstEconomy))
      {
        this.economyPlugin = ((IOstEconomy)plugins[i]);
        break;
      }
    }
    if (this.economyPlugin != null)
    {
      this.economyPlugin.RegisterShopItem("MAC-10", Material.WOOD_SPADE, 0, 1000, true, 1);
      this.economyPlugin.RegisterShopItem("MP5", Material.WOOD_HOE, 0, 8000, true, 1);
      this.economyPlugin.RegisterShopItem("P-90", Material.WOOD_PICKAXE, 0, 64000, true, 1);
      this.economyPlugin.RegisterShopItem("Magnum", Material.SHEARS, 0, 20000, true, 1);
      this.economyPlugin.RegisterShopItem("Weak Shotgun", Material.GOLD_BARDING, 0, 16000, true, 1);
      this.economyPlugin.RegisterShopItem("Shotgun", Material.IRON_BARDING, 0, 96000, true, 1);
      this.economyPlugin.RegisterShopItem("Double Barrel Shotgun", Material.DIAMOND_BARDING, 0, 136000, true, 1);
      this.economyPlugin.RegisterShopItem("Grenade Launcher", Material.IRON_HOE, 0, 200000, true, 1);
      
      this.economyPlugin.RegisterShopItem("Building Block", Material.WOOL, 0, 10000, false, 1);
      this.economyPlugin.RegisterShopItem("Web", Material.WEB, 0, 10000, false, 1);
      this.economyPlugin.RegisterShopItem("Ladder", Material.LADDER, 0, 10000, false, 1);
      this.economyPlugin.RegisterShopItem("Wood", Material.WOOD, 0, 10000, false, 1);
      this.economyPlugin.RegisterShopItem("Snow", Material.SNOW_BLOCK, 0, 20000, false, 1);
      this.economyPlugin.RegisterShopItem("Pumpkin", Material.PUMPKIN, 0, 60000, false, 1);
      
      this.economyPlugin.RegisterShopItem("Automatic rifle ammo", Material.STICK, 50, 0, false, 4);
      this.economyPlugin.RegisterShopItem("Magnum ammo", Material.COAL, 20, 0, false, 1);
      this.economyPlugin.RegisterShopItem("Shotgun ammo", Material.BLAZE_ROD, 60, 0, false, 1);
      this.economyPlugin.RegisterShopItem("Grenade Launcher Grenade", Material.SLIME_BALL, 500, 0, false, 1);
      this.economyPlugin.RegisterShopItem("Hand Grenade", Material.EGG, 400, 0, false, 1);
    }
  }
  
  public void onDisable()
  {
    getLogger().info("onDisable has been infoked!");
    
    getServer().getScheduler().cancelTask(this.tickId);
  }
  
  @EventHandler(priority=EventPriority.LOW)
  private void onWorldInitEvent(WorldInitEvent event) {}
  
  @EventHandler(priority=EventPriority.LOW)
  private void onPlayerJoin(PlayerJoinEvent event)
  {
    Player player = event.getPlayer();
    
    LoadPlayer(player);
    if (this.economyPlugin != null) {
      player.sendMessage("Money: " + this.economyPlugin.getMoney(player));
    }
  }
  
  @EventHandler(priority=EventPriority.LOW)
  private void onPlayerQuit(PlayerQuitEvent event)
  {
    Player player = event.getPlayer();
    if (this.players.containsKey(player)) {
      this.players.remove(player);
    }
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (this.players.containsKey(sender))
    {
      WPlayer player = (WPlayer)this.players.get(sender);
      if (cmd.getName().equalsIgnoreCase("buyammo"))
      {
        if (this.economyPlugin != null)
        {
          Weapon weapon = player.getCurrentWeapon();
          if (weapon != null) {
            this.economyPlugin.BuyShopItem(player.getPlayer(), this.economyPlugin.MaterialToName(weapon.getMagazineType()), 16);
          }
        }
        return true;
      }
    }
    return false;
  }
  
  @EventHandler
  private void onPlayerInteract(PlayerInteractEvent event)
  {
    if ((event.getAction() == Action.RIGHT_CLICK_AIR) || (event.getAction() == Action.RIGHT_CLICK_BLOCK))
    {
      if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
      {
        Material m = event.getClickedBlock().getType();
        if ((m == Material.LEVER) || (m == Material.IRON_DOOR) || (m == Material.WOODEN_DOOR) || (m == Material.TRAP_DOOR) || (m == Material.CHEST) || (m == Material.WOOD_BUTTON) || 
          (m == Material.STONE_BUTTON)) {
          return;
        }
      }
      Player player = event.getPlayer();
      if (this.players.containsKey(player))
      {
        WPlayer wplayer = (WPlayer)this.players.get(player);
        wplayer.Shoot(this);
        if (wplayer.getCurrentWeapon() != null)
        {
          Weapon w = wplayer.getCurrentWeapon();
          if (w.outOfAmmo) {
            w.UpdateGui(player);
          }
        }
      }
      if (player.getItemInHand() != null)
      {
        ItemStack item = player.getItemInHand();
        if (item.getType() == Material.EGG)
        {
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
  private void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event)
  {
    if ((event.getDamager() instanceof Projectile))
    {
      Entity damaged = event.getEntity();
      if (((damaged instanceof ItemFrame)) || ((damaged instanceof Painting)) || ((damaged instanceof Snowman)))
      {
        event.setCancelled(true);
        return;
      }
      Projectile projectile = (Projectile)event.getDamager();
      if (this.projectiles.containsKey(projectile))
      {
        ((WPlayer)this.projectiles.get(projectile)).HandleProjectileHitEntity(event, projectile);
        this.projectiles.remove(projectile);
      }
      else
      {
        event.setDamage(8);
      }
    }
  }
  
  @EventHandler
  private void onProjectileHit(ProjectileHitEvent event)
  {
    Projectile projectile = event.getEntity();
    if (this.projectiles.containsKey(projectile))
    {
      ((WPlayer)this.projectiles.get(projectile)).HandleProjectileHitGround(event, projectile);
      this.projectiles.remove(projectile);
    }
  }
  
  @EventHandler
  private void onPlayerPickupItem(PlayerPickupItemEvent event)
  {
    if (event.getItem().getItemStack().getType() == Material.EGG) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  private void onPlayerThrowEgg(PlayerEggThrowEvent event)
  {
    event.getEgg().remove();
  }
  
  @EventHandler
  private void onEntityDamage(EntityDamageEvent event)
  {
    if (event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler(priority=EventPriority.LOW)
  private void onEntityDeathEvent(EntityDeathEvent event)
  {
    LivingEntity entity = event.getEntity();
    Player killer = entity.getKiller();
    if (this.economyPlugin != null) {
      this.economyPlugin.GiveMoney(killer, entity.getMaxHealth());
    }
  }
  
  @EventHandler
  private void onPlayerInteractEntity(PlayerInteractEntityEvent event)
  {
    if (event.getRightClicked().getType() == EntityType.VILLAGER)
    {
      event.setCancelled(true);
      if (this.players.containsKey(event.getPlayer())) {
        ((WPlayer)this.players.get(event.getPlayer())).Shoot(this);
      }
    }
  }
  
  public Projectile LaunchProjectile(WPlayer source, Class<? extends Projectile> projectileType, double speed, double randomness)
  {
    Vector dir = source.getPlayer().getEyeLocation().getDirection();
    dir = new Vector(dir.getX() + randomness * (2.0D * this.random.nextDouble() - 1.0D), dir.getY() + randomness * (2.0D * this.random.nextDouble() - 1.0D), dir.getZ() + randomness * (
      2.0D * this.random.nextDouble() - 1.0D));
    dir = dir.multiply(speed);
    Projectile projectile = source.getPlayer().launchProjectile(projectileType);
    projectile.setVelocity(dir);
    this.projectiles.put(projectile, source);
    return projectile;
  }
}
