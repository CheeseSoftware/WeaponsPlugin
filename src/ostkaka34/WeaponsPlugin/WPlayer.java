package ostkaka34.WeaponsPlugin;

import java.util.Iterator;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WPlayer {
	Player player;
	long cooldown;
	ItemStack oldHand;
	
	Map<Material, Weapon> weapons;
	
	protected Weapon getCurrentWeapon() {
		if (weapons.containsKey(player.getItemInHand().getType()))
			return weapons.get(player.getItemInHand().getType());
		else return null;
	}
	
	public WPlayer(Player player, Map<Material, Weapon> weapons) {
		this.player = player;
		this.weapons = weapons;
		cooldown = 0;
		oldHand = player.getItemInHand();
	}
	
	/*public boolean Cooldown(long ticks) {
		if (cooldown <= 0 || oldHand.getType() != player.getItemInHand().getType())
		{
			cooldown = ticks;
			oldHand = player.getItemInHand();
			return true;
		}
		return false;
	}*/
	
	public void Tick() {
		Weapon weapon = getCurrentWeapon();
		if (weapon != null)
			weapon.Tick(this);
	}
	
	public Player getPlayer() {
		return player;
	}

	@SuppressWarnings("deprecation")
	public boolean RemoveFromInventory(Material material)
	{
		Inventory inventory = player.getInventory();
		
		if (inventory.contains(material, 1)) {
			int index = inventory.first(material);
			ItemStack item = inventory.getItem(index);
			
			if (item.getAmount() == 1)
			{
				inventory.setItem(index, null);
			}
			else
			{
				item.setAmount(item.getAmount()-1);
				//inventory.setItem(index, item);
			}
			player.updateInventory();
			
			return true;
		}
		return false;
	}

	public void Shoot(WeaponsPlugin plugin) {
		Weapon weapon = getCurrentWeapon();
		if (weapon != null)
			weapon.Shoot(plugin, this);
	}
	
	public void HandleSnowball(EntityDamageByEntityEvent event, Snowball snowball) {
        Iterator<Map.Entry<Material, Weapon>> it = weapons.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Material, Weapon> pairs = (Map.Entry<Material, Weapon>)it.next();
            pairs.getValue().HandleSnowball(event, snowball);
        }
	}

	
}
