package main;

import entity.Npc;
import entity.Wander;
import monster.Ice_Golem;
import monster.Skeleton;
import monster.Skeleton_Wither;
import monster.Zombie;
import object.OBJ_Axe;
import object.OBJ_Chest;
import object.OBJ_Emerald;
import object.OBJ_Firecharge;
import object.OBJ_GateFire;
import object.OBJ_GateIce;
import object.OBJ_Icekey;
import object.OBJ_Lantern;
import object.OBJ_Tent;
import object.OBJ_Portal;
import object.OBJ_Shield;
import object.OBJ_Sword;
import object.OBJ_WoodenSword;
import object.OBJ_healingP;
import object.OBJ_speedBoot;
import tile_interactive.IT_DryTree;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
		int mapNum = 0;
		int i = 0 ;
		
		// Over world
		gp.obj[mapNum][i] = new OBJ_Axe(gp);
		gp.obj[mapNum][i].worldX = 52 * gp.TILE_SIZE;
		gp.obj[mapNum][i].worldY = 64 * gp.TILE_SIZE;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Lantern(gp);
		gp.obj[mapNum][i].worldX = 34 * gp.TILE_SIZE;
		gp.obj[mapNum][i].worldY = 61 * gp.TILE_SIZE;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Tent(gp);
		gp.obj[mapNum][i].worldX = 34 * gp.TILE_SIZE;
		gp.obj[mapNum][i].worldY = 63 * gp.TILE_SIZE;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Tent(gp);
		gp.obj[mapNum][i].worldX = 33 * gp.TILE_SIZE;
		gp.obj[mapNum][i].worldY = 63 * gp.TILE_SIZE;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_WoodenSword(gp);
		gp.obj[mapNum][i].worldX = 30 * gp.TILE_SIZE;
		gp.obj[mapNum][i].worldY = 61 * gp.TILE_SIZE;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Portal(gp);
		gp.obj[mapNum][i].worldX = 34 * gp.TILE_SIZE;
		gp.obj[mapNum][i].worldY = 91 * gp.TILE_SIZE;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Portal(gp);
		gp.obj[mapNum][i].worldX = 152 * gp.TILE_SIZE;
		gp.obj[mapNum][i].worldY = 117 * gp.TILE_SIZE;
		i++;
		
		// Final Boss Portal
		gp.obj[mapNum][i] = new OBJ_Portal(gp);
		gp.obj[mapNum][i].worldX = 89 * gp.TILE_SIZE;
		gp.obj[mapNum][i].worldY = 96 * gp.TILE_SIZE;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_GateIce(gp);
		gp.obj[mapNum][i].worldX = 36 * gp.TILE_SIZE;
		gp.obj[mapNum][i].worldY = 91 * gp.TILE_SIZE;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_GateFire(gp);
		gp.obj[mapNum][i].worldX = 150 * gp.TILE_SIZE;
		gp.obj[mapNum][i].worldY = 117 * gp.TILE_SIZE;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Chest(gp,new OBJ_Icekey(gp));
		gp.obj[mapNum][i].worldX = 52 * gp.TILE_SIZE;
		gp.obj[mapNum][i].worldY = 62 * gp.TILE_SIZE;
		i++;
		
		// Ice Map
		mapNum = 2;
		i=0;
		
		gp.obj[mapNum][i] = new OBJ_Portal(gp);
		gp.obj[mapNum][i].worldX = 82 * gp.TILE_SIZE;
		gp.obj[mapNum][i].worldY = 50 * gp.TILE_SIZE;
		i++;
		
		// Nether Map
		mapNum = 3;
		i=0;
		gp.obj[mapNum][i] = new OBJ_Portal(gp);
		gp.obj[mapNum][i].worldX = 16 * gp.TILE_SIZE;
		gp.obj[mapNum][i].worldY = 50 * gp.TILE_SIZE;
		i++;
		
		// Final Map
		mapNum = 4;
		i= 0;
		gp.obj[mapNum][i] = new OBJ_Portal(gp);
		gp.obj[mapNum][i].worldX = 13 * gp.TILE_SIZE;
		gp.obj[mapNum][i].worldY = 97 * gp.TILE_SIZE;
		i++;
	}
	
	public void setNpc() {
		
		int mapNum = 0;
		int i = 0;
		
		gp.npc[mapNum][i] = new Npc(gp);
		gp.npc[mapNum][i].worldX = gp.TILE_SIZE*49;
		gp.npc[mapNum][i].worldY = gp.TILE_SIZE*63;
		i++;
		
		mapNum =1;
		i=0;
		gp.npc[mapNum][i] = new Wander(gp);
		gp.npc[mapNum][i].worldX = gp.TILE_SIZE*30;
		gp.npc[mapNum][i].worldY = gp.TILE_SIZE*23;
		i++;
	}
	
	public void setMonster() {
		
		int mapNum = 0;
		int i =0;
		
<<<<<<< Updated upstream
//		gp.monster[mapNum][i] = new Zombie(gp);
//		gp.monster[mapNum][i].worldX = gp.tileSize*42;
//		gp.monster[mapNum][i].worldY = gp.tileSize*65;
//		i++;
//		
//		gp.monster[mapNum][i] = new Zombie(gp);
//		gp.monster[mapNum][i].worldX = gp.tileSize*43;
//		gp.monster[mapNum][i].worldY = gp.tileSize*65;
//		i++;
//		
//		gp.monster[mapNum][i] = new Zombie(gp);
//		gp.monster[mapNum][i].worldX = gp.tileSize*44;
//		gp.monster[mapNum][i].worldY = gp.tileSize*65;
//		i++;
//		
//		gp.monster[mapNum][i] = new Skeleton(gp);
//		gp.monster[mapNum][i].worldX = gp.tileSize*42;
//		gp.monster[mapNum][i].worldY = gp.tileSize*66;
//		i++;
//		
//		gp.monster[mapNum][i] = new Skeleton(gp);
//		gp.monster[mapNum][i].worldX = gp.tileSize*43;
//		gp.monster[mapNum][i].worldY = gp.tileSize*66;
//		i++;
//		
//		gp.monster[mapNum][i] = new Skeleton(gp);
//		gp.monster[mapNum][i].worldX = gp.tileSize*44;
//		gp.monster[mapNum][i].worldY = gp.tileSize*66;
//		i++;
		
		gp.monster[mapNum][i] = new Skeleton_Wither(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*45;
		gp.monster[mapNum][i].worldY = gp.tileSize*67;
		i++;
		
		mapNum = 4;
		i = 0;
		gp.monster[mapNum][i] = new Ice_Golem(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*36;
		gp.monster[mapNum][i].worldY = gp.tileSize*57;
		i++;
		if(Progress.GolemDefeated) {
			
		}
=======
		gp.monster[mapNum][i] = new Zombie(gp);
		gp.monster[mapNum][i].worldX = gp.TILE_SIZE*42;
		gp.monster[mapNum][i].worldY = gp.TILE_SIZE*65;
		i++;
		
		gp.monster[mapNum][i] = new Zombie(gp);
		gp.monster[mapNum][i].worldX = gp.TILE_SIZE*43;
		gp.monster[mapNum][i].worldY = gp.TILE_SIZE*65;
		i++;
		
		gp.monster[mapNum][i] = new Zombie(gp);
		gp.monster[mapNum][i].worldX = gp.TILE_SIZE*44;
		gp.monster[mapNum][i].worldY = gp.TILE_SIZE*65;
		i++;
		
		gp.monster[mapNum][i] = new Skeleton(gp);
		gp.monster[mapNum][i].worldX = gp.TILE_SIZE*42;
		gp.monster[mapNum][i].worldY = gp.TILE_SIZE*66;
		i++;
		
		gp.monster[mapNum][i] = new Skeleton(gp);
		gp.monster[mapNum][i].worldX = gp.TILE_SIZE*43;
		gp.monster[mapNum][i].worldY = gp.TILE_SIZE*66;
		i++;
		
		gp.monster[mapNum][i] = new Skeleton(gp);
		gp.monster[mapNum][i].worldX = gp.TILE_SIZE*44;
		gp.monster[mapNum][i].worldY = gp.TILE_SIZE*66;
		i++;
		
>>>>>>> Stashed changes
	}
	
	public void setInteractiveTile() {
		
		int mapNum = 0;
		int i = 0;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 43, 54);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 44, 54);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 45, 54);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 46, 54);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 45, 55);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 53, 55);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 54, 55);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 52, 55);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 53, 54);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 40, 133);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 41, 133);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 42, 133);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 81, 66);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 82, 66);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 80, 67);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 81, 67);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 82, 67);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 86, 52);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 87, 52);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 88, 52);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 87, 51);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 88, 51);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 91, 56);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 92, 56);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 93, 56);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 93, 57);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 146, 40);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 147, 41);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 147, 40);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 120, 61);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 121, 61);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 122, 61);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 120, 60);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 121, 60);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 139, 81);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 139, 82);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 116, 78);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 117, 78);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 118, 78);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 116, 79);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 117, 79);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 124, 91);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 124, 92);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 124, 93);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 124, 94);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 124, 95);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 124, 96);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 124, 97);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 125, 97);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 126, 97);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 127, 97);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 128, 97);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 129, 97);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 129, 98);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 129, 99);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 129, 100);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 128, 100);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 127, 100);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 126, 100);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 125, 100);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 125, 101);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 125, 102);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 125, 103);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 126, 103);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 127, 103);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 128, 103);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 129, 103);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 129, 102);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 130, 102);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 131, 102);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 132, 102);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 133, 102);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 133, 101);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 133, 100);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 133, 99);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 133, 98);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 134, 98);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 135, 98);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 135, 97);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 135, 96);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 135, 95);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 135, 94);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 134, 94);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 133, 94);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 134, 93);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 133, 93);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 132, 93);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 131, 93);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 130, 93);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 129, 93);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 128, 121);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 129, 121);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 130, 121);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 131, 121);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 132, 121);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 129, 120);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 130, 120);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 130, 122);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 131, 122);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 132, 122);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 133, 122);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 134, 122);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 107, 122);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 108, 122);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 109, 122);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 110, 122);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 111, 122);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 112, 122);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 113, 122);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 114, 122);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 115, 122);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 119, 119);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 119, 120);
		i++;
	}
	
}
