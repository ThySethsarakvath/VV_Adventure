package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield extends Entity {

	public OBJ_Shield(GamePanel gp) {
		super(gp);
		// TODO Auto-generated constructor stub
		
		name = "Shield";
		down1 = setup("/objects/Shield",gp.tileSize,gp.tileSize);
		defenseValue =1;
		description = "[" + name + "]\nA Wooden Shield.";
	}

}