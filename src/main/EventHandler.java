package main;

import java.awt.Rectangle;

public class EventHandler {

	GamePanel gp;
	EventRect eventRect[][];
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = false;

	public EventHandler(GamePanel gp) {
		this.gp = gp;

		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

		int col = 0;
		int row = 0;

		while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
			eventRect[col][row] = new EventRect();
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			eventRect[col][row].width = 2;
			eventRect[col][row].height = 2;
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

			col++;
			if (col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}

	}

	public void checkEvent() {
		
		// check if player if 1 tile away from event
		int xDis = Math.abs(gp.player.worldX - previousEventX);
		int yDis = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDis, yDis);
		
		if (distance > gp.tileSize) {
			canTouchEvent = true;
		}
		
		if (canTouchEvent == true) {
			if (hit(28, 17, "any") == true) {
				damagePit(28,17);
			}
			if (hit(27, 17, "any") == true) {
				teleport(gp.dialogueState);
			}
		}
		

		
	}

	public boolean hit(int col, int row, String reqDirection) {

		boolean hit = false;
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

		eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
		eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

		if (gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
			if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
				
				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;
			}
		}

		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

		return hit;
	}

	public void teleport(int gameState) {

		gp.gameState = gameState;
		gp.ui.currentDialogue = "Teleporting...";
		gp.player.worldX = gp.tileSize * 28;
		gp.player.worldY = gp.tileSize * 41;
	}

	public void damagePit(int col, int row) {
//		gp.gameState = gameState;
//		gp.ui.currentDialogue = "You fail";
		gp.player.life -= 1;
		// for one time event
//		eventRect[col][row].eventDone = true;
		// for multi time
		canTouchEvent = false;
	}

}
