package main;

import entity.Npc;
import entity.Wander;
import monster.Skeleton;
import monster.Zombie;
import object.OBJ_Axe;
import object.OBJ_Emerald;
import object.OBJ_Firecharge;
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
		
		// Set speed boot to the world
		gp.obj[mapNum][i] = new OBJ_speedBoot(gp);
		gp.obj[mapNum][i].worldX = 32 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 21 * gp.tileSize;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_WoodenSword(gp);
		gp.obj[mapNum][i].worldX = 33 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 22 * gp.tileSize;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Sword(gp);
		gp.obj[mapNum][i].worldX = 34 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 23 * gp.tileSize;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Shield(gp);
		gp.obj[mapNum][i].worldX = 35 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 24 * gp.tileSize;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_healingP(gp);
		gp.obj[mapNum][i].worldX = 35 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 25 * gp.tileSize;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_healingP(gp);
		gp.obj[mapNum][i].worldX = 36 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 26 * gp.tileSize;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Firecharge(gp);
		gp.obj[mapNum][i].worldX = 37 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 26 * gp.tileSize;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Firecharge(gp);
		gp.obj[mapNum][i].worldX = 38 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 26 * gp.tileSize;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Emerald(gp);
		gp.obj[mapNum][i].worldX = 29 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 17 * gp.tileSize;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Emerald(gp);
		gp.obj[mapNum][i].worldX = 30 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 17 * gp.tileSize;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Emerald(gp);
		gp.obj[mapNum][i].worldX = 31 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 17 * gp.tileSize;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Emerald(gp);
		gp.obj[mapNum][i].worldX = 32 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 17 * gp.tileSize;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Emerald(gp);
		gp.obj[mapNum][i].worldX = 33 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 17 * gp.tileSize;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Axe(gp);
		gp.obj[mapNum][i].worldX = 28 * gp.tileSize;
		gp.obj[mapNum][i].worldY = 17 * gp.tileSize;
		i++;
	}
	
	public void setNpc() {
		
		int mapNum = 0;
		int i = 0;
		
		gp.npc[mapNum][i] = new Npc(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize*21;
		gp.npc[mapNum][i].worldY = gp.tileSize*21;
		i++;
		
		mapNum =1;
		i=0;
		gp.npc[mapNum][i] = new Wander(gp);
		gp.npc[mapNum][i].worldX = gp.tileSize*25;
		gp.npc[mapNum][i].worldY = gp.tileSize*21;
		i++;
	}
	
	public void setMonster() {
		
		int mapNum = 0;
		int i =0;
		
		gp.monster[mapNum][i] = new Zombie(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*29;
		gp.monster[mapNum][i].worldY = gp.tileSize*29;
		i++;
		
		gp.monster[mapNum][i] = new Zombie(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*30;
		gp.monster[mapNum][i].worldY = gp.tileSize*30;
		i++;
		
		gp.monster[mapNum][i] = new Zombie(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*28;
		gp.monster[mapNum][i].worldY = gp.tileSize*28;
		i++;
		
		gp.monster[mapNum][i] = new Zombie(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*31;
		gp.monster[mapNum][i].worldY = gp.tileSize*31;
		i++;
		
		gp.monster[mapNum][i] = new Skeleton(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*31;
		gp.monster[mapNum][i].worldY = gp.tileSize*41;
		i++;
		
		gp.monster[mapNum][i] = new Skeleton(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*30;
		gp.monster[mapNum][i].worldY = gp.tileSize*41;
		i++;
		
		gp.monster[mapNum][i] = new Skeleton(gp);
		gp.monster[mapNum][i].worldX = gp.tileSize*30;
		gp.monster[mapNum][i].worldY = gp.tileSize*40;
		i++;
		
	}
	
	public void setInteractiveTile() {
		
		int mapNum = 0;
		int i = 0;
		
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 22, 20);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 22, 21);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 22, 22);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 21, 20);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 21, 22);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 20, 20);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 20, 21);
		i++;
		gp.iTile[mapNum][i] = new IT_DryTree(gp, 20, 22);
		i++;	
	}
	
}
