package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Lantern extends Entity {

	public OBJ_Lantern(GamePanel gp) {
		super(gp);
		
		type = type_light;
		name = "Lantern";
		down1 = setup("/objects/lantern", gp.TILE_SIZE, gp.TILE_SIZE);
		description = "[Lantern]\nIlluminates your \nsurroundings.";
		price = 3;
		lightRadius = 350;
	}
	
}
