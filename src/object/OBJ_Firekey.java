package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Firekey extends Entity{

	public OBJ_Firekey(GamePanel gp) {
		super(gp);
		this.gp = gp;
		// TODO Auto-generated constructor stub
		type = type_consumable;
		name = "Fire_trailKey";
		down1 = setup("/objects/fire_trailkey",gp.TILE_SIZE,gp.TILE_SIZE);
		description = "[Fire Trail Key]\nKey to open gate for\n Fire Iceland.";
	}
	
	public boolean use(Entity entity) {
		
		int objIndex = getDetected(entity,gp.obj, "GateFire");
		
		if(objIndex != 999) {
			gp.playSE(16);
			gp.obj[gp.currentMap][objIndex] = null;
			return true;
		}
		else {
			return false;
		}
	}
}
