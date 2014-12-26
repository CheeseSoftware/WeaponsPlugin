package com.github.cheesesoftware.WeaponsPlugin;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class WPlayer
{
  Player player;
  long cooldown;
  ItemStack oldHand;
  Map<Material, Weapon> weapons;
  
  public Weapon getCurrentWeapon()
  {
    if (this.weapons.containsKey(this.player.getItemInHand().getType())) {
      return (Weapon)this.weapons.get(this.player.getItemInHand().getType());
    }
    return null;
  }
  
  public WPlayer(Player player, Map<Material, Weapon> weapons)
  {
    this.player = player;
    this.weapons = weapons;
    this.cooldown = 0L;
    this.oldHand = player.getItemInHand();
  }
  
  public void Tick(WeaponsPlugin plugin)
  {
    Weapon weapon = getCurrentWeapon();
    if (weapon != null) {
      weapon.Tick(plugin, this);
    }
  }
  
  public Player getPlayer()
  {
    return this.player;
  }
  
public boolean RemoveFromInventory(Material material)
  {
    if (material == Material.AIR) {
      return true;
    }
    Inventory inventory = this.player.getInventory();
    if (inventory.contains(material, 1))
    {
      int index = inventory.first(material);
      ItemStack item = inventory.getItem(index);
      if (item.getAmount() == 1) {
        inventory.setItem(index, null);
      } else {
        item.setAmount(item.getAmount() - 1);
      }
      this.player.updateInventory();
      
      return true;
    }
    return false;
  }
  
  public void Shoot(WeaponsPlugin plugin)
  {
    Weapon weapon = getCurrentWeapon();
    if (weapon != null) {
      weapon.Shoot(plugin, this);
    }
  }
  
  public void HandleProjectileHitEntity(EntityDamageByEntityEvent event, Projectile projectile)
  {
    Iterator<Map.Entry<Material, Weapon>> it = this.weapons.entrySet().iterator();
    while (it.hasNext())
    {
      Map.Entry<Material, Weapon> pairs = (Entry<Material, Weapon>)it.next();
      ((Weapon)pairs.getValue()).HandleProjectileHitEntity(event, projectile);
    }
  }
  
  public void HandleProjectileHitGround(ProjectileHitEvent event, Projectile projectile)
  {
    Iterator<Map.Entry<Material, Weapon>> it = this.weapons.entrySet().iterator();
    while (it.hasNext())
    {
      Map.Entry<Material, Weapon> pairs = (Entry<Material, Weapon>)it.next();
      ((Weapon)pairs.getValue()).HandleProjectileHitGround(event, projectile);
    }
  }
}
