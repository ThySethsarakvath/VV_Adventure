package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Arrow;
import object.OBJ_Emerald;
import object.OBJ_Firecharge;
import object.OBJ_healingP;

public class Skeleton extends Entity {

	GamePanel gp;
	boolean lockedDirection = false;

	public Skeleton(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_skeleton;
		name = "Skelton";
		speed = 1;
		maxLife = 6;
		life = maxLife;
		pro = new OBJ_Arrow(gp);

		solidArea.x = 8; // X-offset within zombie sprite
		solidArea.y = 16; // Y-offset
		solidArea.width = 32; // Hitbox width
		solidArea.height = 32; // Hitbox height

		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		getImage();
		getAttackImage();
	}

	public void getImage() {

		up1 = setup("/monster/su1", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/monster/su2", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/monster/sd1", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/monster/sd2", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/monster/sl1", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/monster/sl2", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/monster/sr1", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/monster/sr2", gp.TILE_SIZE, gp.TILE_SIZE);
		upStand = setup("/monster/su", gp.TILE_SIZE, gp.TILE_SIZE);
		downStand = setup("/monster/sd", gp.TILE_SIZE, gp.TILE_SIZE);
		leftStand = setup("/monster/sl", gp.TILE_SIZE, gp.TILE_SIZE);
		rightStand = setup("/monster/sr", gp.TILE_SIZE, gp.TILE_SIZE);

	}

	public void getAttackImage() {

		aUp1 = setup("/monster/sua1", gp.TILE_SIZE, gp.TILE_SIZE);
		aUp2 = setup("/monster/sua2", gp.TILE_SIZE, gp.TILE_SIZE);
		aDown1 = setup("/monster/sda1", gp.TILE_SIZE, gp.TILE_SIZE);
		aDown2 = setup("/monster/sda2", gp.TILE_SIZE, gp.TILE_SIZE);
		aLeft1 = setup("/monster/sla1", gp.TILE_SIZE, gp.TILE_SIZE);
		aLeft2 = setup("/monster/sla2", gp.TILE_SIZE, gp.TILE_SIZE);
		aRight1 = setup("/monster/sra1", gp.TILE_SIZE, gp.TILE_SIZE);
		aRight2 = setup("/monster/sra2", gp.TILE_SIZE, gp.TILE_SIZE);

	}

	@Override
	public void update() {
	    super.update();

	    if (attacking) {
	        attackAnimation();
	    }

	    boolean contactPlayer = gp.cChecker.checkPlayer(this);

	    if (contactPlayer && shotCounter >= 30) {
	        damagePlayer(attack);
	        shotCounter = 0;
	    }

	    int xDist = gp.player.worldX - worldX;
	    int yDist = gp.player.worldY - worldY;
	    int tileDist = (Math.abs(xDist) + Math.abs(yDist)) / gp.TILE_SIZE;

	    // Stop shooting if player is too far
	    if (tileDist > 15) {
	        onPath = false;
	        attacking = false;
	        lockedDirection = false; // 🔓 Reset direction lock
	    }

	    // If player is within 7 tiles, shoot
	    else if (tileDist <= 7) {
	        onPath = false;
	        gp.pFinder.pathList.clear();

	        if (!lockedDirection) {
	            // 🎯 Angle-based facing direction
	            double angle = Math.atan2(yDist, xDist);
	            double degrees = Math.toDegrees(angle);
	            if (degrees < 0) degrees += 360;

	            if (degrees >= 45 && degrees < 135) {
	                direction = "down";
	            } else if (degrees >= 135 && degrees < 225) {
	                direction = "left";
	            } else if (degrees >= 225 && degrees < 315) {
	                direction = "up";
	            } else {
	                direction = "right";
	            }

	            lockedDirection = true; // 🔒 Lock direction until attack ends
	        }

	        // Start shooting if not already
	        if (!attacking && shotCounter >= 30 && !pro.alive) {
	            attacking = true;
	            spriteNum = 1;
	            spriteCounter = 0;
	        }
	    }

	    // If player is between 8 and 15 tiles, walk closer
	    else {
	        onPath = true;
	        lockedDirection = false; // 🔓 Unlock direction when chasing
	    }
	}


	public void attackAnimation() {

		spriteCounter++;

		// Frame 1: Draw bow (0-20 frames) - extended for better visibility
		if (spriteCounter <= 20) {
			spriteNum = 1;
		}
		// Frame 2: Release arrow (21-35 frames)
		else if (spriteCounter <= 35) {
			spriteNum = 2;

			// Fire arrow exactly once at frame 21
			if (spriteCounter == 21 && !pro.alive && shotCounter >= 30) {
				pro.set(worldX, worldY, direction, true, this);
				
				for(int i = 0; i<gp.projectile[1].length;i++) {
					if(gp.projectile[gp.currentMap][i] == null) {
						gp.projectile[gp.currentMap][i] = pro;
						break;
					}
				}
				shotCounter = 0;
				gp.playSE(11); // Play arrow sound
			}
		}
		// Smooth transition period (36-45 frames)
		else if (spriteCounter <= 45) {
			// Blend between attack and standing frames
			if (spriteCounter % 3 < 2) { // Show attack frame 2/3 of the time
				spriteNum = 2;
			} else {
				spriteNum = 3; // Briefly show standing frame
			}
		}
		else {
		    attacking = false;
		    spriteNum = 3;
		    spriteCounter = 0;
		    lockedDirection = false; 
		}

	}

	public void setAction() {
		if (attacking) {
			return; // Don't move while shooting
		}

//		if (onPath) {
//			int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
//			int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
//			searchPath(goalCol, goalRow);
//		}
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
