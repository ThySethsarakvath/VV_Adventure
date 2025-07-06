package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_speedBoot extends SuperObject {
	
	GamePanel gp;

	public OBJ_speedBoot(GamePanel gp) {
		
		name = "speed_boot";
		
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream("/objects/speed_boot.png"));
			uTool.scaleImage(image, gp.tileSize, gp.tileSize);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		collision = true;
	}
	
}

