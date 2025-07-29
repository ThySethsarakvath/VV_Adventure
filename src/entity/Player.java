package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Shield;
import object.OBJ_Sword;

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
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxinventorySize = 21;

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
		getPlayerAttackImage();
		setItems();
	}

	// Player coordinate
	public void setDefaultValues() {

		worldX = gp.tileSize * 28;
		worldY = gp.tileSize * 18;
		speed = 4;
		direction = "down";

		// Player state
		// 2 lives = full heart
		// 1 life = half heart

		maxLife = 10; // 10 lives = 5 hearts
		life = maxLife;
		level =1;
		strength = 1; // the more strength , the more damage;
		dexterity = 1; // the more dex, the less receives;
		exp =0;
		nextLevelExp = 5;
		coin = 0;
		currentWeapon = new OBJ_Sword(gp);
		currentShield = new OBJ_Shield(gp);
		attack = getAttack(); // total attack base on strength and weapon
		defense = getDefense(); // total defense base on dexterity and shield
	}
	
	public void setItems() {
		inventory.add(currentShield);
		inventory.add(currentWeapon);
	}
	
	public int getAttack() {
		attackArea = currentWeapon.attackArea;
		return attack = strength * currentWeapon.attackValue;
	}
	
	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
	}
	
	public void setDefaultPositions() {
		
		worldX = gp.tileSize * 28;
		worldY = gp.tileSize * 18;
		direction = "down";
		
	}
	
	// public void restoreLifeAndMana()
	
	public void restoreLife() {
		
		life = maxLife;
		invincible = false;
		
	}

	public void getPlayerImage() {

		up1 = setup("/player/Steve_up1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/Steve_up2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/Steve_down1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/Steve_down2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/Steve_left1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/Steve_left2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/Steve_right1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/Steve_right2", gp.tileSize, gp.tileSize);
		upStand = setup("/player/Steve_up_stand", gp.tileSize, gp.tileSize);
		downStand = setup("/player/Steve_down_stand", gp.tileSize, gp.tileSize);
		leftStand = setup("/player/Steve_left_stand", gp.tileSize, gp.tileSize);
		rightStand = setup("/player/Steve_right_stand", gp.tileSize, gp.tileSize);
	}

	public void getPlayerAttackImage() {
		
		if(currentWeapon.type == type_dsword) {
			aUp1 = setup("/player/Steve_up_a1", gp.tileSize, gp.tileSize * 2); // 16 x 32
			aUp2 = setup("/player/Steve_up_a2", gp.tileSize, gp.tileSize * 2);
			aDown1 = setup("/player/Steve_down_a1", gp.tileSize, gp.tileSize * 2);
			aDown2 = setup("/player/Steve_down_a2", gp.tileSize, gp.tileSize * 2);
			aRight1 = setup("/player/Steve_right_a1", gp.tileSize * 2, gp.tileSize);
			aRight2 = setup("/player/Steve_right_a2", gp.tileSize * 2, gp.tileSize);
			aLeft1 = setup("/player/Steve_left_a1", gp.tileSize * 2, gp.tileSize);
			aLeft2 = setup("/player/Steve_left_a2", gp.tileSize * 2, gp.tileSize);
		}
		if(currentWeapon.type == type_wsword) {
			aUp1 = setup("/player/Steve_wood_up1", gp.tileSize, gp.tileSize * 2); // 16 x 32
			aUp2 = setup("/player/Steve_wood_up2", gp.tileSize, gp.tileSize * 2);
			aDown1 = setup("/player/Steve_wood_down1", gp.tileSize, gp.tileSize * 2);
			aDown2 = setup("/player/Steve_wood_down2", gp.tileSize, gp.tileSize * 2);
			aRight1 = setup("/player/Steve_wood_right2", gp.tileSize * 2, gp.tileSize);
			aRight2 = setup("/player/Steve_wood_right1", gp.tileSize * 2, gp.tileSize);
			aLeft1 = setup("/player/Steve_wood_left1", gp.tileSize * 2, gp.tileSize);
			aLeft2 = setup("/player/Steve_wood_left2", gp.tileSize * 2, gp.tileSize);
		}
		

	}

	public void update() {

		if(gp.keyH.fPressed) {
			attacking = true;
			gp.keyH.fPressed = false;
		}
		// 🗡️ Priority: Attack Mode
		if (attacking == true) {
			attacking(); // handles its own spriteNum logic
//			return; // skip movement and other updates during attack
		}

		// 🎮 Movement Input Handling
		else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

			// Update direction
			if (keyH.upPressed)
				direction = "up";
			else if (keyH.downPressed)
				direction = "down";
			else if (keyH.leftPressed)
				direction = "left";
			else if (keyH.rightPressed)
				direction = "right";

			// Collision checks
			collisionOn = false;
			gp.cChecker.checkTile(this);

			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);

			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNpc(npcIndex);

			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			interactNpc(monsterIndex);
			contactMonster(monsterIndex);

			// Only move if no collision
			if (!collisionOn && keyH.enterPressed == false) {
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
			
			gp.keyH.enterPressed = false;

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

		// ⚡ Speed boost timer
		if (speedBoosted) {
			speedTimer--;
			if (speedTimer <= 0) {
				speed -= 2;
				speedBoosted = false;
			}
		}

		// 🛡️ Invincibility frames
		if (invincible) {
			invinCounter++;
			if (invinCounter > 60) {
				invincible = false;
				invinCounter = 0;
			}
		}
		if(life <= 0) {
			gp.gameState = gp.gameOverState;
			gp.music.pause();
//			gp.playSE(); For game over sound
		}
	}

	public void attacking() {
		if (spriteNum == 2 && !attackSoundPlayed) {
	        gp.playSE(3);
	        attackSoundPlayed = true;
	    }
		spriteCounter++;

		if (spriteCounter <= 5) {
			spriteNum = 1;
		}
		if (spriteCounter > 5 && spriteCounter <= 25) {
			spriteNum = 2;
			
			// Save the current WorldX,Y, solidArea
			int currentX = worldX;
			int currentY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			// Adjust players world X,Y for attack Area
			switch(direction) {
			case "up": 
				worldY -=attackArea.height; 
				break;
			case "down":
				worldY += attackArea.width;
				break;
				
			case "left" :
				worldX -= attackArea.width;
				break;
			case "right" :
				worldX +=attackArea.height;
				break;
			}
			// attack area become solid area
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			
			// Check monster collision with the attack area
			int monsterIndex = gp.cChecker.checkEntity(this,gp.monster);
			damageMonster(monsterIndex);
			
			worldX = currentX;
			worldY = currentY;
			solidArea.width = solidAreaWidth;
			solidArea.height = attackArea.height;
			
		}
		if (spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
			attackSoundPlayed = false;
		}
	}

	public void pickUpObject(int i) {

		if (i != -1) {
			
			if(inventory.size() != maxinventorySize) {
				inventory.add(gp.obj[i]);
			}
			// gp.obj[i] = null; // delete object that player touched
			String objectName = gp.obj[i].name;

			switch (objectName) {
//			case "Door":
//				openDoor++;
//				gp.obj[i] = null;
//				gp.ui.showMessage("The door opened!");
//
//				break;
//			case "speed_boot":
//				if (!speedBoosted) {
//					gp.playSE(1);
//					speedBoosted = true;
//					speed += 2;
//					speedTimer = 180; // 180 frames = 3 seconds, since our FPS = 60
//					gp.obj[i] = null;
//				}
//				break;
			}
			gp.obj[i] = null;
		}
	}

	public void interactNpc(int i) {

		// 🗣️ Talk using Enter key
	    if (gp.keyH.enterPressed == true) {
	        if (i != -1) {
	            gp.gameState = gp.dialogueState;
	            gp.npc[i].speak();
	        }
	        gp.keyH.enterPressed = false;
	    }

	    // 🗡️ Attack using F key
	    if (gp.keyH.fPressed == true) {
//	    	gp.playSE(3);
	        attacking = true;
	        gp.keyH.fPressed = false;
	    }

	}

	public void contactMonster(int i) {

		if (i != -1) {
			if (invincible == false) {
				gp.playSE(2);
				life -= 1;
				invincible = true;
			}

		}
	}
	
	public void damageMonster(int i) {
		
		if(i != -1) {
		
			if(gp.monster[i].invincible == false) {
				
				int hurtSound = new java.util.Random().nextInt(2) + 4; // returns 4 or 5
		        gp.playSE(hurtSound);
		        
				gp.monster[i].life -=1;
				gp.monster[i].invincible =true;
				gp.monster[i].damageReaction();
				
				
				if(gp.monster[i].life <= 0) {
					gp.monster[i].die = true;
					
					gp.playSE(6);
				}
			}
		}
	}

	public void selectItem() {
		
		int itemIndex = gp.ui.getItemIndexOnSlot();
		
		if(itemIndex < inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);
			
//			if(selectedItem.type == type_dsword || selectedItem.type == type_wsword) {
//				currentWeapon = selectedItem;
//				attack = getAttack();
//			}
			
			if(selectedItem.type == type_dsword || selectedItem.type == type_wsword) {
				currentWeapon = selectedItem;
				attack = getAttack();
				getPlayerAttackImage();
			}
			
			if(selectedItem.type == type_shield) {
				currentShield = selectedItem;
				defense = getDefense();
			}
			if(selectedItem.type == type_consumable) {
				//later
			}
		}
		
	}
	
	public void draw(Graphics2D g2) {

//		g2.setColor(Color.white);
//		g2.fillRect(x, y, gp.tileSize , gp.tileSize);

		int tempScreenX = screenX;
		int tempScreenY = screenY;
		BufferedImage image = null;
//		boolean moving = keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed;

		switch (direction) {
		case "up":
			if (attacking == false) {
				if (spriteNum == 1) {
					image = up1;
				}
				if (spriteNum == 2) {
					image = up2;
				}
				if (spriteNum == 3) {
					image = upStand;
				}
			}
			if (attacking == true) {
				tempScreenY = screenY - gp.tileSize; // adjust y when attack upward
				if (spriteNum == 1) {
					image = aUp1;
				}
				if (spriteNum == 2) {
					image = aUp2;
				}
			}
			break;
		case "down":
			if (attacking == false) {
				if (spriteNum == 1) {
					image = down1;
				}
				if (spriteNum == 2) {
					image = down2;
				}
				if (spriteNum == 3) {
					image = downStand;
				}
			}
			if (attacking == true) {
				if (spriteNum == 1) {
					image = aDown1;
				}
				if (spriteNum == 2) {
					image = aDown2;
				}
			}
			break;
		case "left":
			if (attacking == false) {
				if (spriteNum == 1) {
					image = left1;
				}
				if (spriteNum == 2) {
					image = left2;
				}
				if (spriteNum == 3) {
					image = leftStand;
				}
			}
			if (attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if (spriteNum == 1) {
					image = aLeft1;
				}
				if (spriteNum == 2) {
					image = aLeft2;
				}
			}
			break;
		case "right":
			if (attacking == false) {
				if (spriteNum == 1) {
					image = right1;
				}
				if (spriteNum == 2) {
					image = right2;
				}
				if (spriteNum == 3) {
					image = rightStand;
				}
			}
			if (attacking == true) {
				if (spriteNum == 1) {
					image = aRight1;
				}
				if (spriteNum == 2) {
					image = aRight2;
				}
			}
			break;
		}

		// Image transparent when receive damage
		if (invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		}

		g2.drawImage(image, tempScreenX, tempScreenY, null);

		// Reset Alpha
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

//		g2.setFont(new Font("Arail",Font.PLAIN,26));
//		g2.setColor(Color.white);
//		g2.drawString("Invincible: "+invinCounter,10,400);
	}
}
