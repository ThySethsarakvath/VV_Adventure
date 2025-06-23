package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity {
	
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
		
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.width = 32;
		solidArea.height = 32;
		
		setDefaultValues();
		getPlayerImage();
		
	}
	
	// Player coordinate
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 5;
		worldY = gp.tileSize * 5;
		speed = 4;
		direction = "down";
		
	}
	
	public void getPlayerImage() {
		
		up1 = setup("Steve_up1");
		up2 = setup("Steve_up2");
		down1 = setup("Steve_down1");
		down2 = setup("Steve_down2");
		left1 = setup("Steve_left1");
		left2 = setup("Steve_left2");
		right1 = setup("Steve_right1");
		right2 = setup("Steve_right2");
		upStand = setup("Steve_up_stand");
		downStand = setup("Steve_down_stand");
		leftStand = setup("Steve_left_stand");
		rightStand = setup("Steve_right_stand");
	}
	
	public BufferedImage setup(String imageName) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage image =null;
		
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream("/player/"+imageName+".png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
	
		}
		
		return image;
	}
	public void update() {
		
		if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
			
			//The Y-axis decreases as we move up
			if (keyH.upPressed) {
	            direction = "up";
			}
			//The Y-axis increases as we move down
			else if (keyH.downPressed) {
	            direction = "down";        
			}
			//The X-axis decreases as we move left.
			else if (keyH.leftPressed) {
	            direction = "left";      
			}
			//The X-axis increases as we move right
			else if (keyH.rightPressed) {
	            direction = "right";    
			}
			
			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			// IF COLLISION IS FALSE, PLAYER CAN MOVE
			if(collisionOn == false) {
				switch(direction) {
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
	            if (spriteNum == 1) spriteNum = 3;
	            else if (spriteNum == 3) spriteNum = 2;
	            else if (spriteNum == 2) spriteNum = 1;
	            spriteCounter = 0;
	        }
		}
		else {
			// No key is pressed, so reset to standing
			spriteNum = 3;
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

		g2.drawImage(image, screenX, screenY, null);
		
	}
	
}
