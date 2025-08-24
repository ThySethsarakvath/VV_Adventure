package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Emerald;
import object.OBJ_Fireball;
import object.OBJ_Firecharge;
import object.OBJ_healingP;

public class Ice_Golem extends Entity {

	GamePanel gp;
	public static final String monName = "Ice Golem";
	public Ice_Golem(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = type_zombie;
		name = monName;
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 6;
		life = maxLife;
		attack = 5;
		knockBackPower = 5;
//		pro = new OBJ_Fireball(gp);

		solidArea.x = 8; // X-offset within zombie sprite
		solidArea.y = 16; // Y-offset
		solidArea.width = 32; // Hitbox width
		solidArea.height = 32; // Hitbox height

		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		attackArea.width = 48;
		attackArea.height = 48;
		motion1_duration = 40;
		motion2_duration = 85;
		getImage();
		getAttackImage();
	}

	public void getImage() {
		int i = 5;
	    
	    up1 = setup("/monster/Ice_golem_up1",gp.tileSize*i,gp.tileSize*i);
	    up2 = setup("/monster/Ice_golem_up2",gp.tileSize*i,gp.tileSize*i);
	    down1 = setup("/monster/Ice_golem_down1",gp.tileSize*i,gp.tileSize*i);
	    down2 = setup("/monster/Ice_golem_down2",gp.tileSize*i,gp.tileSize*i);
	    left1 = setup("/monster/Ice_golem_left1",gp.tileSize*i,gp.tileSize*i);
	    left2 = setup("/monster/Ice_golem_left2",gp.tileSize*i,gp.tileSize*i);
	    right1 = setup("/monster/Ice_golem_right1",gp.tileSize*i,gp.tileSize*i);
	    right2 = setup("/monster/Ice_golem_right2",gp.tileSize*i,gp.tileSize*i);
	    upStand = setup("/monster/Ice_golem_upStand",gp.tileSize*i,gp.tileSize*i);
	    downStand = setup("/monster/Ice_golem_downStand",gp.tileSize*i,gp.tileSize*i);
	    leftStand = setup("/monster/Ice_golem_leftStand",gp.tileSize*i,gp.tileSize*i);
	    rightStand = setup("/monster/wIce_golem_rightStand",gp.tileSize*i,gp.tileSize*i);
	    
	    
	  }
	  public void getAttackImage() {
	    
	    aUp1 = setup("/monster/wither_au1", gp.tileSize, gp.tileSize * 2); // 16 x 32
	    aUp2 = setup("/monster/wither_au2", gp.tileSize, gp.tileSize * 2);
	    aDown1 = setup("/monster/wither_ad1", gp.tileSize, gp.tileSize * 2);
	    aDown2 = setup("/monster/wither_ad2", gp.tileSize, gp.tileSize * 2);
	    aRight1 = setup("/monster/wither_ar1", gp.tileSize * 2, gp.tileSize);
	    aRight2 = setup("/monster/wither_ar2", gp.tileSize * 2, gp.tileSize);
	    aLeft1 = setup("/monster/wither_al1", gp.tileSize * 2, gp.tileSize);
	    aLeft2 = setup("/monster/wither_al2", gp.tileSize * 2, gp.tileSize);
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
		
		if(attacking == false) {
			checkAttackOrNot(30,gp.tileSize*4,gp.tileSize);
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
