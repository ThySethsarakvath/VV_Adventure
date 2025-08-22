package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Emerald;
import object.OBJ_Fireball;
import object.OBJ_Firecharge;
import object.OBJ_healingP;

public class Zombie extends Entity {

	GamePanel gp;
	public Zombie(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_zombie;
		name = "Zombie";
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 6;
		life = maxLife;
//		pro = new OBJ_Fireball(gp);
		
		solidArea.x = 8; // X-offset within zombie sprite
	    solidArea.y = 16; // Y-offset
	    solidArea.width = 32; // Hitbox width
	    solidArea.height = 32; // Hitbox height

	    solidAreaDefaultX = solidArea.x;
	    solidAreaDefaultY = solidArea.y;
	    getImage();
	}
	
	public void getImage() {
		
		up1 = setup("/monster/zombie_up1",gp.tileSize,gp.tileSize);
		up2 = setup("/monster/zombie_up2",gp.tileSize,gp.tileSize);
		down1 = setup("/monster/zombie_down1",gp.tileSize,gp.tileSize);
		down2 = setup("/monster/zombie_down2",gp.tileSize,gp.tileSize);
		left1 = setup("/monster/zombie_left1",gp.tileSize,gp.tileSize);
		left2 = setup("/monster/zombie_left2",gp.tileSize,gp.tileSize);
		right1 = setup("/monster/zombie_right1",gp.tileSize,gp.tileSize);
		right2 = setup("/monster/zombie_right2",gp.tileSize,gp.tileSize);
		upStand = setup("/monster/zombie_up",gp.tileSize,gp.tileSize);
		downStand = setup("/monster/zombie_down",gp.tileSize,gp.tileSize);
		leftStand = setup("/monster/zombie_left",gp.tileSize,gp.tileSize);
		rightStand = setup("/monster/zombie_right",gp.tileSize,gp.tileSize);
		
		
	}
	
	public void update() {
		
		super.update();
		
		boolean contactPlayer = gp.cChecker.checkPlayer(this);

		if (contactPlayer && shotCounter >= 30 && !gp.player.invincible) {
		    damagePlayer(attack);
		    shotCounter = 0;
		}
		
		int xDist = Math.abs(worldX - gp.player.worldX);
		int yDist = Math.abs(worldY - gp.player.worldY);
		int tileDist = (xDist + yDist)/gp.tileSize;
		
		if(onPath == false && tileDist < 5) {
			
			int i =  new Random().nextInt(100)+1;
			if(i > 50) {
				onPath = true;
			}
		}
		// stop chasing
		if(onPath == true && tileDist > 15) {
			onPath = false;
		}
	}
	
	public void setAction() {

		if (onPath == true) {

			// goal position
//			int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
//			int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
//
//			checkCollision();
//			if (collisionOn) {
//				gp.pFinder.pathList.clear();
//				searchPath(goalCol, goalRow);
//			} else {
//				searchPath(goalCol, goalRow);
//			}

		} else {
			actionLockCounter++;

			if (actionLockCounter == 120) {
				Random rand = new Random();
				int i = rand.nextInt(100) + 1; // number from 1 to 100

				if (i <= 20) {
					// Do nothing — keeps current direction and "stands" still
				} else if (i <= 40) {
					direction = "up";
				} else if (i <= 60) {
					direction = "down";
				} else if (i <= 80) {
					direction = "left";
				} else {
					direction = "right";
				}

				actionLockCounter = 0;
			}
		}
		
	}
	
	public void damageReaction() {
		
		actionLockCounter = 0;
		onPath = true;
	}
	
	public void checkDrop() {
		// cast a die
		int i = new Random().nextInt(100)+1;
		
		// set dropping
		if(i<50) {
			dropItem(new OBJ_Emerald(gp));
		}
		if(i >= 50 && i< 75) {
			dropItem(new OBJ_Firecharge(gp));
		}
		if(i >= 75 && i< 100) {
			dropItem(new OBJ_healingP(gp));
		}
	}
}
