package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Arrow extends Projectile {
	
	GamePanel gp;
	
	public OBJ_Arrow(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name = "Arrow";
		speed = 8;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive = false;
		getImage();
		// TODO Auto-generated constructor stub
	}
	
	public void getImage() {
		
		up1 = setup("/projectile/arrow_up",gp.tileSize,gp.tileSize);
		up2 = setup("/projectile/arrow_up",gp.tileSize,gp.tileSize);
		down1 = setup("/projectile/arrow_down",gp.tileSize,gp.tileSize);
		down2 = setup("/projectile/arrow_down",gp.tileSize,gp.tileSize);
		left1 = setup("/projectile/arrow_left",gp.tileSize,gp.tileSize);
		left2 = setup("/projectile/arrow_left",gp.tileSize,gp.tileSize);
		right1 = setup("/projectile/arrow_right",gp.tileSize,gp.tileSize);
		right2 = setup("/projectile/arrow_right",gp.tileSize,gp.tileSize);

	}

}
