package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Arrow;

public class Skeleton extends Entity {

	GamePanel gp;
	public Skeleton(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_zombie;
		name = "Skelton";
		speed = 1;
		maxLife = 6;
		life = maxLife;
		pro = new OBJ_Arrow(gp);
		
		solidArea.x = 0; // X-offset within zombie sprite
	    solidArea.y = 16; // Y-offset
	    solidArea.width = 48; // Hitbox width
	    solidArea.height = 32; // Hitbox height

	    solidAreaDefaultX = solidArea.x;
	    solidAreaDefaultY = solidArea.y;
	    getImage();
	    getAttackImage();
	}
	
	public void getImage() {
		
		up1 = setup("/monster/su1",gp.tileSize,gp.tileSize);
		up2 = setup("/monster/su2",gp.tileSize,gp.tileSize);
		down1 = setup("/monster/sd1",gp.tileSize,gp.tileSize);
		down2 = setup("/monster/sd2",gp.tileSize,gp.tileSize);
		left1 = setup("/monster/sl1",gp.tileSize,gp.tileSize);
		left2 = setup("/monster/sl2",gp.tileSize,gp.tileSize);
		right1 = setup("/monster/sr1",gp.tileSize,gp.tileSize);
		right2 = setup("/monster/sr2",gp.tileSize,gp.tileSize);
		upStand = setup("/monster/su",gp.tileSize,gp.tileSize);
		downStand = setup("/monster/sd",gp.tileSize,gp.tileSize);
		leftStand = setup("/monster/sl",gp.tileSize,gp.tileSize);
		rightStand = setup("/monster/sr",gp.tileSize,gp.tileSize);
		
		
	}
	
	public void getAttackImage() {
		
		aUp1 = setup("/monster/sua1",gp.tileSize,gp.tileSize);
		aUp2 = setup("/monster/sua2",gp.tileSize,gp.tileSize);
		aDown1 = setup("/monster/sda1",gp.tileSize,gp.tileSize);
		aDown2 = setup("/monster/sda2",gp.tileSize,gp.tileSize);
		aLeft1 = setup("/monster/sla1",gp.tileSize,gp.tileSize);
		aLeft2 = setup("/monster/sla2",gp.tileSize,gp.tileSize);
		aRight1 = setup("/monster/sra1",gp.tileSize,gp.tileSize);
		aRight2 = setup("/monster/sra2",gp.tileSize,gp.tileSize);
		
		
	}
	
	@Override
	public void update() {
		super.update();
		
		if(attacking) {
			attackAnimation();
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
	            gp.playSE(10); // Play arrow sound
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
	    // Complete reset
	    else {
	        attacking = false;
	        spriteNum = 3;
	        spriteCounter = 0;
	    }
	}
	
	public void setAction() {

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
			if(i>80) {
				attacking = true;
				spriteNum = 1;
				spriteCounter = 0;
			}

			actionLockCounter = 0;
		}
		
		// add this for new projectile moster
		int i = new Random().nextInt(100)+1;
		if(!attacking && i > 99 && pro.alive == false && shotCounter == 30 ){
			attacking = true;
			spriteNum = 1;
			spriteCounter = 0;
//			pro.set(worldX, worldY, direction, true, this);
//			gp.projectileList.add(pro);
//			shotCounter=0;
		}
		
	}
	
	public void damageReaction() {
		
		actionLockCounter = 0;
		direction = gp.player.direction;
	}
}
