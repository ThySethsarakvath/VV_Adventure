package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[50];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/world01.txt");
	}
	
	public void getTileImage() {
		
		// unused placeholder
		setup(0,"grass0",false);
		setup(1,"grass0",false);
		setup(2,"grass0",false);
		setup(3,"grass0",false);
		setup(4,"grass0",false);
		setup(5,"grass0",false);
		setup(6,"grass0",false);
		setup(7,"grass0",false);
		setup(8,"grass0",false);
		setup(9,"grass0",false);
		
		//Tiles
		setup(10,"grass",false);
		setup(11,"stone",true);
		setup(12,"water",true);
		setup(13,"dirt",false);
		setup(14,"dirt_path",false);
		setup(15,"sand",false);
		setup(16,"sand2",false);
		setup(17,"tree",true);
		setup(18,"tree_snow",true);
		setup(19,"snow",false);
		setup(20,"lava",true);
		setup(21,"brick",true);
		setup(22,"down_corner_outlier1",true);
		setup(23,"down_corner_outlier2",true);
		setup(24,"down_middle_outlier",true);
		setup(25,"left_middle_outlier",true);
		setup(26,"right_middle_outlier",true);
		setup(27,"up_corner_outlier1",true);
		setup(28,"up_corner_outlier2",true);
		setup(29,"up_middle_outlier",true);
		setup(30,"corner1",true);
		setup(31,"corner2",true);
		setup(32,"corner3",true);
		setup(33,"corner4",true);
		setup(34,"dirt_path1",false);
		setup(35,"dirt_path2",false);
		setup(36,"dirt_path3",false);
		setup(37,"dirt_path4",false);
		setup(38,"dirt_path5",false);
		setup(39,"dirt_path6",false);
		setup(40,"dirt_path7",false);
		setup(41,"dirt_path8",false);
		setup(42,"dirt_path9",false);
		setup(43,"dirt_path10",false);
		setup(44,"dirt_path11",false);
		setup(45,"dirt_path12",false);
		
	}
	
	public void setup(int index, String image, boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
		try {
			
			tile[index] =new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+image+".png"));
			tile[index].image = uTool.scaleImage(tile[index].image,gp.tileSize,gp.tileSize);
			tile[index].collision = collision;
			
		}catch(IOException e) {
			
		}
	}
	
//	public void loadMap(String filePath) {
//		
//		try {
//			
//			InputStream is = getClass().getResourceAsStream(filePath);
//			BufferedReader br = new BufferedReader(new InputStreamReader(is));
//			
//			int col = 0;
//			int row = 0;
//			
//			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
//				
//				String line = br.readLine();
//				
//				while(col < gp.maxWorldCol) {
//					String numbers[] = line.split(" ");
//					
//					int num = Integer.parseInt(numbers[col]);
//					
//					mapTileNum[col][row] =num;
//					col++;
//				}
//				
//				if(col == gp.maxWorldCol) {
//					col =0;
//					row++;
//				}
//			}
//			
//		}catch(Exception e){
//			
//		}
//	}
	
	public void loadMap(String filePath) {
	    try {
	        InputStream is = getClass().getResourceAsStream(filePath);
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));

	        int row = 0;

	        while (row < gp.maxWorldRow) {
	            String line = br.readLine(); // read one line from map file
	            if (line == null) break; // safety: stop if file is shorter than expected

	            String numbers[] = line.split(" ");

	            for (int col = 0; col < gp.maxWorldCol; col++) {
	                int num = Integer.parseInt(numbers[col]);
	                mapTileNum[col][row] = num;
	            }
	            row++;
	        }

	        br.close(); // close the reader after reading
	    } catch (Exception e) {
	        e.printStackTrace(); // print error if file not found or bad format
	    }
	}

	
	public void draw(Graphics2D g2) { // Draw world map, camera setting
		
//		g2.drawImage(tile[0].image,0,0,gp.tileSize,gp.tileSize,null);
//		g2.drawImage(tile[1].image,48,0,gp.tileSize,gp.tileSize,null);
//		g2.drawImage(tile[2].image,96,0,gp.tileSize,gp.tileSize,null);
		
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize; // For example worldX = worldCol++ x 48
			int worldY = worldRow * gp.tileSize; // For example worldY = worldRow++ x 48
			/*
			 	+ gp.player.screenX, because we want the player still origin at the center 
			 	in your screen while you are walking
			 */
			int screenX = worldX - gp.player.worldX + gp.player.screenX; 
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			/*
			 	Avoiding draw world that far away from screen
			 		- Improve performance with larger map ex. 10000 x 10000, 
			 		  it not draw 10000 x 10000 at the same time 
			 */
			
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
		       worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
				
			}
		
			worldCol++;
			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow ++;
			}
		}
	}
}
