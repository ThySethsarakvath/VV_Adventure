package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity {

	KeyHandler keyH;

	public final int screenX;
	public final int screenY;

	// Count how many object we interaction with
	public int openDoor = 0;

	// Speed boot
	int getSpeedBoot = 0;
	int speedTimer = 0;
	boolean speedBoosted = false;

	public Player(GamePanel gp, KeyHandler keyH) {

		super(gp);
		this.keyH = keyH;

		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;

		// Object interaction
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		solidArea.width = 32;
		solidArea.height = 32;

		setDefaultValues();
		getPlayerImage();
	}

	// Player coordinate
	public void setDefaultValues() {

		worldX = gp.tileSize * 28;
		worldY = gp.tileSize * 18;
		speed = 4;
		direction = "down";
		
		//Player state
		// 2 lives = full heart
		//1 life = half heart
		
		maxLife = 10; // 10 lives = 5 hearts
		life = maxLife;
	}

	public void getPlayerImage() {

		up1 = setup("/player/Steve_up1");
		up2 = setup("/player/Steve_up2");
		down1 = setup("/player/Steve_down1");
		down2 = setup("/player/Steve_down2");
		left1 = setup("/player/Steve_left1");
		left2 = setup("/player/Steve_left2");
		right1 = setup("/player/Steve_right1");
		right2 = setup("/player/Steve_right2");
		upStand = setup("/player/Steve_up_stand");
		downStand = setup("/player/Steve_down_stand");
		leftStand = setup("/player/Steve_left_stand");
		rightStand = setup("/player/Steve_right_stand");
	}

	public void update() {

		if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

			// The Y-axis decreases as we move up
			if (keyH.upPressed) {
				direction = "up";
			}
			// The Y-axis increases as we move down
			else if (keyH.downPressed) {
				direction = "down";
			}
			// The X-axis decreases as we move left.
			else if (keyH.leftPressed) {
				direction = "left";
			}
			// The X-axis increases as we move right
			else if (keyH.rightPressed) {
				direction = "right";
			}

			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);

			// CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true); // True = player
			pickUpObject(objIndex);

			// Npc collsion
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNpc(npcIndex);
			
			//Monster collision
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			interactNpc(monsterIndex);
			contactMonster(monsterIndex);

			// IF COLLISION IS FALSE, PLAYER CAN MOVE
			if (collisionOn == false) {
				switch (direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}

			// Animate walking frames
			spriteCounter++;
			if (spriteCounter > 10) {
				if (spriteNum == 1)
					spriteNum = 3;
				else if (spriteNum == 3)
					spriteNum = 2;
				else if (spriteNum == 2)
					spriteNum = 1;
				spriteCounter = 0;
			}
		} else {
			// No key is pressed, so reset to standing
			spriteNum = 3;
		}

		// Reset speed to the default one
		if (speedBoosted) {
			speedTimer--;
			if (speedTimer <= 0) {
				speed -= 2;
				speedBoosted = false;
			}
		}
		
		if(invincible == true) {
			invinCounter ++;
			if(invinCounter > 60) {
				invincible = false;
				invinCounter =0;
			}
		}
	}

	public void pickUpObject(int i) {

		if (i != -1) {
			// gp.obj[i] = null; // delete object that player touched
			String objectName = gp.obj[i].name;

			switch (objectName) {
//			case "Door":
//				openDoor++;
//				gp.obj[i] = null;
//				gp.ui.showMessage("The door opened!");
//
//				break;
			case "speed_boot":
				if (!speedBoosted) {
					gp.playSE(1);
					speedBoosted = true;
					speed += 2;
					speedTimer = 180; // 180 frames = 3 seconds, since our FPS = 60

					gp.obj[i] = null;
				}
				break;
			}
		}
	}

	public void interactNpc(int i) {

		if (i != -1) {
			
			if(gp.keyH.enterPressed == true) {
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}
		}
		gp.keyH.enterPressed = false;
	}
	
	public void contactMonster(int i) {
		
		if(i != -1) {
			if(invincible == false) {
				life -= 1;
				invincible = true;
			}
			
		}
	}

	public void draw(Graphics2D g2) {

//		g2.setColor(Color.white);
//		g2.fillRect(x, y, gp.tileSize , gp.tileSize);

		BufferedImage image = null;
//		boolean moving = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;

		switch (direction) {
		case "up":
			if (spriteNum == 1) image = up1;
			else if (spriteNum == 2) image = up2;
			else if (spriteNum == 3) image = upStand;
			break;
		case "down":
			if (spriteNum == 1) image = down1;
			else if (spriteNum == 2) image = down2;
			else if (spriteNum == 3) image = downStand;
			break;
		case "left":
			if (spriteNum == 1) image = left1;
			else if (spriteNum == 2) image = left2;
			else if (spriteNum == 3) image = leftStand;
			break;
		case "right":
			if (spriteNum == 1) image = right1;
			else if (spriteNum == 2) image = right2;
			else if (spriteNum == 3) image = rightStand;
			break;
		}
		
		// Image transparent when receive damage
		if(invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
		}

		g2.drawImage(image, screenX, screenY, null);
		
		// Reset Alpha
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		
//		g2.setFont(new Font("Arail",Font.PLAIN,26));
//		g2.setColor(Color.white);
//		g2.drawString("Invincible: "+invinCounter,10,400);
	}
}
