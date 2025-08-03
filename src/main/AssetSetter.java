package main;

import entity.Npc;
import monster.Skeleton;
import monster.Zombie;
import object.OBJ_Axe;
import object.OBJ_Door;
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
		
		int i = 0 ;
		// This is where you can set position of the object
		// for example here door located at (30, 21)
		gp.obj[i] = new OBJ_Door(gp);
		gp.obj[i].worldX = 30 * gp.tileSize;
		gp.obj[i].worldY = 24 * gp.tileSize;
		i++;
		
		// Set speed boot to the world
		gp.obj[i] = new OBJ_speedBoot(gp);
		gp.obj[i].worldX = 32 * gp.tileSize;
		gp.obj[i].worldY = 21 * gp.tileSize;
		i++;
		
		gp.obj[i] = new OBJ_WoodenSword(gp);
		gp.obj[i].worldX = 33 * gp.tileSize;
		gp.obj[i].worldY = 22 * gp.tileSize;
		i++;
		
		gp.obj[i] = new OBJ_Sword(gp);
		gp.obj[i].worldX = 34 * gp.tileSize;
		gp.obj[i].worldY = 23 * gp.tileSize;
		i++;
		
		gp.obj[i] = new OBJ_Shield(gp);
		gp.obj[i].worldX = 35 * gp.tileSize;
		gp.obj[i].worldY = 24 * gp.tileSize;
		i++;
		
		gp.obj[i] = new OBJ_healingP(gp);
		gp.obj[i].worldX = 35 * gp.tileSize;
		gp.obj[i].worldY = 25 * gp.tileSize;
		i++;
		
		gp.obj[i] = new OBJ_healingP(gp);
		gp.obj[i].worldX = 36 * gp.tileSize;
		gp.obj[i].worldY = 26 * gp.tileSize;
		i++;
		
		gp.obj[i] = new OBJ_Firecharge(gp);
		gp.obj[i].worldX = 37 * gp.tileSize;
		gp.obj[i].worldY = 26 * gp.tileSize;
		i++;
		
		gp.obj[i] = new OBJ_Firecharge(gp);
		gp.obj[i].worldX = 38 * gp.tileSize;
		gp.obj[i].worldY = 26 * gp.tileSize;
		i++;
		
//		gp.obj[i] = new OBJ_Firecharge(gp);
//		gp.obj[i].worldX = 39 * gp.tileSize;
//		gp.obj[i].worldY = 26 * gp.tileSize;
//		i++;
		
		gp.obj[i] = new OBJ_Axe(gp);
		gp.obj[i].worldX = 28 * gp.tileSize;
		gp.obj[i].worldY = 17 * gp.tileSize;
		i++;
	}
	
	public void setNpc() {
		gp.npc[0] = new Npc(gp);
		gp.npc[0].worldX = gp.tileSize*21;
		gp.npc[0].worldY = gp.tileSize*21;
	}
	
	public void setMonster() {
		int i =0;
		gp.monster[i] = new Zombie(gp);
		gp.monster[i].worldX = gp.tileSize*29;
		gp.monster[i].worldY = gp.tileSize*29;
		i++;
		
		gp.monster[i] = new Zombie(gp);
		gp.monster[i].worldX = gp.tileSize*30;
		gp.monster[i].worldY = gp.tileSize*30;
		i++;
		
		gp.monster[i] = new Zombie(gp);
		gp.monster[i].worldX = gp.tileSize*28;
		gp.monster[i].worldY = gp.tileSize*28;
		i++;
		
		gp.monster[i] = new Zombie(gp);
		gp.monster[i].worldX = gp.tileSize*31;
		gp.monster[i].worldY = gp.tileSize*31;
		i++;
		
		gp.monster[i] = new Skeleton(gp);
		gp.monster[i].worldX = gp.tileSize*31;
		gp.monster[i].worldY = gp.tileSize*41;
		i++;
		
		gp.monster[i] = new Skeleton(gp);
		gp.monster[i].worldX = gp.tileSize*30;
		gp.monster[i].worldY = gp.tileSize*41;
		i++;
	}
	
	public void setInteractiveTile() {
		
		int i = 0;
		gp.iTile[i] = new IT_DryTree(gp, 22, 20);
		i++;
		gp.iTile[i] = new IT_DryTree(gp, 22, 21);
		i++;
		gp.iTile[i] = new IT_DryTree(gp, 22, 22);
		i++;
		gp.iTile[i] = new IT_DryTree(gp, 21, 20);
		i++;
		gp.iTile[i] = new IT_DryTree(gp, 21, 22);
		i++;
		gp.iTile[i] = new IT_DryTree(gp, 20, 20);
		i++;
		gp.iTile[i] = new IT_DryTree(gp, 20, 21);
		i++;
		gp.iTile[i] = new IT_DryTree(gp, 20, 22);
		i++;	
	}
	
}
