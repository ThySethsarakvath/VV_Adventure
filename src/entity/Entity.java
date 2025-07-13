package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {

	GamePanel gp;
	
	public int worldX, worldY;
	public int speed;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2,upStand, downStand, leftStand, rightStand;
	public String direction = "down";
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	
	public int solidAreaDefaultX, solidAreaDefaultY; // For Object interaction attributes
	
	public boolean collisionOn = false;
	
	public int actionLockCounter = 0;
	
	// FOR DIALOGUE ATTRIBUTES
	String dialogues[] = new String[20];
	int dialogueIndex = 0;
	
	public BufferedImage image,image2,image3;
	public String name;
	public boolean collision = false;
	
	//Hero State
	public int maxLife;
	public int life;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setAction() {}
	
	public void speak() {
		
		if(dialogues[dialogueIndex] == null) {
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
		switch(gp.player.direction) {
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
		gp.cChecker.checkPlayer(this);
		
		if (!collisionOn && !direction.equals("stand")) {
	        switch (direction) {
	            case "up": worldY -= speed; break;
	            case "down": worldY += speed; break;
	            case "left": worldX -= speed; break;
	            case "right": worldX += speed; break;
	        }
	    }

		// Animate only if moving
		if (!direction.equals("stand") && !collisionOn) {
		    spriteCounter++;
		    if (spriteCounter > 10) {
		        if (spriteNum == 1) spriteNum = 3;
		        else if (spriteNum == 3) spriteNum = 2;
		        else if (spriteNum == 2) spriteNum = 1;
		        spriteCounter = 0;
		    }
		} else {
		    // Set to standing frame
		    spriteNum = 3;
		}
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		int screenX = worldX - gp.player.worldX + gp.player.screenX; 
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
		   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
	       worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
		   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
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
			
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);	
		}
	}
	
	public BufferedImage setup(String imagePath) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
}
