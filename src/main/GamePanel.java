package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

	// Screen Panel
	final int originalTileSize = 16; // 16x 16
	final int scale = 3;

	public final int tileSize = originalTileSize * scale; // make element 48x48
	public final int maxScreenRow = 12;
	public final int maxScreenCol = 16;
	public final int screenWidth = tileSize * maxScreenCol; // 768 px of screen
	public final int screenHeight = tileSize * maxScreenRow; // 576 px

	// WORLD SETTINGS
	public final int maxWorldCol = 105; // Changeable depends on world01.txt
	public final int maxWorldRow = 100;
	public final int worldWidth = tileSize * maxWorldCol; // Can be deleted
	public final int worldHeight = tileSize * maxWorldRow;// Can be deleted

	// Full Screen
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	
	// FPS
	int FPS = 60;

	// SYSTEM
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler(this);
	Sound music = new Sound();
	Sound se = new Sound();
	
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Thread gameThread; // to start and to stop

	// Entity and Object
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10];// object array
	public Entity npc[] = new Entity[10];
	
	// GAME STATE
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
//	public final int dialogueState = 3;

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);

		/*
		 * DoubleBuffered is a technique that draws graphics to an off-screen image
		 * first, then displays it all at once to prevent flickering and make rendering
		 * smoother.
		 */
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setupGame() {
		aSetter.setObject();
		aSetter.setNpc();
		playMusic(0);
		
		gameState = playState;

		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) tempScreen.getGraphics();
		
//		setFullScreen(); // command this if want to turn off fullscreen mode
	}

	public void setFullScreen() {
		// Get screen size from local device
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Main.window);
		
		// width and height of full screen
		screenWidth2 = Main.window.getWidth();
		screenHeight2 = Main.window.getHeight();
		
		}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	// delta method
	public void run() {

		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {

			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
//				drawToTempScreen();
				drawToScreen();
				delta--;
				drawCount++;
			}

			if (timer >= 1000000000) {
//				System.out.println("FPS: "+ drawCount );
				drawCount = 0;
				timer = 0;
			}
		}
	}

	public void update() {

		// Player
		if(gameState == playState) {
			player.update();
		} 
		
		if(gameState == pauseState) {
			// Nothing for now
		}

		// Npc
		for (int i = 0; i < npc.length; i++) {
			if (npc[i] != null) {
				npc[i].update();
			}
		}
	}

	// For activate full screen
	public void drawToTempScreen() {
		tileM.draw(g2); // draw the world before player !!

		// OBJECT
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] != null) { // to avoid null pointer errors
				obj[i].draw(g2, this);
			}
		}

		// Npc
		for (int i = 0; i < npc.length; i++) {
			if (npc[i] != null) {
				npc[i].draw(g2);
			}
		}

		// PLAYER
		player.draw(g2);
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		// TILE
		tileM.draw(g2); // draw the world before player !!

		// OBJECT
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] != null) { // to avoid null pointer errors
				obj[i].draw(g2, this);
			}
		}

		// Npc
		for (int i = 0; i < npc.length; i++) {
			if (npc[i] != null) {
				npc[i].draw(g2);
			}
		}

		// PLAYER
		player.draw(g2);
		
		// UI
		ui.draw(g2);

		g2.dispose();
	}

	public void drawToScreen() {
		
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2,null);
		g.dispose();
	}
	// for background music
	public void playMusic(int i) {

		music.setFile(i); // get sound from file
		music.play(); // play sound
		music.loop(); // loop sound through game
	}

	public void stopMusic() {

		music.stop();
	}

	// for item sound effect
	public void playSE(int i) {

		se.setFile(i);
		se.play();
	}
}