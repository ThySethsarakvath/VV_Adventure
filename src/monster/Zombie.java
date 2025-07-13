package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class Zombie extends Entity {

	public Zombie(GamePanel gp) {
		super(gp);
		
		name = "Zombie";
		speed = 1;
		maxLife = 6;
		life = maxLife;
		
		solidArea.x = 0; // X-offset within zombie sprite
	    solidArea.y = 16; // Y-offset
	    solidArea.width = 48; // Hitbox width
	    solidArea.height = 32; // Hitbox height

	    solidAreaDefaultX = solidArea.x;
	    solidAreaDefaultY = solidArea.y;
	    
	    getImgae();
	}
	
	public void getImgae() {
		
		up1 = setup("/monster/zombie_up1");
		up2 = setup("/monster/zombie_up2");
		down1 = setup("/monster/zombie_down1");
		down2 = setup("/monster/zombie_down2");
		left1 = setup("/monster/zombie_left1");
		left2 = setup("/monster/zombie_left2");
		right1 = setup("/monster/zombie_right1");
		right2 = setup("/monster/zombie_right2");
		upStand = setup("/monster/zombie_up");
		downStand = setup("/monster/zombie_down");
		leftStand = setup("/monster/zombie_left");
		rightStand = setup("/monster/zombie_right");
		
		
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
	}
}
