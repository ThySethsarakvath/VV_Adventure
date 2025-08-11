package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Fireball;

public class Zombie extends Entity {

	GamePanel gp;
	public Zombie(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_zombie;
		name = "Zombie";
		speed = 1;
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

			actionLockCounter = 0;
		}
		
		// add this for new projectile moster
//		int i = new Random().nextInt(100)+1;
//		if(i > 99 && pro.alive == false && shotCounter == 30) {
//			pro.set(worldX, worldY, direction, true, this);
//			gp.projectileList.add(pro);
//			shotCounter=0;
//		}
		
	}
	
	public void damageReaction() {
		
		actionLockCounter = 0;
		direction = gp.player.direction;
	}
}
