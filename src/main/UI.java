package main;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import object.OBJ_Door;

import javax.imageio.ImageIO;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font arial_30;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	
	// For dialogue attributes
	public String currentDialogue = "";
	BufferedImage dialogueBoxImage;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_30 = new Font("Arial", Font.PLAIN, 30);
		
		try {
			dialogueBoxImage = ImageIO.read(getClass().getResourceAsStream("/dialogues/dialogue_wood.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
		
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(arial_30);
		g2.setColor(Color.white);
		
		// PLAY STATE
		if(gp.gameState == gp.playState) {
			// Do playState stuff later
		}
		
		// PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		
		// DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
		}
	}
	
	public void drawPauseScreen() {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		String text = "PAUSED";
		
		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 2;
		
		g2.drawString(text, x, y);
	
	}
	
	public void drawDialogueScreen() {
		
		// WINDOW
		int boxX = gp.tileSize * 2;
		int boxY = (gp.tileSize / 2);
		int boxWidth = gp.screenWidth - (gp.tileSize * 4);
		int boxHeight = gp.tileSize * 4;
		
//		drawSubWindow(x, y, width, height);
		g2.drawImage(dialogueBoxImage, boxX, boxY, boxWidth, boxHeight, null); // adjust x/y to resize wood dialogue
		
		// Position number is changeable here
		int textX = boxX + 55; 
		int textY = boxY + 70;
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
		
		for(String line : currentDialogue.split("\n")) {
			// Shadow
			g2.setColor(Color.black);
			g2.drawString(line, textX + 2, textY + 2);
			
			// Main text
			g2.setColor(Color.white);
			g2.drawString(line, textX, textY);
			textY += 40;
		}
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0, 0, 0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
		
	}
	
	public int getXforCenteredText(String text) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - length / 2;
		
		return x;
	}
	
}
