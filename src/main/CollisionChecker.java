package main;

import entity.Entity;

public class CollisionChecker {

	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	public void checkTile(Entity entity) {

		// Calculate player's solidArea corners
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

		// Convert pixel positions to tile grid positions
		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;

		int tileNum1, tileNum2;

		switch (entity.direction) {

		case "up":

			entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];

			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}

			break;
		case "down":

			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}

			break;
		case "right":

			entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}

			break;
		case "left":

			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];

			if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}

			break;

		}

	}

	public int checkObject(Entity entity, boolean player) { // Check entity is player or not

		int index = -1;

		for (int i = 0; i < gp.obj.length; i++) {

			if (gp.obj[i] != null) {

				// Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				// Get the object's solid area position
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

				switch (entity.direction) {

				case "up":
					entity.solidArea.y -= entity.speed;
					break;

				case "down":
					entity.solidArea.y += entity.speed;
					break;

				case "left":
					entity.solidArea.x -= entity.speed;
					break;

				case "right":
					entity.solidArea.x += entity.speed;
					break;

				}

				// Auto checks if 2 rectangles are colliding or not
				if (entity.solidArea.intersects(gp.obj[i].solidArea)) { // entity pas object ort?
					if (gp.obj[i].collision == true) {
						entity.collisionOn = true;
					}
					if (player == true) { // NPC or monster can't pick objects
						index = i;
					}
				}

				// Reset solidArea.x/y to prevent keep increasing value
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
			}

		}

		return index;

	}

	public int checkEntity(Entity entity, Entity[] target) {
		int index = -1;

		for (int i = 0; i < target.length; i++) {

			if (target[i] != null) {

				// Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				// Get the object's solid area position
				target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
				target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

				switch (entity.direction) {

				case "up":
					entity.solidArea.y -= entity.speed;
					break;

				case "down":
					entity.solidArea.y += entity.speed;
					break;

				case "left":
					entity.solidArea.x -= entity.speed;
					break;

				case "right":
					entity.solidArea.x += entity.speed;
					break;

				}

				// Auto checks if 2 rectangles are colliding or not
				if (entity.solidArea.intersects(target[i].solidArea)) { // entity pas object ort?
					if (target[i] != entity) {
						entity.collisionOn = true;
						index = i;
					}
				}
				// Reset solidArea.x/y to prevent keep increasing value
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;

			}

		}
		return index;
	}

	public boolean checkPlayer(Entity entity) {

		boolean contactPlayer = false;
		// Get entity's solid area position
		entity.solidArea.x = entity.worldX + entity.solidArea.x;
		entity.solidArea.y = entity.worldY + entity.solidArea.y;
		// Get the object's solid area position
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

		switch (entity.direction) {

		case "up":
			entity.solidArea.y -= entity.speed;
			break;

		case "down":
			entity.solidArea.y += entity.speed;
			break;

		case "left":
			entity.solidArea.x -= entity.speed;
			break;

		case "right":
			entity.solidArea.x += entity.speed;
			break;
		}

		// Auto checks if 2 rectangles are colliding or not
		if (entity.solidArea.intersects(gp.player.solidArea)) { // entity pas object ort?
			entity.collisionOn = true;
			contactPlayer =true;
		}

		// Reset solidArea.x/y to prevent keep increasing value
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		
		return contactPlayer;
	}
}
