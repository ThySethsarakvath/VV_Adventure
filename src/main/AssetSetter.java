package main;

import entity.Npc;
import object.OBJ_Door;
import object.OBJ_speedBoot;

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
		gp.obj[0].worldY = 24 * gp.tileSize;
		
		// Set speed boot to the world
		gp.obj[1] = new OBJ_speedBoot(gp);
		gp.obj[1].worldX = 32 * gp.tileSize;
		gp.obj[1].worldY = 21 * gp.tileSize;
		
	}
	
	public void setNpc() {
		gp.npc[0] = new Npc(gp);
		gp.npc[0].worldX = gp.tileSize*21;
		gp.npc[0].worldY = gp.tileSize*21;
	}
	
}
