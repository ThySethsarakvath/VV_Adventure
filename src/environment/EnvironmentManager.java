package environment;

import java.awt.Graphics2D;

import main.GamePanel;

public class EnvironmentManager {

	GamePanel gp;
	Lighting lighting;
	
	public EnvironmentManager(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setup() {
		
		// param 2: shouldn't be greater than our screen width or height
		lighting = new Lighting(gp, 576);
		
	}
	
	public void draw(Graphics2D g2) {
		
		lighting.draw(g2);
		
	}
	
}
