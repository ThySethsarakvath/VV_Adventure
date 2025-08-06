package main;

import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import object.OBJ_Heart;
import entity.Entity;

import javax.imageio.ImageIO;

import javax.sound.sampled.*;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font PressStart2P;
	BufferedImage full_heart,half_heart, empty_heart;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0;
	int counter = 0;
	
	// For dialogue attributes
	public String currentDialogue = "";
	BufferedImage dialogueBoxImage;
	BufferedImage checkBoxImage;
	BufferedImage checkmarkImage;
	BufferedImage longBoxImage;
	BufferedImage pointImage;
	BufferedImage bgImage;
	BufferedImage InImage;
	BufferedImage DeImage;
	
	// For dialogue text appear letter by letter
	public String displayedText = ""; // Text currently shown
	public int charIndex = 0; 
	public int textTimer = 0;
	int textSpeed = 3; // Delay between characters (smaller = faster)
	public boolean textCompleted = false;
	
	// For TITLE STATE attributes
	public int commandNum = 0;
	
	// FOR SUB SCREEN ATTRIBUTE
	int subState = 0;
	
	public int slotCol = 0;
	public int slotRow = 0;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		try {
			bgImage = ImageIO.read(getClass().getResourceAsStream("/background/background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			InImage = ImageIO.read(getClass().getResourceAsStream("/dialogues/inventory.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			DeImage = ImageIO.read(getClass().getResourceAsStream("/dialogues/D_Image.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
		
		try {
			checkBoxImage = ImageIO.read(getClass().getResourceAsStream("/dialogues/checkbox.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			longBoxImage = ImageIO.read(getClass().getResourceAsStream("/dialogues/longBox.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			pointImage = ImageIO.read(getClass().getResourceAsStream("/dialogues/point.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			checkmarkImage = ImageIO.read(getClass().getResourceAsStream("/dialogues/checkmark.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Entity heart = new OBJ_Heart(gp);
		full_heart = heart.image;
		half_heart = heart.image2;
		empty_heart = heart.image3;
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
		
		// TITLE STATE
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		
		// PLAY STATE
		if(gp.gameState == gp.playState) {
			// Do playState stuff later
			
			drawPlayerlife();
		}
		
		// PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
			drawPlayerlife();
		}
		
		// DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawDialogueScreen();
			drawPlayerlife();
		}
		
		// OPTIONS STATE
		if(gp.gameState == gp.optionsState) {
			drawOptionsScreen();
		}
		
		// GAMEOVER STATE
		if(gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}
		
		if(gp.gameState == gp.characterState) {
			drawCharacterScreen(); // for character state if want in the future
			drawInventory();
		}
		// transition state
		if(gp.gameState ==  gp.transitionState) {
			drawTransition();
		}
	}
	
	public void drawGameOverScreen(){
		
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50f));
		
		text = "Game Over";
		
		// Shadow
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.tileSize * 4;
		g2.drawString(text, x, y);
		
		// Main
		g2.setColor(Color.white);
		g2.drawString(text, x - 4, y - 4);
		
		// Retry
		g2.setFont(g2.getFont().deriveFont(25f));
		text = "Retry";
		x = getXforCenteredText(text);
		y += gp.tileSize * 4;
		g2.drawString(text, x, y);
		if(commandNum == 0) {
			g2.drawString(">", x - 40, y);
		}
		
		// Back to the title screen
		text = "Quit";
		x = getXforCenteredText(text);
		y += 55;
		g2.drawString(text, x, y);
		if(commandNum == 1) {
			g2.drawString(">", x - 40, y);
		}
		
	}
	
	public void drawTitleScreen() {
		
		g2.drawImage(bgImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
		
		// TITLE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 56F));
		String text = "VV_Adventure";
		int x = getXforCenteredText(text);
		int y = gp.tileSize * 3;
		
		// SHADOW
		g2.setColor(Color.gray);
		g2.drawString(text, x + 4, y + 4);
		
		// MAIN COLOR
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		
		// MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 26F));
		
		// IF WE ARE USING drawToTempScreen()
		//================= drawToTempScreen() =================
		String[] menu = {"NEW GAME", "LOAD GAME", "QUIT"};

	    y += gp.tileSize * 4;  // starting y for menu

	    for (int i = 0; i < menu.length; i++) {
	        text = menu[i];
	        x = getXforCenteredText(text);

	        if (i == commandNum) {
	            g2.drawString("> " + text + " <", x - gp.tileSize, y);
	        } else {
	            g2.drawString(text, x, y);
	        }

	        y += gp.tileSize * 1.5;
	    }
	    //======================================================
		
		// IF WE ARE USING paintComponent()
	    //================= paintComponent() =================
//		text = "NEW GAME";
//		x = getXforCenteredText(text); 
//		y += gp.tileSize * 4;
//		g2.drawString(text, x, y);
//		
//		if(commandNum == 0) {
//			g2.drawString(">", x - gp.tileSize , y);
//		}
//		
//		text = "LOAD GAME";
//		x = getXforCenteredText(text); 
//		y += gp.tileSize * 1.5;
//		g2.drawString(text, x, y);
//		
//		if(commandNum == 1) {
//			g2.drawString(">", x - gp.tileSize , y);
//		}
//		
//		text = "QUIT";
//		x = getXforCenteredText(text); 
//		y += gp.tileSize * 1.5;
//		g2.drawString(text, x, y);
//		
//		if(commandNum == 2) {
//			g2.drawString(">", x - gp.tileSize , y);
//		}
	    //====================================================
	}
	
	public void drawPlayerlife() {
		
//		gp.player.life = 3;
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		
		int i = 0;
		//Empty heart
		while(i<gp.player.maxLife/2) {
			g2.drawImage(empty_heart, x, y,null);
			i++;
			x+=gp.tileSize;
		}
		//Reset 
		x = gp.tileSize/2;
		y = gp.tileSize/2;
		
		i = 0;
		
		//Current life
		while(i<gp.player.life) {
			g2.drawImage(half_heart,x,y,null);
			i++;
			if(i<gp.player.life) {
				g2.drawImage(full_heart, x, y,null);
			}
			i++;
			x+=gp.tileSize;
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
		int boxY = (gp.tileSize * 8);
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
	
	public void drawOptionsScreen() {
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(14F));
		
		// SUB WINDOW
		int frameX = gp.tileSize * 4;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 8;
		int frameHeight = gp.tileSize * 10;
		
		// IF WE WANT TO USE WOOD DIALOGUE
		g2.drawImage(dialogueBoxImage, frameX, frameY, frameWidth, frameHeight, null);
		
		// DEFAULT DIALOGUE
//		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		switch(subState) {
		case 0: options_top(frameX, frameY); break;
		case 1: options_fullScreenNotification(frameX, frameY); break;
		case 2: options_control(frameX, frameY); break;
		case 3: options_endGameConfirmation(frameX, frameY); break;
		}
		
		gp.keyH.enterPressed = false;
		
	}
	
	public void options_top(int frameX, int frameY){
		
		int textX;
		int textY;
		
		// TITLE
		String text = "OPTIONS";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize + 75;
		g2.drawString(text, textX, textY);
		
		g2.setFont(g2.getFont().deriveFont(12F));
		
		// FULL SCREEN ON/OFF
		textX = frameX + gp.tileSize + 20;
		textY += gp.tileSize;
		g2.drawString("FULL SCREEN", textX, textY);
		if(commandNum == 0) {
			g2.drawString("> ", textX - 25, textY);
			if(gp.keyH.enterPressed == true) {
				if(gp.fullScreenOn == false) {
					gp.fullScreenOn = true;
				}
				else if(gp.fullScreenOn == true) {
					gp.fullScreenOn = false;
				}
				subState = 1;
			}
		}
		
		// MUSIC
		textY += gp.tileSize - 20;
		g2.drawString("MUSIC", textX, textY);
		if(commandNum == 1) {
			g2.drawString("> ", textX - 25, textY);
		}
		
		// SE
		textY += gp.tileSize - 20;
		g2.drawString("SE", textX, textY);
		if(commandNum == 2) {
			g2.drawString("> ", textX - 25, textY);
		}
		
		// CONTROL
		textY += gp.tileSize - 20;
		g2.drawString("CONTROL", textX, textY);
		if(commandNum == 3) {
			g2.drawString("> ", textX - 25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 2;
				commandNum = 0;
			}
		}
		
		// END GAME
		textY += gp.tileSize - 20;
		g2.drawString("END GAME", textX, textY);
		if(commandNum == 4) {
			g2.drawString("> ", textX - 25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 3;
				commandNum = 0;
			}
		}
		
		// BACK
		textY += (gp.tileSize * 2) - 20;
		g2.drawString("BACK", textX, textY);
		if(commandNum == 5) {
			g2.drawString("> ", textX - 25, textY);
			if(gp.keyH.enterPressed == true) {
				gp.gameState = gp.playState;
				commandNum = 0;
			}
		}
		
		// FULL SCREEN CHECK BOX
		textX = (int) (frameX + gp.tileSize * 4.5);
		textY = (frameY + gp.tileSize * 3) + 5;
//		g2.drawRect(textX, textY, 24, 24);
		g2.drawImage(checkBoxImage, textX, textY, 24, 30, null);
		if(gp.fullScreenOn == true) {
			g2.drawImage(checkmarkImage, textX, textY, 24, 24, null);
		}
		
		// MUSIC VOLUME BAR
		textY += gp.tileSize - 20;
		g2.drawImage(longBoxImage, textX, textY, 110, 30, null); // 110 / 5 = 22 
		
		// Draw volume "points" based on volume scale
		for(int i = 0; i < gp.music.volumeScale; i++) {
			int pointX = textX + (i * 22); // 110px wide total, so 22px per unit
			g2.drawImage(pointImage, pointX, textY, 22, 30, null);
		}
		
		
		// SE VOLUME BAR
		textY += gp.tileSize - 20;
		g2.drawImage(longBoxImage, textX, textY, 110, 30, null);
		
		for(int i = 0; i < gp.se.volumeScale; i++) {
			int pointX = textX + (i * 22);
			g2.drawImage(pointImage, pointX, textY, 22, 30, null);	
		}
		
		gp.config.saveConfig();
	}
	
	public void options_fullScreenNotification(int frameX, int frameY) {
		
		int textX = frameX + gp.tileSize + 20;
		int textY = frameY + gp.tileSize * 3;
		
		g2.setFont(g2.getFont().deriveFont(10F));
		
		currentDialogue = "The change will take \neffect after restarting \nthe game.";
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		
		// BACK
		textY += frameY + gp.tileSize;
		g2.drawString("BACK", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
			}
		}
		
		
	}
	
	public void options_control(int frameX, int frameY) {
		
		int textX;
		int textY;
		
		g2.setFont(g2.getFont().deriveFont(12F));
		
		// TITLE
		String text = "CONTROL";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize + 70;
		g2.drawString(text, textX, textY);
		
		textX = frameX + gp.tileSize;
		textY += gp.tileSize;
		g2.drawString("Move", textX, textY); textY += gp.tileSize - 20;
		g2.drawString("Confirm/Attack", textX, textY); textY += gp.tileSize - 20;
		g2.drawString("Shoot/Cast", textX, textY); textY += gp.tileSize - 20;
		g2.drawString("Pause", textX, textY); textY += gp.tileSize - 20;
		g2.drawString("Options", textX, textY); textY += gp.tileSize - 20;
		
		textX = frameX + gp.tileSize * 6;
		textY = frameY + (gp.tileSize * 4) - 25;
		g2.drawString("WASD", textX, textY); textY += gp.tileSize - 20;
		g2.drawString("ENTER", textX, textY); textY += gp.tileSize - 20;
		g2.drawString("F", textX, textY); textY += gp.tileSize - 20;
		g2.drawString("P", textX, textY); textY += gp.tileSize - 20;
		g2.drawString("ESC", textX, textY); textY += gp.tileSize - 20;
		
		// BACK
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize * 7;
		g2.drawString("BACK", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 3;
			}
		}	
	}
	
	public void options_endGameConfirmation(int frameX, int frameY) {
		
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize * 3;
		
		g2.setFont(g2.getFont().deriveFont(12F));
		
		currentDialogue = "Quit the game and return \nto the title screen?";
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		
		// YES
		String text = "Yes";
		textX = getXforCenteredText(text);
		textY += gp.tileSize * 2;
		g2.drawString(text, textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				gp.music.stop();
				gp.gameState = gp.titleState;
			}
		}
		
		// NO
		text = "No";
		textX = getXforCenteredText(text);
		textY += gp.tileSize;
		g2.drawString(text, textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX - 25, textY);
			if(gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 4;
			}
		}
	}
	
	
	public void drawCharacterScreen() {
		
	}
	
	public void drawInventory() {

		final int frameWidth = gp.tileSize * 9;        // Width to match visual slots
		final int frameHeight = gp.tileSize * 4 + 12;   // Slight padding for title bar

		final int frameX = gp.screenWidth / 2 - frameWidth / 2 - gp.tileSize*2;           // Center horizontally
		final int frameY = gp.screenHeight / 2 + gp.tileSize + 8;     // Just below player

		g2.drawImage(InImage, frameX, frameY, frameWidth, frameHeight, null);
		
		final int slotXStart = frameX + 21;
		final int slotYStart = frameY +21;
		int slotX = slotXStart;
		int slotY = slotYStart;
		final int slotSize = gp.tileSize + 8;
		
		for(int i = 0;i<gp.player.inventory.size();i++) {
			
			//Equip cursor
			if(gp.player.inventory.get(i) == gp.player.currentWeapon || 
					gp.player.inventory.get(i) == gp.player.currentShield || 
					gp.player.inventory.get(i) == gp.player.currentBall) {
				g2.setColor(new Color(75,46,25));
				g2.fillRoundRect(slotX,slotY,gp.tileSize,gp.tileSize,10,10);
			}
			g2.drawImage(gp.player.inventory.get(i).down1, slotX,slotY,null);
			
			slotX += slotSize;
			
			if(i == 6 || i == 13 || i == 20) {
				slotX = slotXStart;
				slotY +=slotSize;
			}
		}
		
		// Cursor
		int cursorX = slotXStart + (slotSize *slotCol);
		int cursorY = slotYStart + (slotSize*slotRow);
		int cursorWidth = gp.tileSize;
		int cursorHeight = gp.tileSize;
		// draw Cursor
		Color goldenBrown = new Color(0xb37731);
		g2.setColor(goldenBrown);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight,10,10);
		
		// Description frame
		int dFrameX = frameX+frameWidth +16;
		int dFrameY = frameY ;
		int dFrameWidth = gp.tileSize*4;
		int dFrameHeight = frameHeight;
		
		
		
		int textX = dFrameX + 20;
		int textY = dFrameY +gp.tileSize ;
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 10F));
		
		int maxLineWidth = dFrameWidth - 40; // total horizontal space (20px padding on both sides)
		FontMetrics fm = g2.getFontMetrics();

		int itemIndex = getItemIndexOnSlot();
		if (itemIndex < gp.player.inventory.size()) {
			g2.drawImage(DeImage, dFrameX, dFrameY, dFrameWidth, dFrameHeight, null);
			String[] paragraphLines = gp.player.inventory.get(itemIndex).description.split("\n");

			for (String paragraph : paragraphLines) {
			    String[] words = paragraph.split(" ");
			    String currentLine = "";

			    for (String word : words) {
			        String testLine = currentLine.isEmpty() ? word : currentLine + " " + word;

			        if (fm.stringWidth(testLine) <= maxLineWidth) {
			            currentLine = testLine;
			        } else {
			            g2.drawString(currentLine, textX, textY);
			            textY += 20;
			            currentLine = word;
			        }
			    }

			    // draw any remaining line after the loop
			    if (!currentLine.isEmpty()) {
			        g2.drawString(currentLine, textX, textY);
			        textY += 20;
			    }
			}

		}

		
	}
	
	public void drawTransition() {
		
		counter++;
		g2.setColor(new Color(0,0,0,counter*5));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		if(counter == 50) {
			counter = 0;
			gp.gameState = gp.playState;
			gp.currentMap = gp.eHandler.tempMap;
			gp.player.worldX = gp.tileSize*gp.eHandler.tempCol;
			gp.player.worldY = gp.tileSize*gp.eHandler.tempRow;
			gp.eHandler.previousEventX = gp.player.worldX;
			gp.eHandler.previousEventY = gp.player.worldY;

		}
	}
	
	public int getItemIndexOnSlot() {
		int itemIndex = slotCol + (slotRow*7);
		return itemIndex;
	}
	
	// FOR DEFAULT DIALOGUE
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
