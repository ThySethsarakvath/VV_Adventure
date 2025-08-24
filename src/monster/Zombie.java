package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Emerald;
import object.OBJ_Fireball;
import object.OBJ_Firecharge;
import object.OBJ_healingP;

public class Zombie extends Entity {

	GamePanel gp;

	public Zombie(GamePanel gp) {
		super(gp);
		this.gp = gp;
		

		type = type_zombie;
		name = "Zombie";
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 6;
		life = maxLife;
		attack = 2;
//		pro = new OBJ_Fireball(gp);

		solidArea.x = 8; // X-offset within zombie sprite
		solidArea.y = 16; // Y-offset
		solidArea.width = 32; // Hitbox width
		solidArea.height = 32; // Hitbox height

		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		getImage();
	}

	public void getImage() {

		up1 = setup("/monster/zombie_up1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/zombie_up2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/zombie_down1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/zombie_down2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/zombie_left1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/zombie_left2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/zombie_right1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/zombie_right2", gp.tileSize, gp.tileSize);
		upStand = setup("/monster/zombie_up", gp.tileSize, gp.tileSize);
		downStand = setup("/monster/zombie_down", gp.tileSize, gp.tileSize);
		leftStand = setup("/monster/zombie_left", gp.tileSize, gp.tileSize);
		rightStand = setup("/monster/zombie_right", gp.tileSize, gp.tileSize);

	}

	public void setAction() {

		boolean contactPlayer = gp.cChecker.checkPlayer(this);

		if (contactPlayer && shotCounter >= 30 && !gp.player.invincible) {
			damagePlayer(attack);
			shotCounter = 0;
		}

		if (onPath == true) {
			
			checkStopChasingOrNot(gp.player,15,100);
			
			checkCollision();
			if (collisionOn) {
				gp.pFinder.pathList.clear();
				searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
			} else {
				searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
			}
		} else {
			checkStartChasingOrNot(gp.player,5,100);
			getRandomDirection();
			}
			
		}
	

	public void damageReaction() {

		actionLockCounter = 0;
		onPath = true;
	}

	public void checkDrop() {
		// cast a die
		int i = new Random().nextInt(100) + 1;

		// set dropping
		if (i < 50) {
			dropItem(new OBJ_Emerald(gp));
		}
		if (i >= 50 && i < 75) {
			dropItem(new OBJ_Firecharge(gp));
		}
		if (i >= 75 && i < 100) {
			dropItem(new OBJ_healingP(gp));
		}
	}
}
