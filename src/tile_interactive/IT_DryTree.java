package tile_interactive;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;

public class IT_DryTree extends InteractiveTile {
	
	GamePanel gp;

	public IT_DryTree(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		this.worldX = gp.TILE_SIZE * col;
		this.worldY = gp.TILE_SIZE * row;
		
		down1 = setup("/tiles_interactive/Dry_tree", gp.TILE_SIZE, gp.TILE_SIZE);
		destructible = true;
		life = 3;
	}
	
	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;
		
		if(entity.currentWeapon.type == type_axe) {
			isCorrectItem = true;
		}
		
		return isCorrectItem;
	}
	
	// Cut tree sound
	public void playSE() {
//		gp.playSE(15);
	}
	
	public InteractiveTile getDestroyedForm() {
		
		InteractiveTile tile = new IT_Trunk(gp, worldX/gp.TILE_SIZE, worldY/gp.TILE_SIZE);
		return tile;
		
	}

	public Color getParticleColor() {
		Color color = new Color(65, 50, 30);
		return color;
	}
	
	public int getParticleSize() {
		int size = 6; // 6px
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
