package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_NightPoison extends Entity {

	GamePanel gp;
	
	public OBJ_NightPoison(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_consumable;
		name = "NightPoison";
		down1 = setup("/objects/night_poison", gp.tileSize, gp.tileSize);
		description = "[NightPoison]\nYou can sleep until\nnext morning.";
		price = 300;
		stackable = true;
	}
	
	@Override
	public void use(Entity entity) {
		
		gp.gameState = gp.sleepState;
		gp.playSE(9);
		gp.player.life = gp.player.maxLife;
		gp.player.mana = gp.player.maxMana;
		
	}
}
