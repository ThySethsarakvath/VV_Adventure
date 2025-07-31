package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Fireball extends Projectile {
	
	GamePanel gp;
	
	public OBJ_Fireball(GamePanel gp) {
		super(gp);
		this.gp = gp;
		name = "Fireball";
		speed = 5;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive = false;
		getImage();
		// TODO Auto-generated constructor stub
	}
	
	public void getImage() {
		up1 = setup("/projectile/fireball_up1",gp.tileSize,gp.tileSize);
		up2 = setup("/projectile/fireball_up2",gp.tileSize,gp.tileSize);
		down1 = setup("/projectile/fireball_down1",gp.tileSize,gp.tileSize);
		down2 = setup("/projectile/fireball_down2",gp.tileSize,gp.tileSize);
		left1 = setup("/projectile/fireball_left1",gp.tileSize,gp.tileSize);
		left2 = setup("/projectile/fireball_left2",gp.tileSize,gp.tileSize);
		right1 = setup("/projectile/fireball_right1",gp.tileSize,gp.tileSize);
		right2 = setup("/projectile/fireball_right2",gp.tileSize,gp.tileSize);

	}

}
