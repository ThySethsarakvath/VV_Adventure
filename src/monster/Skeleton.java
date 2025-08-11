package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Arrow;

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

		up1 = setup("/monster/su1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/su2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/sd1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/sd2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/sl1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/sl2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/sr1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/sr2", gp.tileSize, gp.tileSize);
		upStand = setup("/monster/su", gp.tileSize, gp.tileSize);
		downStand = setup("/monster/sd", gp.tileSize, gp.tileSize);
		leftStand = setup("/monster/sl", gp.tileSize, gp.tileSize);
		rightStand = setup("/monster/sr", gp.tileSize, gp.tileSize);

	}

	public void getAttackImage() {

		aUp1 = setup("/monster/sua1", gp.tileSize, gp.tileSize);
		aUp2 = setup("/monster/sua2", gp.tileSize, gp.tileSize);
		aDown1 = setup("/monster/sda1", gp.tileSize, gp.tileSize);
		aDown2 = setup("/monster/sda2", gp.tileSize, gp.tileSize);
		aLeft1 = setup("/monster/sla1", gp.tileSize, gp.tileSize);
		aLeft2 = setup("/monster/sla2", gp.tileSize, gp.tileSize);
		aRight1 = setup("/monster/sra1", gp.tileSize, gp.tileSize);
		aRight2 = setup("/monster/sra2", gp.tileSize, gp.tileSize);

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
	    int tileDist = (Math.abs(xDist) + Math.abs(yDist)) / gp.tileSize;

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
				gp.projectileList.add(pro);
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

		if (onPath) {
			int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
			int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
			searchPath(goalCol, goalRow);
		}
	}

	public void damageReaction() {

		actionLockCounter = 0;
		direction = gp.player.direction;
	}
}
