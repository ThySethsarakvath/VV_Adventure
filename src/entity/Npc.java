package entity;

import java.util.Random;

import main.GamePanel;

public class Npc extends Entity {

	public Npc(GamePanel gp) {
	    super(gp);

	    direction = "down";
	    speed = 1;

	    // Customize solid area position and size
	    solidArea.x = 0; // X-offset within NPC sprite
	    solidArea.y = 16; // Y-offset
	    solidArea.width = 48; // Hitbox width
	    solidArea.height = 32; // Hitbox height

	    solidAreaDefaultX = solidArea.x;
	    solidAreaDefaultY = solidArea.y;

	    getImage();
	    setDialogue();
	}


	// Set NPC sprite
	public void getImage() {

		up1 = setup("/npc/npc_up1");
		up2 = setup("/npc/npc_up2");
		down1 = setup("/npc/npc_down1");
		down2 = setup("/npc/npc_down2");
		left1 = setup("/npc/npc_left1");
		left2 = setup("/npc/npc_left2");
		right1 = setup("/npc/npc_right1");
		right2 = setup("/npc/npc_right2");
		upStand = setup("/npc/npc_up");
		downStand = setup("/npc/npc_down");
		leftStand = setup("/npc/npc_left");
		rightStand = setup("/npc/npc_right");
	}
	
	public void setDialogue() {
		
		dialogues[0] = "Hello, player!";
		dialogues[1] = "How are you!";
		dialogues[2] = "You need to find a way to get out \nfrom this island!";
		dialogues[3] = "You will face some monsters!";
		
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
	
	public void speak() {
		
		// Do this character specific stuff
		
		super.speak();
		
	}
}
