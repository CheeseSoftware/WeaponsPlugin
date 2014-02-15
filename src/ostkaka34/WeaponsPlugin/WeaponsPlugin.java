package ostkaka34.WeaponsPlugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class WeaponsPlugin extends JavaPlugin implements Listener {
	
	public Map<Snowball, WPlayer> snowballs = new HashMap<Snowball, WPlayer>();
	public Map<Player, WPlayer> players = new HashMap<Player, WPlayer>();
	
	public Random random = new Random();
	public int tickId;
	
	@Override
	public void onEnable(){
		//getCommand("testcommand").setExecutor(this);
		getServer().getPluginManager().registerEvents(this, this);
		
		tickId = getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
		    public void run() {
		        Iterator<Map.Entry<Player, WPlayer>> it = players.entrySet().iterator();
		        while (it.hasNext()) {
		            Map.Entry<Player, WPlayer> pairs = (Map.Entry<Player, WPlayer>)it.next();
		            pairs.getValue().Tick();
		        }
		    }}, 0, 1);
	}
	
	@Override
	public void onDisable(){
		getLogger().info("onDisable has been infoked!");
		
		getServer().getScheduler().cancelTask(tickId);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		if (players.containsKey(player))
			players.remove(player);
		
		Map<Material, Weapon> weapons = new HashMap<Material, Weapon>();
		weapons.put(Material.WOOD_SPADE,	new WeaponA());
		weapons.put(Material.WOOD_HOE,		new WeaponB());
		weapons.put(Material.WOOD_PICKAXE,	new WeaponC());
		weapons.put(Material.SHEARS,		new WeaponShotgun());
		weapons.put(Material.IRON_BARDING,	new WeaponMagnum());
		
		players.put(player, new WPlayer(player, weapons));
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		if (players.containsKey(player))
			players.remove(player);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		/*if (sender instanceof Player) {
			//Player player = (Player)sender;
			
			if(cmd.getName().equalsIgnoreCase("testcommand")){ // If the player typed /basic then do the following...
				if (args.length > 0) {
					sender.sendMessage("Too many arguments!");
					return false;
				}
				
				sender.sendMessage("It works!");
				
				
				//player.getWorld().createExplosion(player.getLocation(), 10.f, true);
				//player.getWorld().generateTree(player.getLocation().add(0, -10, 0), TreeType.TREE);
				
				return true;
			}
		}*/
		return false; 
	}

	@EventHandler
	public void onPlayerInteractBlock(PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
			
	    Player player = event.getPlayer();
	    
	    if (!players.containsKey(player))
	    	return;
	    
	    WPlayer wplayer = players.get(player);
	    
//	    ItemStack hand = player.getItemInHand();
	    
	    wplayer.Shoot(this);
	    
	    /*if (hand.getType() == Material.BLAZE_ROD) {// == Material.BONE) {
	    		if (!wplayer.Cooldown(60))
	    			return;
	    	
		    	int strength = player.getFoodLevel();
		    	
		    	if (strength == 0)
		    		return;
		    	else if (strength > 10)
		    		strength = 10;
		    	
		    	player.setFoodLevel(player.getFoodLevel()-1);
		    	
		    	
		    	
		    	hand.setDurability((short)(hand.getDurability()-strength));
		    	//hand.notify();
		    	
		    	//player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 200, 1));
		    	//player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 10, 4));
		    	//player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 10, 4));
		    	player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20, 1));
		    	
		    	player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 600, 1));
			    // Creates a bolt of lightning at a given location. In this case, that location is where the player is looking.
			    // Can only create lightning up to 200 blocks away.
			    @SuppressWarnings("deprecation")
				Location loc = player.getTargetBlock(null, 200).getLocation();
			    player.getWorld().strikeLightningEffect(loc);
			    player.getWorld().strikeLightningEffect(player.getLocation());
			    
			    double strength2 = strength;
			    
			    List<Entity> nearby =  player.getWorld().getEntities();//.getNearbyEntities(strength2/2, strength2/2 ,strength2/2);
			    for (Entity tmp: nearby)
			    {
			    	if (tmp.getLocation().distance(loc) > strength)
			    		continue;
			    	
			       if (tmp instanceof Damageable)
			       {
			    	  double distance = tmp.getLocation().distance(player.getLocation());
			    	   
			    	  int fireTicks = (int)(1200/distance);
			    	  
			    	  if (fireTicks == 0)
			    		  fireTicks = 1;
			    	  else if (fireTicks > 1200)
			    		  fireTicks = 1200;
			    	  
			    	  
			    	  if (tmp instanceof Player)
			    	  {
			    		  player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, fireTicks/30 + 1, 1));
			    		  tmp.setFireTicks(fireTicks/30 + 1);
			    		  //player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20, 1));
			    	  }
			    	  else
			    	  {
			    		  tmp.setFireTicks(fireTicks);
			    		  ((Damageable) tmp).damage(strength2*24/distance);
			    	  }
			       }
			    }
			    player.getWorld().createExplosion(loc, (float)strength, true);
			    if (strength == 5)
			    	player.getWorld().createExplosion(loc, 3.f, true);
			    player.getWorld().setStorm(true);* /
			    
			    
	
		    }
	    	/ *else if (hand.getType() == Material.FISHING_ROD) {
	    		if (RemoveFromInventory(player, Material.ARROW) && wplayer.Cooldown(0))
		    	{
		    		player.launchProjectile(Arrow.class);
		    	}
	    	}
		    else if (hand.getType() == Material.CARROT_STICK) {
		    	//gun, rifle?
		    	if (RemoveFromInventory(player, Material.ARROW) && wplayer.Cooldown(0))
		    	{
		    		Arrow arrow = player.launchProjectile(Arrow.class);
		    		arrow.setVelocity(player.getEyeLocation().getDirection().multiply(2));
		    		arrow.setFireTicks(300);
		    	}
		    }
		    else if (hand.getType() == Material.SHEARS) {
		    	//shotgun
		    	if (wplayer.Cooldown(20))
	    		{
			    	if (RemoveFromInventory(player, Material.WOOD_BUTTON))
			    	{
			    		
					    	for (int i = 0; i < 16; i++)
					    		LaunchSnowball(player, 'D', 1, 0.25);
			    		
			    	}
	    		}
		    }
		    else if (hand.getType() == Material.WOOD_SPADE) {
		    	//semi-auto, weak gun
		    	if (wplayer.Cooldown(2))
		    	{
		    		if (RemoveFromInventory(player, Material.STICK))
		    			LaunchSnowball(player, 'A', 1, 0.0625);
		    	}
		    	
		    }
		    else if (hand.getType() == Material.WOOD_HOE) {
		    	//auto, weak gun
		    	if (RemoveFromInventory(player, Material.STICK) && wplayer.Cooldown(0))
		    		LaunchSnowball(player, 'B', 1.5, 0.042);
		    	
		    }
		    else if (hand.getType() == Material.WOOD_PICKAXE) {
		    	//auto, strong gun
		    	if (RemoveFromInventory(player, Material.STICK) && wplayer.Cooldown(0))
		    		LaunchSnowball(player, 'D', 2, 0.03125);
		    	
		    }*/
	}
	
	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player)
		{
			Player player = (Player)e.getDamager();
			
			ItemStack hand = player.getItemInHand();
			
			if (hand.getType() == Material.SIGN)
			{
				e.setDamage(24);
				if (hand.getAmount() == 1)
					player.setItemInHand(null);
				else
					hand.setAmount(hand.getAmount()-1);
			}
			else if (hand.getType() == Material.PAPER)
			{
				e.setDamage(6);
			}
		}
		else if (e.getDamager() instanceof Snowball)
		{
			Snowball snowball = (Snowball)e.getDamager();
			
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
	 
	
	public Snowball LaunchSnowball(WPlayer source, double speed, double randomness)
	{
		Vector dir = source.getPlayer().getEyeLocation().getDirection();
		dir = new Vector(
			dir.getX()+randomness*(2*random.nextDouble()-1.0),
			dir.getY()+randomness*(2*random.nextDouble()-1.0),
			dir.getZ()+randomness*(2*random.nextDouble()-1.0));
		dir = dir.multiply(speed);
		Snowball snowball = source.getPlayer().launchProjectile(Snowball.class);
		snowball.setVelocity(dir);//, new Vector(
						//speed*(dir.getX()+randomness*(2*random.nextDouble()-1.0)),
						//speed*(dir.getY()+randomness*(2*random.nextDouble()-1.0)),
						//speed*(dir.getZ()+randomness*(2*random.nextDouble()-1.0))));
		
		snowballs.put(snowball, source);
		return snowball;
	}
	

	
	
}