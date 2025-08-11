package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Arc2D.Float;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import ai.Node;
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
	public boolean onPath = false;

	// COUNTER
	public int actionLockCounter = 0;
	public int invinCounter = 0;
	public int spriteCounter = 0;
	int dieCounter;
	int hpBarCounter = 0;
	public int shotCounter;

	// CHARACTER ATTRIBUTES
	public String name;
	public int maxLife;
	public int mana;
	public int maxMana;
	public int life;
	public int speed;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public Entity currentWeapon;
	public Entity currentShield;
	public Entity currentBall;
	public Projectile pro;

	// Item Attributes
	public int attackValue;
	public int defenseValue;
	public String description = "";
	public int useCost;

	public int type; // 0 = player , 1 = npc, 2 = monster
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_zombie = 2;
	public final int type_dsword = 3;
	public final int type_wsword = 4;
	public final int type_shield = 5;
	public final int type_consumable = 6;
	public final int type_firecharge = 7;
	public final int type_skeleton = 8;
	public final int type_axe = 9;

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

	public void use(Entity entity) {

	}

	public Color getParticleColor() {
		Color color = null;
		return color;
	}

	public int getParticleSize() {
		int size = 0;
		return size;
	}

	public int getParticleSpeed() {
		int speed = 0;
		return speed;
	}

	public int getParticleMaxLife() {
		int maxLife = 0;
		return maxLife;
	}

	public void generateParticle(Entity generator, Entity target) {

		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxLife = generator.getParticleMaxLife();

		Particle p1 = new Particle(gp, generator, color, size, speed, maxLife, -2, -1);
		Particle p2 = new Particle(gp, generator, color, size, speed, maxLife, 2, -1);
		Particle p3 = new Particle(gp, generator, color, size, speed, maxLife, -2, 1);
		Particle p4 = new Particle(gp, generator, color, size, speed, maxLife, 2, 1);
		gp.particleList.add(p1);
		gp.particleList.add(p2);
		gp.particleList.add(p3);
		gp.particleList.add(p4);
	}

	public void checkCollision() {

		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.monster);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.iTile);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);

		if (this.type == type_zombie && contactPlayer == true) {
			damagePlayer(attack);
		}
	}

	public void update() {
		setAction();
		checkCollision();

		if (!attacking && !collisionOn && !direction.equals("stand")) {
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
		if (!attacking) {
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
		}

		if (invincible == true) {
			invinCounter++;
			if (invinCounter > 40) {
				invincible = false;
				invinCounter = 0;
			}
		}
		if (shotCounter < 30) {
			shotCounter++;
		}
	}

	public void damagePlayer(int attack) {
	    if (!gp.player.invincible) {
	        gp.playSE(2);
	        int damage = attack - gp.player.defense;
	        if (damage < 1) damage = 1; // Ensure minimum damage
	        gp.player.life -= damage;
	        gp.player.invincible = true;
	    }
	}

	public void draw(Graphics2D g2) {
		if (!alive)
			return;
		
		BufferedImage image = null;
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;

		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
				&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
				&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
				&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

			switch (direction) {
			case "up":
				if (attacking == true) {
					if (spriteCounter <= 20) {
						image = aUp1;
					} else {
						image = aUp2;
					}
				}
				if (attacking == false) {
					if (spriteNum == 1)
						image = up1;
					if (spriteNum == 2)
						image = up2;
					if (spriteNum == 3)
						image = upStand;
				}
				break;
			case "down":
				if (attacking == true) {
					if (spriteCounter <= 20) {
						image = aDown1;
					} else {
						image = aDown2;
					}
				}
				if (attacking == false) {
					if (spriteNum == 1)
						image = down1;
					if (spriteNum == 2)
						image = down2;
					if (spriteNum == 3)
						image = downStand;
				}
				break;
			case "left":
				if (attacking == true) {
					if (spriteCounter <= 20) {
						image = aLeft1;
					} else {
						image = aLeft2;
					}
				}
				if (attacking == false) {
					if (spriteNum == 1)
						image = left1;
					if (spriteNum == 2)
						image = left2;
					if (spriteNum == 3)
						image = leftStand;
				}
				break;
			case "right":
				if (attacking == true) {
					if (spriteCounter <= 20) {
						image = aRight1;
					} else {
						image = aRight2;
					}
				}
				if (attacking == false) {
					if (spriteNum == 1)
						image = right1;
					if (spriteNum == 2)
						image = right2;
					if (spriteNum == 3)
						image = rightStand;
				}
				break;
			}

			// Monster HP bar
			if ((type == type_zombie || type == type_skeleton) && hpBarOn == true) {
				double oneScale = (double) gp.tileSize / maxLife;
				double hpBarValue = oneScale * life;

				g2.setColor(new Color(35, 35, 35));
				g2.fillRect(screenX - 1, screenY - 16, gp.tileSize, 10);

				g2.setColor(new Color(255, 0, 0));
				g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);

				hpBarCounter++;

				if (hpBarCounter > 600) {
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

			g2.drawImage(image, screenX, screenY, null);

			// Reset Alpha
			changeAlpha(g2, 1f);
		}
		
		// solid area debugging
//		g2.setColor(Color.RED);
//	    g2.drawRect(screenX + solidArea.x, 
//	                screenY + solidArea.y, 
//	                solidArea.width, 
//	                solidArea.height);
	}

	public void deadAnimation(Graphics2D g2) {
		dieCounter++;

		int i = 5;
		if (dieCounter <= i) {
			changeAlpha(g2, 0f);
		}
		if (dieCounter > i && dieCounter <= i * 2) {
			changeAlpha(g2, 1f);
		}
		if (dieCounter > i * 2 && dieCounter <= i * 3) {
			changeAlpha(g2, 0f);
		}
		if (dieCounter > i * 3 && dieCounter <= i * 4) {
			changeAlpha(g2, 1f);
		}
		if (dieCounter > i * 4 && dieCounter <= i * 5) {
			changeAlpha(g2, 0f);
		}
		if (dieCounter > i * 5 && dieCounter <= i * 6) {
			changeAlpha(g2, 1f);
		}
		if (dieCounter > i * 6 && dieCounter <= i * 7) {
			changeAlpha(g2, 0f);
		}
		if (dieCounter > i * 7 && dieCounter <= i * 8) {
			changeAlpha(g2, 1f);
		}

		if (dieCounter > i * 8) {
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
	
	public void searchPath(int goalCol, int goalRow) {
	    int startCol = (worldX + solidArea.x) / gp.tileSize;
	    int startRow = (worldY + solidArea.y) / gp.tileSize;

	    gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow, this);

	    if (gp.pFinder.search()) {
	        // Follow exact path coordinates
	        int nextCol = gp.pFinder.pathList.get(0).col;
	        int nextRow = gp.pFinder.pathList.get(0).row;
	        
	        // Calculate exact target position
	        int nextX = nextCol * gp.tileSize;
	        int nextY = nextRow * gp.tileSize;
	        
	        // Check if we've reached the current path node
	        if (Math.abs(worldX - nextX) < speed && Math.abs(worldY - nextY) < speed) {
	            // Remove reached node from path
	            if (!gp.pFinder.pathList.isEmpty()) {
	                gp.pFinder.pathList.remove(0);
	            }
	        }
	        
	        // Direct movement toward exact path coordinates
	        if (worldX < nextX) {
	            direction = "right";
	        } else if (worldX > nextX) {
	            direction = "left";
	        } else if (worldY < nextY) {
	            direction = "down";
	        } else if (worldY > nextY) {
	            direction = "up";
	        }
	        
	        // If path is empty, we've reached the goal
	        if (gp.pFinder.pathList.isEmpty()) {
	            onPath = false;
	        }
	    } else {
	        onPath = false; // No path found
	    }
	}
	
//	public void followPath() {
//	    if (gp.pFinder.pathList.size() > 0) {
//	        Node nextNode = gp.pFinder.pathList.get(0);
//	        int nextX = nextNode.col * gp.tileSize;
//	        int nextY = nextNode.row * gp.tileSize;
//
//	        if (worldX < nextX) {
//	            direction = "right";
//	        } else if (worldX > nextX) {
//	            direction = "left";
//	        } else if (worldY < nextY) {
//	            direction = "down";
//	        } else if (worldY > nextY) {
//	            direction = "up";
//	        }
//
//	        // Remove node if reached
//	        if (worldX == nextX && worldY == nextY) {
//	            gp.pFinder.pathList.remove(0);
//	        }
//	    } else {
//	        onPath = false; // Finished
//	    }
//	}


}
