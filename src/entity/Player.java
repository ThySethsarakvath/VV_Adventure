package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

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
		
		worldX = gp.tileSize * 58;
		worldY = gp.tileSize * 99;
		speed = 4;
		direction = "down";
		
	}
	
	public void getPlayerImage() {
		
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/Steve_up1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/Steve_up2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/Steve_down1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/Steve_down2.png"));
			
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/Steve_left1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/Steve_left2.png"));
			
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/Steve_right1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/Steve_right2.png"));
			
			upStand = ImageIO.read(getClass().getResourceAsStream("/player/Steve_up_stand.png"));
			downStand = ImageIO.read(getClass().getResourceAsStream("/player/Steve_down_stand.png"));
			leftStand = ImageIO.read(getClass().getResourceAsStream("/player/Steve_left_stand.png"));
			rightStand = ImageIO.read(getClass().getResourceAsStream("/player/Steve_right_stand.png"));

			
		} catch(IOException e) {
			e.getStackTrace();
		}
		
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

		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		
	}
	
}
