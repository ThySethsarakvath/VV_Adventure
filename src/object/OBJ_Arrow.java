package object;

import java.awt.Color;

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
		
		up1 = setup("/projectile/arrow_up",gp.TILE_SIZE,gp.TILE_SIZE);
		up2 = setup("/projectile/arrow_up",gp.TILE_SIZE,gp.TILE_SIZE);
		down1 = setup("/projectile/arrow_down",gp.TILE_SIZE,gp.TILE_SIZE);
		down2 = setup("/projectile/arrow_down",gp.TILE_SIZE,gp.TILE_SIZE);
		left1 = setup("/projectile/arrow_left",gp.TILE_SIZE,gp.TILE_SIZE);
		left2 = setup("/projectile/arrow_left",gp.TILE_SIZE,gp.TILE_SIZE);
		right1 = setup("/projectile/arrow_right",gp.TILE_SIZE,gp.TILE_SIZE);
		right2 = setup("/projectile/arrow_right",gp.TILE_SIZE,gp.TILE_SIZE);

	}
	
	public Color getParticleColor() {
		Color color = new Color(128, 128, 128);
		return color;
	}
	
	public int getParticleSize() {
		int size = 10; 
		return size;
	}
	
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	
	public int getParticleMaxLife() {
		int maxLife = 20;
		return maxLife;
	}

}
