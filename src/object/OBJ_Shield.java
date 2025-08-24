package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield extends Entity {

	public OBJ_Shield(GamePanel gp) {
		super(gp);
		// TODO Auto-generated constructor stub
		type = type_shield;
		name = "Shield";
		down1 = setup("/objects/Shield",gp.TILE_SIZE,gp.TILE_SIZE);
		defenseValue =1;
		description = "[" + name + "]\nA Wooden Shield.";
		price = 2;
	}

}