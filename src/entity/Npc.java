package entity;

import java.util.Random;

import main.GamePanel;

public class Npc extends Entity {

	public Npc(GamePanel gp) {
		super(gp);

		direction = "down";
		speed = 1;

		// Customize solid area position and size
		solidArea.x = 8; // X-offset within NPC sprite
		solidArea.y = 16; // Y-offset
		solidArea.width = 32; // Hitbox width
		solidArea.height = 32; // Hitbox height

		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		getImage();
		setDialogue();
	}

	// Set NPC sprite
	public void getImage() {

		up1 = setup("/npc/npc_up1", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/npc/npc_up2", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/npc/npc_down1", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/npc/npc_down2", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/npc/npc_left1", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/npc/npc_left2", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/npc/npc_right1", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/npc/npc_right2", gp.TILE_SIZE, gp.TILE_SIZE);
		upStand = setup("/npc/npc_up", gp.TILE_SIZE, gp.TILE_SIZE);
		downStand = setup("/npc/npc_down", gp.TILE_SIZE, gp.TILE_SIZE);
		leftStand = setup("/npc/npc_left", gp.TILE_SIZE, gp.TILE_SIZE);
		rightStand = setup("/npc/npc_right", gp.TILE_SIZE, gp.TILE_SIZE);
	}

	public void setDialogue() {

		dialogues[0] = "Hello, player!";
		dialogues[1] = "How are you!";
		dialogues[2] = "You need to find a way to get out \nfrom this island!";
		dialogues[3] = "You will face some monsters!";

	}

	public void setAction() {

		if (onPath == true) {

//			 goal position
			int goalCol = 32;
			int goalRow = 61;
//			int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
//			int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;

			checkCollision();
			if (collisionOn) {
				gp.pFinder.pathList.clear();
				searchPath(goalCol, goalRow);
			} else {
				searchPath(goalCol, goalRow);
			}

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

	@Override
	public void speak() {

		// Do this character specific stuff

		super.speak();
//		onPath = true;
	}
}
