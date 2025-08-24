package tile;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Map extends TileManager {

	GamePanel gp;
	BufferedImage worldMap[];
	public boolean miniMapOn = false;
	
	public Map(GamePanel gp) {
		super(gp);
		this.gp = gp;
		createWorldMap();
	}

	public void createWorldMap() {
		
		worldMap = new BufferedImage[gp.MAX_MAP];
		int miniMapWidth = 200;  // Fixed smaller size
        int miniMapHeight = 200;
		
		for(int i = 0; i < gp.MAX_MAP; i++) {
			
			worldMap[i] = new BufferedImage(miniMapWidth, miniMapHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = (Graphics2D)worldMap[i].createGraphics();
			
			double scaleX = (double)miniMapWidth / (gp.maxWorldCol * gp.TILE_SIZE);
            double scaleY = (double)miniMapHeight / (gp.maxWorldRow * gp.TILE_SIZE);
            
            g2.scale(scaleX, scaleY);
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				int tileNum = mapTileNum[i][col][row];
				int x = gp.TILE_SIZE * col;
				int y = gp.TILE_SIZE * row;
				g2.drawImage(tile[tileNum].image, x, y, null);
				
				col++;
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			g2.dispose();
		}
	}
	
	public void drawFullMapScreen(Graphics2D g2) {
		
		// Background Color
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
		
		// Draw Map
		int width = 500;
		int height = 500;
		int x = (gp.SCREEN_WIDTH / 2) - (width / 2);
		int y = (gp.SCREEN_HEIGHT / 2) - (height / 2);
		g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);
		
		// Draw Player
		double scale = (double)(gp.TILE_SIZE * gp.maxWorldCol)/width;
		int playerX = (int)(x + gp.player.worldX/scale);
		int playerY = (int)(y + gp.player.worldY/scale);
		int playerSize = (int)(gp.TILE_SIZE/2);
		g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize, null);
		
		// Hint
		g2.setFont(gp.ui.PressStart2P.deriveFont(11f));
		g2.setColor(Color.white);
		g2.drawString("Press M to close", 570, 565);
	}
	
	public void drawMiniMap(Graphics2D g2) {
		
		if(miniMapOn == true) {
			
			// Draw Map
			int width = 200;
			int height = 200;
			int x = gp.SCREEN_WIDTH - width - 50;
			int y = 50;
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);
			
			// Draw Player
			double scale = (double)(gp.TILE_SIZE * gp.maxWorldCol)/width;
			int playerX = (int)(x + gp.player.worldX/scale);
			int playerY = (int)(y + gp.player.worldY/scale);
			int playerSize = (int)(gp.TILE_SIZE/3);
			g2.drawImage(gp.player.down1, playerX - 6, playerY - 6, playerSize, playerSize, null);
			
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
		
	}
}
