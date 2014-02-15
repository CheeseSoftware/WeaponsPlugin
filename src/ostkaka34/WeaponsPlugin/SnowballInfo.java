package ostkaka34.WeaponsPlugin;

import org.bukkit.entity.Snowball;

public class SnowballInfo{
	protected Snowball snowball;
	protected Weapon weapon;
	
	public SnowballInfo(Snowball snowball, Weapon weapon) {
		this.snowball = snowball;
		this.weapon = weapon;
	}
	
	public Snowball getSnowball() {
		return snowball;
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
}
