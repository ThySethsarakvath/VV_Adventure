package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Arc2D.Float;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {

	protected GamePanel gp;

	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, upStand, downStand, leftStand,
			rightStand;
	public BufferedImage aUp1, aUp2, aDown1, aDown2, aRight1, aRight2, aLeft1, aLeft2;
	public BufferedImage image, image2, image3;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public int solidAreaDefaultX, solidAreaDefaultY; // For Object interaction attributes
	public boolean collisionOn = false;
	boolean attackSoundPlayed = false;
	String dialogues[] = new String[20];

	// STATE
	public int worldX, worldY;
	public String direction = "down";
	public int spriteNum = 1;
	int dialogueIndex = 0;
	public boolean invincible = false;
	public boolean collision = false;
	public boolean attacking = false;
	public boolean alive = true;
	public boolean die = false;
	boolean hpBarOn = false;

	// COUNTER
	public int actionLockCounter = 0;
	public int invinCounter = 0;
	public int spriteCounter = 0;
	int dieCounter;
	int hpBarCounter =0;

	// CHARACTER ATTRIBUTES
	public String name;
	public int type; // 0 = player , 1 = npc, 2 = monster
	public int maxLife;
	public int life;
	public int speed;

	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	public void setAction() {
	}
	
	public void damageReaction() {
		
	}

	public void speak() {

		if (dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}

		// Set the current dialogue text
		gp.ui.currentDialogue = dialogues[dialogueIndex];

		// Reset typing animation
		gp.ui.displayedText = "";
		gp.ui.charIndex = 0;
		gp.ui.textCompleted = false;
		gp.ui.textTimer = 0;

		dialogueIndex++;

		// NPC will face to us during we're talking to each other
		switch (gp.player.direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "right":
			direction = "left";
			break;
		case "left":
			direction = "right";
			break;
		}

	}

	public void update() {
		setAction();
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.monster);
		gp.cChecker.checkEntity(this, gp.npc);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);

		if (this.type == 2 && contactPlayer == true) {
			if (gp.player.invincible == false) {
				// recieve damage
				gp.playSE(2);
				gp.player.life -= 1;
				gp.player.invincible = true;
			}
		}

		if (!collisionOn && !direction.equals("stand")) {
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

		// Animate only if moving
		if (!direction.equals("stand") && !collisionOn) {
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
			// Set to standing frame
			spriteNum = 3;
		}

		if (invincible) {
			invinCounter++;
			if (invinCounter > 40) {
				invincible = false;
				invinCounter = 0;
			}
		}
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;

		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
				&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
				&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
				&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

			switch (direction) {
			case "up":
				if (spriteNum == 1)
					image = up1;
				else if (spriteNum == 2)
					image = up2;
				else if (spriteNum == 3)
					image = upStand;
				break;
			case "down":
				if (spriteNum == 1)
					image = down1;
				else if (spriteNum == 2)
					image = down2;
				else if (spriteNum == 3)
					image = downStand;
				break;
			case "left":
				if (spriteNum == 1)
					image = left1;
				else if (spriteNum == 2)
					image = left2;
				else if (spriteNum == 3)
					image = leftStand;
				break;
			case "right":
				if (spriteNum == 1)
					image = right1;
				else if (spriteNum == 2)
					image = right2;
				else if (spriteNum == 3)
					image = rightStand;
				break;
			}
			
			//Monster HP bar
			if(type ==2 && hpBarOn == true) {
				
				double oneScale = (double)gp.tileSize/maxLife;
				double hpBarValue = oneScale*life;
				
				g2.setColor(new Color(35,35,35));
				g2.fillRect(screenX-1, screenY-16, gp.tileSize, 10);
				
				g2.setColor(new Color(255,0,0));
				g2.fillRect(screenX, screenY-15, (int)hpBarValue, 10);
				
				hpBarCounter ++;
				
				if(hpBarCounter > 600) { // the bar appear within 600 frames = 10s
					hpBarCounter = 0;
					hpBarOn = false;
				}
			}

			// Image transparent when receive damage
			if (invincible == true) {
				hpBarOn = true;
				hpBarCounter = 0;
				changeAlpha(g2, 0.5f);
			}

			if (die == true) {
				deadAnimation(g2);
			}

			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

			// Reset Alpha
			changeAlpha(g2, 1f);
		}
	}

	public void deadAnimation(Graphics2D g2) {
		dieCounter++;

		int i = 5;
		if (dieCounter <= i) {
			changeAlpha(g2, 0f);
		}
		if (dieCounter > i && dieCounter <= i*2) {
			changeAlpha(g2, 1f);
		}
		if (dieCounter > i*2 && dieCounter <= i*3) {
			changeAlpha(g2, 0f);
		}
		if (dieCounter > i*3 && dieCounter <= i*4) {
			changeAlpha(g2, 1f);
		}
		if (dieCounter > i*4 && dieCounter <= i*5) {
			changeAlpha(g2, 0f);
		}
		if (dieCounter > i*5 && dieCounter <= i*6) {
			changeAlpha(g2, 1f);
		}
		if (dieCounter > i*6 && dieCounter <= i*7) {
			changeAlpha(g2, 0f);
		}
		if (dieCounter > i*7 && dieCounter <= i*8) {
			changeAlpha(g2, 1f);
		}

		if (dieCounter > 40) {
			die = false;
			alive = false;
		}
	}

	public void changeAlpha(Graphics2D g2, float value) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, value));
	}

	public BufferedImage setup(String imagePath, int width, int height) {

		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}
}
