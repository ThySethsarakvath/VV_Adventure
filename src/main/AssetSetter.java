package main;

import object.OBJ_Door;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
		// This is where you can set position of the object
		// for example here door located at (30, 21)
		gp.obj[0] = new OBJ_Door(gp);
		gp.obj[0].worldX = 30 * gp.tileSize;
		gp.obj[0].worldY = 21 * gp.tileSize;
		
	}
	
}
