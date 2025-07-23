package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword extends Entity {

	public OBJ_Sword(GamePanel gp) {
		super(gp);
		// TODO Auto-generated constructor stub
		
		name = "Diamond Sowrd";
		down1 = setup("/objects/Sword",gp.tileSize,gp.tileSize);
		attackValue = 2;
		description = "[" + name + "]\nA Diamond Sword.";
	}

}
