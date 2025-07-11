package main;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import object.OBJ_Door;

import javax.imageio.ImageIO;

import javax.sound.sampled.*;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font PressStart2P;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	
	// For dialogue attributes
	public String currentDialogue = "";
	BufferedImage dialogueBoxImage;
	
	// For dialogue text appear letter by letter
	public String displayedText = ""; // Text currently shown
	public int charIndex = 0; 
	public int textTimer = 0;
	int textSpeed = 3; // Delay between characters (smaller = faster)
	public boolean textCompleted = false;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		try {
			InputStream is = getClass().getResourceAsStream("/font/PressStart2P-Regular.ttf");
			PressStart2P = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
		try {
			dialogueBoxImage = ImageIO.read(getClass().getResourceAsStream("/dialogues/dialogue_wood.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void playBlip() {
		try {
			// Input typing sound later
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/sound/typing.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
		
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(PressStart2P);
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
		
		g2.drawImage(dialogueBoxImage, boxX, boxY, boxWidth, boxHeight, null); // adjust x/y to resize wood dialogue
		
		// Position number is changeable here
		int textX = boxX + 55; 
		int textY = boxY + 70;
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 14F));
		
		// Typing animation logic
		if(!textCompleted) {
			textTimer++;
			if(textTimer >= textSpeed) {
				if(charIndex < currentDialogue.length()) {
					char currentChar = currentDialogue.charAt(charIndex);
					displayedText += currentChar;
					charIndex++;
					textTimer = 0;
					
					if(currentChar != ' ' && currentChar != '\n') {
						playBlip(); // Only play sound for visible letters
					}
				} else {
					textCompleted = true;
				}
			}
		}
		
		// Shadow
		for(String line : displayedText.split("\n")) {
			g2.setColor(Color.black);
			g2.drawString(line, textX + 2, textY + 2);
			
			// Main text
			g2.setColor(Color.white);
			g2.drawString(line, textX, textY);	
			
			textY += 40;
		}
	
	}
	
	public int getXforCenteredText(String text) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - length / 2;
		
		return x;
	}
	
}
